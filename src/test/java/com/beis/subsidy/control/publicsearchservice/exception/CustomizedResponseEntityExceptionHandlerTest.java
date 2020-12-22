package com.beis.subsidy.control.publicsearchservice.exception;

import com.beis.subsidy.control.publicsearchservice.service.impl.SearchServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CustomizedResponseEntityExceptionHandlerTest {

    @InjectMocks
    private CustomizedResponseEntityExceptionHandler sut;

    Exception ex = mock(Exception.class);
    WebRequest request = mock(WebRequest.class);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHandleEmptyResultException() {
        SearchResultNotFoundException searchResultDataAccessException = new SearchResultNotFoundException("Not Found");
        ResponseEntity<Object> responseEntity
                = sut.handleAwardNotFoundException(searchResultDataAccessException,request);
        ExceptionResponse response = (ExceptionResponse)responseEntity.getBody();
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertEquals("Not Found", response.getMessage());
    }

    @Test
    public void testHandleAllExceptions() {
        Exception serverException = new Exception("Server Not Found");
        ResponseEntity<Object> responseEntity
                = sut.handleAllExceptions(serverException,request);
        ExceptionResponse response = (ExceptionResponse)responseEntity.getBody();
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, responseEntity.getStatusCode());
        assertEquals("Server Not Found", response.getMessage());
    }

    @Test
    public void testBadRequest() {
        InvalidRequestException invalidException = new InvalidRequestException("Bad Request");
        ResponseEntity<Object> responseEntity
                = sut.customValidationError(invalidException,request);
        ExceptionResponse response = (ExceptionResponse)responseEntity.getBody();
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Bad Request", response.getMessage());
    }

    @Test
    public void testMethodArgumentNotValidException() {
        HttpHeaders headerMock = mock(HttpHeaders.class);
        BindingResult bindingResult = mock(BindingResult.class);
        MethodArgumentNotValidException exceptionMock = mock(MethodArgumentNotValidException.class);
        when(exceptionMock.getBindingResult()).thenReturn(bindingResult);
        ResponseEntity<Object> responseEntity
                = sut.handleMethodArgumentNotValid(exceptionMock,headerMock,HttpStatus.BAD_REQUEST,request);
        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
    }
}
