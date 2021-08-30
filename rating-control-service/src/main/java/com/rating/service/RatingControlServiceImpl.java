package com.rating.service;

import com.rating.common.RatingLevels;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
@NoArgsConstructor
public class RatingControlServiceImpl implements RatingControlService{

    private RestTemplate restTemplate;

    public RatingControlServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean canReadBook(String customerRatingControlLevel, String bookId) {

        HttpEntity<?> requestEntity = new HttpEntity<>(generateHeader());
        ResponseEntity<String> responseEntity = restTemplate.exchange("https://mythird-party.service.com/fetch/book/ratings" + bookId,
                HttpMethod.GET,
                requestEntity,
                String.class);

        if (HttpStatus.OK == responseEntity.getStatusCode()) {
            String ratingControlLevel = responseEntity.getBody();
            if (Integer.parseInt(ratingControlLevel) <= Integer.parseInt(customerRatingControlLevel)) {
                return true;
            }
        }

        return false;
    }

    private MultiValueMap<String, String> generateHeader() {
        MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
        headers.add("Accept", "application/json");
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
