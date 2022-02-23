package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.response.GrantingAuthorityListResponse;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;
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

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SchemeSearchControllerTest {

    @InjectMocks
    private SchemeSearchController schemeSearchController;

    SearchService searchServiceMock;
    GrantingAuthorityRepository grantingAuthorityRepositoryMock;
    List<GrantingAuthority> gaList = new ArrayList<>();

    @BeforeEach
    public void setUp(){
        searchServiceMock = mock(SearchService.class);
        grantingAuthorityRepositoryMock = mock(GrantingAuthorityRepository.class);
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

    @Test
    public void testAllGas(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;
        gaList.add(new GrantingAuthority());
        when(grantingAuthorityRepositoryMock.findAll()).thenReturn(gaList);

        ResponseEntity<?> actual = schemeSearchController.allGas();

        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(actual.getBody()).isInstanceOf(GrantingAuthorityListResponse.class);

        GrantingAuthorityListResponse gaResponse = (GrantingAuthorityListResponse) actual.getBody();
        assert gaResponse != null;
        assertThat(gaResponse.getGaList()).isNotNull();
        assertThat(gaResponse.getGaList().size()).isEqualTo(1);
    }
}
