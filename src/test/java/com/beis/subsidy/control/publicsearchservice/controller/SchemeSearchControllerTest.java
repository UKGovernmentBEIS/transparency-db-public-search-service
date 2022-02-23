package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SchemeSearchControllerTest {

    @InjectMocks
    private SchemeSearchController schemeSearchController;

    SearchService searchServiceMock;

    @BeforeEach
    public void setUp(){
        searchServiceMock = mock(SearchService.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testHealthOfTheSystem() {
        final HttpStatus expectedHttpStatus = HttpStatus.OK;
        ResponseEntity<?> actual = schemeSearchController.getHealth();
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        assertThat(actual.getBody()).isNotNull();
        assertThat(actual.getBody()).isEqualTo("Successful health check - Public Search API");
    }

}
