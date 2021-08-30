package com.rating.exception;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.*;

@ExtendWith(SpringExtension.class)
class BookNotFoundExceptionTest {

    @Test
    public void shouldThrowBookNOtFoundExceptionWithMessage_whenBookIsNotFound() {
        // After this test passes, our service implementation is ready
        // and we can move on to writing test cases for exceptions in our service class
        assertThatThrownBy(() -> {
            throw new BookNotFoundException("Book not found");
        }).isInstanceOf(BookNotFoundException.class)
                .hasMessageContaining("Book not found");
    }

}