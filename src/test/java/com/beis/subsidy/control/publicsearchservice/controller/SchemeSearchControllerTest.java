package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasureVersionResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasuresResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasureResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.GrantingAuthorityListResponse;


import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;

import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import com.beis.subsidy.control.publicsearchservice.model.LegalBasis;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasureVersion;

import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;

import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
    SearchResults smAwardSearchResults;
    Award smAssociatedAward;
    SubsidyMeasureVersionResponse smvResponse;
    GrantingAuthority ga;
    LegalBasis lb;
    SubsidyMeasure sm;
    SubsidyMeasureVersion smv;
    String scNumber;
    UUID versionUuid;
    HttpServletRequest requestMock;

    @BeforeEach
    public void setUp(){
        scNumber = "SC10001";
        versionUuid = UUID.randomUUID();

        ga = new GrantingAuthority();
        ga.setGrantingAuthorityName("Test GA");

        lb = new LegalBasis();
        lb.setLegalBasisText("Legal basis");

        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryId(1l);
        beneficiary.setNationalId("nationalId");
        beneficiary.setBeneficiaryName("bName");
        beneficiary.setOrgSize("1");

        smv = new SubsidyMeasureVersion();
        smv.setVersion(versionUuid);
        smv.setScNumber(scNumber);
        smv.setScNumber(scNumber);
        smv.setStartDate(LocalDate.now());
        smv.setEndDate(LocalDate.now());
        smv.setPublishedMeasureDate(LocalDate.now());
        smv.setCreatedTimestamp(LocalDateTime.now());
        smv.setLastModifiedTimestamp(LocalDateTime.now());
        smv.setBudget("500");
        smv.setGrantingAuthority(ga);
        smv.setLegalBasisText(lb.getLegalBasisText());

        sm = new SubsidyMeasure();
        sm.setScNumber(scNumber);
        sm.setStartDate(LocalDate.now());
        sm.setEndDate(LocalDate.now());
        sm.setPublishedMeasureDate(LocalDate.now());
        sm.setCreatedTimestamp(LocalDateTime.now());
        sm.setLastModifiedTimestamp(LocalDateTime.now());
        sm.setBudget("5000000");
        sm.setGrantingAuthority(ga);
        sm.setLegalBases(lb);

        smAssociatedAward = new Award();
        smAssociatedAward.setAwardNumber(1L);
        smAssociatedAward.setSubsidyMeasure(sm);
        smAssociatedAward.setApprovedBy("system");
        smAssociatedAward.setCreatedBy("system");
        smAssociatedAward.setCreatedTimestamp(LocalDate.now());
        smAssociatedAward.setLastModifiedTimestamp(LocalDate.now());
        smAssociatedAward.setGoodsServicesFilter("serviceFilter");
        smAssociatedAward.setLegalGrantingDate(LocalDate.now());
        smAssociatedAward.setSubsidyFullAmountRange("5000");
        smAssociatedAward.setSubsidyInstrument("subsidyInstrument");
        smAssociatedAward.setSubsidyObjective("subsidyObj");
        smAssociatedAward.setSubsidyFullAmountExact(new BigDecimal(100000.0));
        smAssociatedAward.setSpendingSector("spendingSector");
        smAssociatedAward.setLegalGrantingDate(LocalDate.now());
        smAssociatedAward.setStatus("status");
        smAssociatedAward.setPublishedAwardDate(LocalDate.now());
        smAssociatedAward.setBeneficiary(beneficiary);
        smAssociatedAward.setGrantingAuthority(ga);

        sm.setSchemeVersions(new ArrayList<>());

        smResponse = new SubsidyMeasureResponse(sm, true);
        smAwardSearchResults = new SearchResults();
        smAwardSearchResults.setAwards(Arrays.asList(new AwardResponse(smAssociatedAward,true)));
        smResponse.setAwardSearchResults(smAwardSearchResults);

        smsResponse = new SubsidyMeasuresResponse();
        smsResponse.setSubsidySchemes(new ArrayList<>(Arrays.asList(smResponse,smResponse,smResponse)));

        smvResponse = new SubsidyMeasureVersionResponse(smv);

        searchServiceMock = mock(SearchService.class);
        grantingAuthorityRepositoryMock = mock(GrantingAuthorityRepository.class);
        requestMock = mock(HttpServletRequest.class);
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

    @Test
    public void testGetSchemeDetailsByScNumberWithAwards()
    {
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        SearchInput searchInput = new SearchInput();
        searchInput.setScNumber(scNumber);
        searchInput.setTotalRecordsPerPage(10);
        searchInput.setPageNumber(1);
        when(searchServiceMock.findSchemeByScNumberWithAwards(scNumber, searchInput)).thenReturn(smResponse);

        ResponseEntity<?> actual = schemeSearchController.getSchemeDetailsByScNumberWithAwards(scNumber,searchInput);
        Assertions.assertSame(smResponse.getAwardSearchResults(), smAwardSearchResults);

        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasureResponse actualResponse = (SubsidyMeasureResponse) actual.getBody();
        assert actualResponse != null;

        assertThat(actualResponse).isInstanceOf(SubsidyMeasureResponse.class);
        assertThat(actualResponse.getScNumber()).isEqualTo(scNumber);

        Exception exception = assertThrows(InvalidRequestException.class, () -> schemeSearchController.getSchemeDetailsByScNumberWithAwards("", searchInput));

        String expectedMessage = "Invalid Request";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testAllSchemes(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(requestMock.getParameter(Mockito.any(String.class))).thenReturn(null);
        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getBody()).isInstanceOf(SubsidyMeasuresResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNotNull();
        assertThat(smsResponseActual.getSubsidySchemes().size()).isEqualTo(3);
    }

    @Test
    public void testAllSchemesAllFilters(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);

        // start param mocks
        when(requestMock.getParameter("sort")).thenReturn("scNumber");
        when(requestMock.getParameter("page")).thenReturn("1");
        when(requestMock.getParameter("limit")).thenReturn("10");
        when(requestMock.getParameter("budget-from")).thenReturn("500");
        when(requestMock.getParameter("budget-to")).thenReturn("1000");
        when(requestMock.getParameter("scnumber")).thenReturn("SC10001");
        when(requestMock.getParameter("name")).thenReturn("SC Name");
        when(requestMock.getParameter("ga")).thenReturn("Granting Authority");
        when(requestMock.getParameter("start-day-from")).thenReturn("01");
        when(requestMock.getParameter("start-month-from")).thenReturn("01");
        when(requestMock.getParameter("start-year-from")).thenReturn("2022");
        when(requestMock.getParameter("start-day-to")).thenReturn("31");
        when(requestMock.getParameter("start-month-to")).thenReturn("12");
        when(requestMock.getParameter("start-year-to")).thenReturn("2022");
        when(requestMock.getParameter("end-day-from")).thenReturn("01");
        when(requestMock.getParameter("end-month-from")).thenReturn("01");
        when(requestMock.getParameter("end-year-from")).thenReturn("2022");
        when(requestMock.getParameter("end-day-to")).thenReturn("31");
        when(requestMock.getParameter("end-month-to")).thenReturn("12");
        when(requestMock.getParameter("end-year-to")).thenReturn("2022");
        when(requestMock.getParameter("status")).thenReturn("active");
        when(requestMock.getParameter("adhoc")).thenReturn("no");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getBody()).isInstanceOf(SubsidyMeasuresResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNotNull();
        assertThat(smsResponseActual.getSubsidySchemes().size()).isEqualTo(3);
    }

    @Test
    public void testAllSchemesBudgetFromFilter(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);

        // start param mocks
        when(requestMock.getParameter("budget-from")).thenReturn("500");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getBody()).isInstanceOf(SubsidyMeasuresResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNotNull();
        assertThat(smsResponseActual.getSubsidySchemes().size()).isEqualTo(3);
    }

    @Test
    public void testAllSchemesBudgetToFilter(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);

        // start param mocks
        when(requestMock.getParameter("budget-to")).thenReturn("500");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getBody()).isInstanceOf(SubsidyMeasuresResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNotNull();
        assertThat(smsResponseActual.getSubsidySchemes().size()).isEqualTo(3);
    }

    @Test
    public void testAllSchemesNumberFormatException(){
        final HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        when(requestMock.getParameter("budget-from")).thenReturn("a");

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNull();
    }

    @Test
    public void testAllSchemesReverseSort(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);

        // start param mocks
        when(requestMock.getParameter("sort")).thenReturn("-scNumber");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getBody()).isInstanceOf(SubsidyMeasuresResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNotNull();
        assertThat(smsResponseActual.getSubsidySchemes().size()).isEqualTo(3);
    }

    @Test
    public void testAllSchemesInvalidStartFromDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        // start param mocks
        when(requestMock.getParameter("start-day-from")).thenReturn("32");
        when(requestMock.getParameter("start-month-from")).thenReturn("13");
        when(requestMock.getParameter("start-year-from")).thenReturn("9999");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
