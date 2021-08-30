package com.rating.service;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@NoArgsConstructor
public class RatingControlServiceImpl implements RatingControlService{

    private RestTemplate restTemplate;

    public RatingControlServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public boolean canReadBook(String customerRatingControlLeve, String bookId) {
        return false;
    }
}
