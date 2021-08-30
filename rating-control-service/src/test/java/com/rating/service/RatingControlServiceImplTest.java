package com.rating.service;

import com.rating.common.RatingControlServiceConfig;
import com.rating.exception.BookNotFoundException;
import com.rating.exception.TechnicalFailureException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
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

    @Test
    public void shouldReturnFalse_whenTechnicalFailureExceptionIsThrownFromBookService() throws Exception {
        // This will fail if there is nothing in the service code to handle the exception.
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(),
                Mockito.<Class<String>>any())).thenThrow(TechnicalFailureException.class);

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID)).isFalse();
    }

    @Test
    public void shouldReturnBookNotFoundException_whenExceptionIsThrownFromBookServiceForBookNotFound() {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(),
                Mockito.<Class<String>>any())).thenThrow(BookNotFoundException.class);

        Assertions.assertThrows(BookNotFoundException.class, () -> {
            ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID);
        });
    }

    // edge cases

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAsU_andCustomerProvidedRatingCodeIsU() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_U, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_U, TEST_SAMPLE_BOOK_ID)).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAsU_andCustomerProvidedRatingCodeIs8() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_U, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_8, TEST_SAMPLE_BOOK_ID)).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs8_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_8, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID)).isTrue();
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs12_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_12, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_BOOK_ID)).isTrue();
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAs15_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_15, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID)).isFalse();
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAs18_andCustomerProvidedRatingCodeIs12() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_18, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_12, TEST_SAMPLE_BOOK_ID)).isFalse();
    }

    @Test
    public void shouldReturnTrue_whenBookCodeLevelReturnAs15_andCustomerProvidedRatingCodeIs18() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_15, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_18, TEST_SAMPLE_BOOK_ID)).isTrue();
    }

    @Test
    public void shouldReturnFalse_whenBookCodeLevelReturnAsXX_whichIsUnknown_andCustomerProvidedRatingCodeIs18() throws Exception {
        Mockito.when(restTemplate.exchange(anyString(), (HttpMethod) any(), (HttpEntity) any(), Mockito.<Class<String>>any()))
                .thenReturn(new ResponseEntity<>(BOOK_SERVICE_RATING_LEVEL_CODE_XX, HttpStatus.OK));

        assertThat(ratingControlService.canReadBook(CUSTOMER_RATING_LEVEL_CODE_18, TEST_SAMPLE_BOOK_ID)).isFalse();
    }
}