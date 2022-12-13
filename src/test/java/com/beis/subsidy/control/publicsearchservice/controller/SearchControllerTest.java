package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.*;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import com.beis.subsidy.control.publicsearchservice.model.MFAGrouping;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import org.apache.tomcat.jni.Local;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.servlet.http.HttpServletRequest;
import java.math.BigDecimal;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.powermock.api.mockito.PowerMockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class SearchControllerTest {

    @InjectMocks
    private SearchController searchController;

    SearchService searchServiceMock;

    SearchInput searchInput = new SearchInput();

    SearchResults searchResultsResponse = new SearchResults();

    AwardResponse awardResponse = new AwardResponse();

    @Mock
    HttpServletRequest requestMock;

    MFAGrouping mfaGrouping = new MFAGrouping();
    MFAGroupingResponse mfaGroupingResponse;
    GrantingAuthority grantingAuthority = new GrantingAuthority();
    MFAAward mfaAward = new MFAAward();
    MFAAwardResponse mfaAwardResponse;
    MFAAwardsResponse mfaAwardsResponse;

    @BeforeEach
    public void setUp() throws Exception {
        grantingAuthority.setGrantingAuthorityName("TEST GA");

        mfaGrouping.setMfaGroupingName("MFA Grouping");
        mfaGrouping.setMfaGroupingNumber("MFA10001");
        mfaGrouping.setGaId(1L);
        mfaGrouping.setGrantingAuthority(grantingAuthority);
        mfaGrouping.setCreatedBy("A Person");
        mfaGrouping.setStatus("Active");
        mfaGrouping.setCreatedTimestamp(LocalDateTime.now());
        mfaGrouping.setLastModifiedTimestamp(LocalDateTime.now());


        mfaAward.setMfaAwardNumber(1L);
        mfaAward.setSPEI(false);
        mfaAward.setMfaGroupingPresent(true);
        mfaAward.setMfaGroupingNumber("MFA10001");
        mfaAward.setMfaGrouping(mfaGrouping);
        mfaAward.setGaId(1L);
        mfaAward.setGrantingAuthority(grantingAuthority);
        mfaAward.setAwardAmount(BigDecimal.valueOf(100000));
        mfaAward.setConfirmationDate(LocalDate.now());
        mfaAward.setPublishedDate(LocalDate.now());
        mfaAward.setRecipientName("A Charity");
        mfaAward.setRecipientIdType("Charity Number");
        mfaAward.setRecipientId("12345678");
        mfaAward.setStatus("Published");
        mfaAward.setCreatedBy("A Person");
        mfaAward.setApprovedBy("Another Person");
        mfaAward.setCreatedTimestamp(LocalDateTime.now());
        mfaAward.setLastModifiedTimestamp(LocalDateTime.now());

        mfaAwardResponse = new MFAAwardResponse(mfaAward);
        mfaAwardsResponse = new MFAAwardsResponse(new ArrayList<>(Arrays.asList(mfaAward,mfaAward,mfaAward)),3,1,1);

        searchServiceMock = mock(SearchService.class);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testRetrieveServiceResultsResponseByRequest() throws ParseException {
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        searchInput.setTotalRecordsPerPage(0);
        when(searchServiceMock.findMatchingAwards(searchInput)).thenReturn(searchResultsResponse);
        ResponseEntity<?> actual = searchController
                .findSearchResults(searchInput);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        verify(searchServiceMock, times(1)).findMatchingAwards(searchInput);
    }

    @Test
    public void testRetrieveServiceResultsByResponsePageOneByRequest() throws ParseException {
        final HttpStatus expectedHttpStatus = HttpStatus.OK;
        SearchInput searchInputMock = mock(SearchInput.class);
        when(searchInputMock.getTotalRecordsPerPage()).thenReturn(1);
        when(searchServiceMock.findMatchingAwards(searchInput)).thenReturn(searchResultsResponse);
        ResponseEntity<?> actual = searchController
                .findSearchResults(searchInput);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        verify(searchServiceMock, times(1)).findMatchingAwards(searchInput);
        verify(searchInputMock,times(0)).setTotalRecordsPerPage(1);
    }

    @Test
    public void testGetAwardDetailsByAwardIdShouldThrowException() {
        Assertions.assertThrows(InvalidRequestException.class, () -> {
            searchController.getAwardDetailsByAwardNumber(null);
        });
    }

    @Test
    public void testGetAwardDetailsByAwardId() {
        final HttpStatus expectedHttpStatus = HttpStatus.OK;
        when(searchServiceMock.findByAwardNumber(1L)).thenReturn(awardResponse);
        ResponseEntity<?> actual = searchController.getAwardDetailsByAwardNumber(1L);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        verify(searchServiceMock, times(1)).findByAwardNumber(any());
    }

    @Test
    public void testHealthOfTheSystem() {
        final HttpStatus expectedHttpStatus = HttpStatus.OK;
        when(searchServiceMock.findByAwardNumber(1L)).thenReturn(awardResponse);
        ResponseEntity<?> actual = searchController.getHealth();
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(actual.getBody().toString().equals("Successful health check - Public Search API"));
    }

    @Test
    public void testFindMfaAwards(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        Mockito.when(requestMock.getParameter(Mockito.any(String.class))).thenReturn(null);
        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNotNull();
        assertThat(mfaAwardsResponseActual.getMfaAwards().size()).isEqualTo(3);
    }

    @Test
    public void testFindMfaAwardsAllFilters(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("sort")).thenReturn("mfaAwardNumber");
        Mockito.when(requestMock.getParameter("page")).thenReturn("1");
        Mockito.when(requestMock.getParameter("limit")).thenReturn("10");
        Mockito.when(requestMock.getParameter("amount-from")).thenReturn("500");
        Mockito.when(requestMock.getParameter("amount-to")).thenReturn("1000");
        Mockito.when(requestMock.getParameter("speia")).thenReturn("Yes");
        Mockito.when(requestMock.getParameter("ga")).thenReturn("Granting Authority");
        Mockito.when(requestMock.getParameter("recipient")).thenReturn("A Charity");
        Mockito.when(requestMock.getParameter("mfa-grouping")).thenReturn("MFA Grouping");
        Mockito.when(requestMock.getParameter("confirmation-day-from")).thenReturn("01");
        Mockito.when(requestMock.getParameter("confirmation-month-from")).thenReturn("01");
        Mockito.when(requestMock.getParameter("confirmation-year-from")).thenReturn("2022");
        Mockito.when(requestMock.getParameter("confirmation-day-to")).thenReturn("31");
        Mockito.when(requestMock.getParameter("confirmation-month-to")).thenReturn("12");
        Mockito.when(requestMock.getParameter("confirmation-year-to")).thenReturn("2022");
        Mockito.when(requestMock.getParameter("status")).thenReturn("Published");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNotNull();
        assertThat(mfaAwardsResponseActual.getMfaAwards().size()).isEqualTo(3);
    }

    @Test
    public void testFindMfaAwardsNoEndDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("confirmation-day-from")).thenReturn("01");
        Mockito.when(requestMock.getParameter("confirmation-month-from")).thenReturn("01");
        Mockito.when(requestMock.getParameter("confirmation-year-from")).thenReturn("2022");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNotNull();
        assertThat(mfaAwardsResponseActual.getMfaAwards().size()).isEqualTo(3);
    }

    @Test
    public void testFindMfaAwardsNoStartDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("confirmation-day-to")).thenReturn("31");
        Mockito.when(requestMock.getParameter("confirmation-month-to")).thenReturn("12");
        Mockito.when(requestMock.getParameter("confirmation-year-to")).thenReturn("2022");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNotNull();
        assertThat(mfaAwardsResponseActual.getMfaAwards().size()).isEqualTo(3);
    }

    @Test
    public void testFindMfaAwardsInvalidPageNumber(){
        final HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("page")).thenReturn("X");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNull();
    }

    @Test
    public void testFindMfaAwardsInvalidSpeia(){
        final HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("speia")).thenReturn("X");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNull();
    }

    @Test
    public void testFindMfaAwardsInvalidFromDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("confirmation-day-from")).thenReturn("31");
        Mockito.when(requestMock.getParameter("confirmation-month-from")).thenReturn("X");
        Mockito.when(requestMock.getParameter("confirmation-year-from")).thenReturn("Y");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNull();
    }

    @Test
    public void testFindMfaAwardsInvalidToDate(){
        final HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("confirmation-day-to")).thenReturn("31");
        Mockito.when(requestMock.getParameter("confirmation-month-to")).thenReturn("X");
        Mockito.when(requestMock.getParameter("confirmation-year-to")).thenReturn("Y");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNull();
    }

    @Test
    public void testFindMfaAwardsInvalidStatus(){
        final HttpStatus expectedHttpStatus = HttpStatus.BAD_REQUEST;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("status")).thenReturn("X");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNull();
    }

    @Test
    public void testFindMfaAwardsDescendingSort(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        Mockito.when(searchServiceMock.findMatchingMfaAwards(Mockito.any(SearchInput.class))).thenReturn(mfaAwardsResponse);

        // start param mocks
        Mockito.when(requestMock.getParameter("sort")).thenReturn("-mfaAwardNumber");
        // end param mocks

        ResponseEntity<?> actual = searchController.findMfaAwards();
        assertThat(actual.getBody()).isInstanceOf(MFAAwardsResponse.class);
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);

        MFAAwardsResponse mfaAwardsResponseActual = (MFAAwardsResponse) actual.getBody();
        assert mfaAwardsResponseActual != null;
        assertThat(mfaAwardsResponseActual.getMfaAwards()).isNotNull();
        assertThat(mfaAwardsResponseActual.getMfaAwards().size()).isEqualTo(3);
    }

    @Test
    public void testFindStandaloneAwards(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        String[] sortArray = {""};
        searchInput.setSortBy(sortArray);

        searchInput.setTotalRecordsPerPage(0);
        when(searchServiceMock.findStandaloneAwards(searchInput)).thenReturn(searchResultsResponse);
        ResponseEntity<?> actual = searchController
                .findStandaloneAwards(searchInput);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        verify(searchServiceMock, times(1)).findStandaloneAwards(searchInput);
    }

    @Test
    public void testFindStandaloneAwardsSorted(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        String[] sortArray = {"awardNumber"};
        searchInput.setSortBy(sortArray);

        searchInput.setTotalRecordsPerPage(0);
        when(searchServiceMock.findStandaloneAwards(searchInput)).thenReturn(searchResultsResponse);
        ResponseEntity<?> actual = searchController
                .findStandaloneAwards(searchInput);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        verify(searchServiceMock, times(1)).findStandaloneAwards(searchInput);
    }

    @Test
    public void testFindStandaloneAwardsSortedDesc(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;

        String[] sortArray = {"-awardNumber"};
        searchInput.setSortBy(sortArray);

        searchInput.setTotalRecordsPerPage(0);
        when(searchServiceMock.findStandaloneAwards(searchInput)).thenReturn(searchResultsResponse);
        ResponseEntity<?> actual = searchController
                .findStandaloneAwards(searchInput);
        assertThat(actual).isNotNull();
        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        verify(searchServiceMock, times(1)).findStandaloneAwards(searchInput);
    }
}
