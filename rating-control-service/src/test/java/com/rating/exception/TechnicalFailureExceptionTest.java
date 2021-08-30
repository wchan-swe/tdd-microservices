package com.rating.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
public class TechnicalFailureExceptionTest {

    @Test
    public void shouldThrowTechnicalFailureExceptionWithMessage_whenTechnicalFailureIsThrown() {
        assertThatThrownBy(() -> {
            throw new TechnicalFailureException("System error");
        }).isInstanceOf(TechnicalFailureException.class)
                .hasMessageContaining("System error");
    }
}