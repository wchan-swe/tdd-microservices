package com.rating.common;

import org.junit.jupiter.api.Test;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

public class RatingLevelsTest {

    private Map<String, Integer> actualRatingCodeMap = RatingLevels.RATING_CODE_LEVEL;

    @Test
    public void shouldReturnParentalCodeLevelsBasedOnRequestedParentalCode() {
        assertThat(actualRatingCodeMap.get("U")).isEqualTo(0);
        assertThat(actualRatingCodeMap.get("8")).isEqualTo(1);
        assertThat(actualRatingCodeMap.get("12")).isEqualTo(2);
        assertThat(actualRatingCodeMap.get("15")).isEqualTo(3);
        assertThat(actualRatingCodeMap.get("18")).isEqualTo(4);
    }
}