//        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).size().isEqualTo(3);
    }

    @Test
    public void testAllSchemesInvalidStartToDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        // start param mocks
        when(requestMock.getParameter("start-day-to")).thenReturn("32");
        when(requestMock.getParameter("start-month-to")).thenReturn("13");
        when(requestMock.getParameter("start-year-to")).thenReturn("9999");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).size().isEqualTo(3);
    }

    @Test
    public void testAllSchemesInvalidEndFromDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        // start param mocks
        when(requestMock.getParameter("end-day-from")).thenReturn("32");
        when(requestMock.getParameter("end-month-from")).thenReturn("13");
        when(requestMock.getParameter("end-year-from")).thenReturn("9999");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).size().isEqualTo(3);
    }

    @Test
    public void testAllSchemesInvalidEndToDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        // start param mocks
        when(requestMock.getParameter("end-day-to")).thenReturn("32");
        when(requestMock.getParameter("end-month-to")).thenReturn("13");
        when(requestMock.getParameter("end-year-to")).thenReturn("9999");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).size().isEqualTo(3);
    }

    @Test
    public void testAllSchemesStartEndFromNull(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        // start param mocks
        when(requestMock.getParameter("start-day-to")).thenReturn("31");
        when(requestMock.getParameter("start-month-to")).thenReturn("12");
        when(requestMock.getParameter("start-year-to")).thenReturn("2022");
        when(requestMock.getParameter("end-day-to")).thenReturn("31");
        when(requestMock.getParameter("end-month-to")).thenReturn("12");
        when(requestMock.getParameter("end-year-to")).thenReturn("2022");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getBody()).isInstanceOf(SubsidyMeasuresResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNotNull();
        assertThat(smsResponseActual.getSubsidySchemes().size()).isEqualTo(3);
    }

    @Test
    public void testAllSchemesStartEndToNull(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        // start param mocks
        when(requestMock.getParameter("start-day-from")).thenReturn("01");
        when(requestMock.getParameter("start-month-from")).thenReturn("01");
        when(requestMock.getParameter("start-year-from")).thenReturn("2022");
        when(requestMock.getParameter("end-day-from")).thenReturn("01");
        when(requestMock.getParameter("end-month-from")).thenReturn("01");
        when(requestMock.getParameter("end-year-from")).thenReturn("2022");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getBody()).isInstanceOf(SubsidyMeasuresResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNotNull();
        assertThat(smsResponseActual.getSubsidySchemes().size()).isEqualTo(3);
    }

    @Test
    public void testAllSchemesInvalidStatus(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findAllSchemes(Mockito.any(SearchInput.class))).thenReturn(smsResponse);
        // start param mocks
        when(requestMock.getParameter("status")).thenReturn("none");
        // end param mocks

        ResponseEntity<?> actual = schemeSearchController.allSchemes();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasuresResponse smsResponseActual = (SubsidyMeasuresResponse) actual.getBody();
        assert smsResponseActual != null;
        assertThat(smsResponseActual.getSubsidySchemes()).isNull();
    }

    @Test
    public void testFindSubsidySchemeVersion(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        when(searchServiceMock.findSubsidySchemeVersion(scNumber, versionUuid.toString())).thenReturn(smvResponse);

        ResponseEntity<?> actual = schemeSearchController.findSubsidySchemeVersion(scNumber, versionUuid.toString());

        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        SubsidyMeasureVersionResponse actualResponse = (SubsidyMeasureVersionResponse) actual.getBody();
        assert actualResponse != null;

        assertThat(actualResponse).isInstanceOf(SubsidyMeasureVersionResponse.class);
        assertThat(actualResponse.getScNumber()).isEqualTo(scNumber);
        assertThat(actualResponse.getVersion()).isEqualTo(versionUuid.toString());

        Exception scException = assertThrows(InvalidRequestException.class, () -> schemeSearchController.findSubsidySchemeVersion("", versionUuid.toString()));
        Exception versionException = assertThrows(InvalidRequestException.class, () -> schemeSearchController.findSubsidySchemeVersion(scNumber, ""));

        String expectedScMessage = "Bad Request SC Number is null";
        String expectedVersionMessage = "Bad Request version is null";

        assertEquals(scException.getMessage(), expectedScMessage);
        assertEquals(versionException.getMessage(), expectedVersionMessage);
    }
}
