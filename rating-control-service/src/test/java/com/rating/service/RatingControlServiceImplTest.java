package com.rating.service;

import com.rating.common.RatingControlServiceConfig;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.lenient;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RatingControlServiceImplTest {

    private static final String VALID_URL_BOOK_SERVICE = "https://my-third-party.service.com/fetch/book/rating/{book_Id}";

    private static final String CUSTOMER_RATING_LEVEL_CODE_12 = "12";
    private static final String TEST_BOOK_ID = "M1211";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_12 = "12";

    private static final String CUSTOMER_RATING_LEVEL_CODE_U = "U";
    private static final String CUSTOMER_RATING_LEVEL_CODE_8 = "8";
    private static final String CUSTOMER_RATING_LEVEL_CODE_15 = "15";
    private static final String CUSTOMER_RATING_LEVEL_CODE_18 = "18";

    private static final String TEST_SAMPLE_BOOK_ID = "S1211";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_U = "U";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_8 = "8";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_15 = "15";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_18 = "18";
    private static final String BOOK_SERVICE_RATING_LEVEL_CODE_XX = "XX";

    @Mock
    private RestTemplate restTemplate;

    @Mock
    private RatingControlServiceConfig ratingControlServiceConfig;

    @InjectMocks
    private RatingControlServiceImpl ratingControlService;

    @BeforeEach
    public void setup() {
        when(ratingControlServiceConfig.getBookServiceEndpoint()).thenReturn(VALID_URL_BOOK_SERVICE);
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturns12_andCustomerProvidedRatingCodeIs12() throws Exception {
        when(restTemplate.exchange(anyString(), any(), any(),
                Mockito.<Class<String>>any())).thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_12,
                HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID)).isTrue();
    }
}