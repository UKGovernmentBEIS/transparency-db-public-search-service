package com.beis.subsidy.control.publicsearchservice.exception;

import org.junit.jupiter.api.Test;

import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

public class ExceptionResponseTest {

    @Test
    public void testErrorResponse() {
        ExceptionResponse errorDetails = new ExceptionResponse();
        errorDetails.setDetails("details");
        errorDetails.setMessage("msg");
        errorDetails.setTimestamp(new Date());

        assertThat(errorDetails).isNotNull();
        assertThat(errorDetails.getDetails()).isEqualTo("details");
        assertThat(errorDetails.getMessage()).isEqualTo("msg");
        assertThat(errorDetails.getTimestamp()).isNotNull();
    }

    @Test
    public void testErrorResponseArgConstructor() {
        ExceptionResponse errorDetails = new ExceptionResponse(new Date(),"details","msg");
        assertThat(errorDetails).isNotNull();
        assertThat(errorDetails.getDetails()).isEqualTo("msg");
        assertThat(errorDetails.getMessage()).isEqualTo("details");
        assertThat(errorDetails.getTimestamp()).isNotNull();
    }
}
