package com.rating.common;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class RatingControlServiceConfigTest {

    private static final String BOOK_SERVICE_URL = "https://my-third-party.service.com/fetch/book/rating/";

    @Autowired
    private RatingControlServiceConfig ratingControlService;

    @Test
    public void loadContext() { }

    @Test
    public void shouldLoadBookServiceEndPoint() {
        assertThat(ratingControlService.getBookServiceEndpoint()).isNotEmpty();
        assertThat(ratingControlService.getBookServiceEndpoint()).isEqualTo(BOOK_SERVICE_URL);
    }


}