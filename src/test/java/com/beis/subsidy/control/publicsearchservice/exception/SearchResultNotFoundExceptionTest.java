package com.beis.subsidy.control.publicsearchservice.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchResultNotFoundExceptionTest {

    @Test
    public void testHandleNotFoundException() {
        InvalidRequestException invalidRequestException = new InvalidRequestException("Not Found");
        assertThat(invalidRequestException).isNotNull();
        assertEquals(HttpStatus.NOT_FOUND.getReasonPhrase(), invalidRequestException.getMessage());
    }
}
