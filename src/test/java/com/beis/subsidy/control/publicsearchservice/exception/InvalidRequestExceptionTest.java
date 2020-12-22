package com.beis.subsidy.control.publicsearchservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvalidRequestExceptionTest {

    @Test
    public void testHandleInvalidRequestException() {
        InvalidRequestException invalidRequestException = new InvalidRequestException("Bad Request");
        assertThat(invalidRequestException).isNotNull();
        assertEquals(HttpStatus.BAD_REQUEST.getReasonPhrase(), invalidRequestException.getMessage());
    }
}
