package com.rating.service;

import org.springframework.stereotype.Service;

@Service
public class RatingControlServiceImpl implements RatingControlService{
    @Override
    public boolean canReadBook(String customerRatingControlLeve, String bookId) {
        return false;
    }
}
