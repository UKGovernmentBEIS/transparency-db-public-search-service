package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.response.GrantingAuthorityListResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasureResponse;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.LegalBasis;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SchemeSearchControllerTest {

    @InjectMocks
    private SchemeSearchController schemeSearchController;

    SearchService searchServiceMock;
    GrantingAuthorityRepository grantingAuthorityRepositoryMock;
    List<GrantingAuthority> gaList = new ArrayList<>();
    SubsidyMeasureResponse smResponse;
    SubsidyMeasuresResponse smsResponse;
    GrantingAuthority ga;
    LegalBasis lb;
    SubsidyMeasure sm;
    String scNumber;

    @BeforeEach
    public void setUp(){
        scNumber = "SC10001";

        ga = new GrantingAuthority();
        ga.setGrantingAuthorityName("Test GA");

        lb = new LegalBasis();
        lb.setLegalBasisText("Legal basis");

        sm = new SubsidyMeasure();
        sm.setScNumber(scNumber);
        sm.setStartDate(LocalDate.now());
        sm.setEndDate(LocalDate.now());
        sm.setPublishedMeasureDate(LocalDate.now());
        sm.setCreatedTimestamp(new Date(System.currentTimeMillis()));
        sm.setLastModifiedTimestamp(new Date(System.currentTimeMillis()));
        sm.setBudget("5000000");
        sm.setGrantingAuthority(ga);
        sm.setLegalBases(lb);

        smResponse = new SubsidyMeasureResponse(sm, true);

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

    @Test
    public void testGetSchemeDetailsByScNumber(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findSchemeByScNumber(scNumber)).thenReturn(smResponse);

        ResponseEntity<?> actual = schemeSearchController.getSchemeDetailsByScNumber(scNumber);

        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasureResponse actualResponse = (SubsidyMeasureResponse) actual.getBody();
        assert actualResponse != null;

        assertThat(actualResponse).isInstanceOf(SubsidyMeasureResponse.class);
        assertThat(actualResponse.getScNumber()).isEqualTo(scNumber);

        Exception exception = assertThrows(InvalidRequestException.class, () -> schemeSearchController.getSchemeDetailsByScNumber(""));

        String expectedMessage = "Invalid Request";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
}
