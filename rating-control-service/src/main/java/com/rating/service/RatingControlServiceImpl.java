package com.rating.service;

import com.rating.common.RatingControlServiceConfig;
import com.rating.common.RatingLevels;
import com.rating.exception.BookNotFoundException;
import com.rating.exception.TechnicalFailureException;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@NoArgsConstructor
public class RatingControlServiceImpl implements RatingControlService{

    private RestTemplate restTemplate;
    private RatingControlServiceConfig ratingControlServiceConfig;

    public RatingControlServiceImpl(RestTemplate restTemplate, RatingControlServiceConfig ratingControlServiceConfig) {
        this.restTemplate = restTemplate;
        this.ratingControlServiceConfig = ratingControlServiceConfig;
    }

    @Override
    public boolean canReadBook(String customerRatingControlLevel, String bookId) {
        Map<String, Integer> ratingControlLevelMap = RatingLevels.RATING_CODE_LEVEL;
        Integer customerProvidedRatingLevelOrder = ratingControlLevelMap.get(customerRatingControlLevel);
        HttpEntity<?> requestEntity = new HttpEntity<>(generateHeader());
        try {
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    ratingControlServiceConfig.getBookServiceEndpoint() + bookId,
                    HttpMethod.GET,
                    requestEntity,
                    String.class);

            if (HttpStatus.OK == responseEntity.getStatusCode()) {
                Integer bookRatingControlLevel = ratingControlLevelMap.get(responseEntity.getBody());
                String ratingControlLevel = responseEntity.getBody();
                if (containsValidRatingLevelCodes(bookRatingControlLevel, customerProvidedRatingLevelOrder)) {
                    return bookRatingControlLevel <= customerProvidedRatingLevelOrder;
                }
            }
            return false;
        } catch (TechnicalFailureException te) {
            return false;  // alternatively could throw TechnicalException instead of false
        } catch (BookNotFoundException be) {
            throw new BookNotFoundException("Book not found ");
        }
    }

    private MultiValueMap<String, String> generateHeader() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        return headers;
    }

    private boolean containsValidRatingLevelCodes(Integer bookRatingControlLevel, Integer customerProvidedRatingLevelOrder) {
        return !ObjectUtils.isEmpty(bookRatingControlLevel) && !ObjectUtils.isEmpty(customerProvidedRatingLevelOrder);
    }
}
