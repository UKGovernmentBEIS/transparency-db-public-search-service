package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.Filter;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.*;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import com.beis.subsidy.control.publicsearchservice.model.MFAGrouping;
import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
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

    GrantingAuthorityRepository grantingAuthorityRepositoryMock;

    MFAGrouping mfaGrouping = new MFAGrouping();
    MFAGroupingResponse mfaGroupingResponse;
    GrantingAuthority grantingAuthority = new GrantingAuthority();
    MFAAward mfaAward = new MFAAward();
    MFAAwardResponse mfaAwardResponse;
    MFAAwardsResponse mfaAwardsResponse;
    Filter filter;
    Pageable mfaPageable;
    List<GrantingAuthority> gaList = new ArrayList<>();

    @BeforeEach
    public void setUp() throws Exception {
        filter = new Filter();
        mfaPageable = PageRequest.of(
                0,
                10,
                Sort.by(Sort.Direction.DESC, "publishedDate")
        );
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
        grantingAuthorityRepositoryMock = mock(GrantingAuthorityRepository.class);
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
    public void testAllGas(){
        final HttpStatus expectedHttpStatus = HttpStatus.OK;
        gaList.add(new GrantingAuthority());
        Mockito.when(grantingAuthorityRepositoryMock.findAll()).thenReturn(gaList);

        ResponseEntity<?> actual = searchController.allGas();

        assertThat(actual.getStatusCode()).isEqualTo(expectedHttpStatus);
        assertThat(actual.getBody()).isInstanceOf(GrantingAuthorityListResponse.class);

        GrantingAuthorityListResponse gaResponse = (GrantingAuthorityListResponse) actual.getBody();
        assert gaResponse != null;
        assertThat(gaResponse.getGaList()).isNotNull();
        assertThat(gaResponse.getGaList().size()).isEqualTo(1);
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
        Mockito.when(searchServiceMock.findMfaAwards(Mockito.any(Filter.class),Mockito.any(Pageable.class))).thenReturn(mfaAwardsResponse);

        ResponseEntity<?> actual = searchController.findMfaAwards(filter, mfaPageable);
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

    @Test
    void findSchemesTest() {
        Filter filter = new Filter();
        filter.setKeyword("keyword");

        Pageable pageable = PageRequest.of(
                0,
                10,
                Sort.by(Sort.Direction.DESC, "publishedDate")
        );

        SubsidyMeasuresResponse expectedResponse = new SubsidyMeasuresResponse(
                Collections.emptyList(),
                0,
                1,
                0
        );

        when(searchServiceMock.findSchemes(any(Filter.class), any(Pageable.class)))
                .thenReturn(expectedResponse);

        ResponseEntity<SubsidyMeasuresResponse> response =
                searchController.findSchemes(filter, pageable);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(expectedResponse, response.getBody());

        verify(searchServiceMock).findSchemes(
                eq(filter),
                argThat(mappedPageable ->
                        mappedPageable.getPageNumber() == 0
                                && mappedPageable.getPageSize() == 10
                )
        );
    }

    @Test
    void findSearchResultsPreservesNonDefaultPageSize() {
        SearchInput input = new SearchInput();
        input.setTotalRecordsPerPage(25);
        when(searchServiceMock.findMatchingAwards(input)).thenReturn(searchResultsResponse);

        ResponseEntity<SearchResults> response = searchController.findSearchResults(input);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(input.getTotalRecordsPerPage()).isEqualTo(25);
        verify(searchServiceMock).findMatchingAwards(input);
    }

    @Test
    void findStandaloneAwardsAppliesDefaultPageAndPageSize() {
        SearchInput input = new SearchInput();
        input.setSortBy(new String[]{""});
        when(searchServiceMock.findStandaloneAwards(input)).thenReturn(searchResultsResponse);

        ResponseEntity<SearchResults> response = searchController.findStandaloneAwards(input);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(input.getPageNumber()).isEqualTo(1);
        assertThat(input.getTotalRecordsPerPage()).isEqualTo(10);
        assertThat(input.getSortBy()).containsExactly("awardNumber,desc");
        verify(searchServiceMock).findStandaloneAwards(input);
    }

    @Test
    void findStandaloneAwardsPreservesExplicitPageAndPageSize() {
        SearchInput input = new SearchInput();
        input.setPageNumber(3);
        input.setTotalRecordsPerPage(25);
        input.setSortBy(new String[]{"awardNumber"});
        when(searchServiceMock.findStandaloneAwards(input)).thenReturn(searchResultsResponse);

        searchController.findStandaloneAwards(input);

        assertThat(input.getPageNumber()).isEqualTo(3);
        assertThat(input.getTotalRecordsPerPage()).isEqualTo(25);
        assertThat(input.getSortBy()).containsExactly("awardNumber,asc");
    }

    @Test
    void findMfaAwardRejectsNonNumericAwardNumber() {
        ResponseEntity<MFAAwardResponse> response = searchController.findMfaAward("not-a-number");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isNotNull();
        verify(searchServiceMock, never()).findMfaByAwardNumber(anyLong());
    }

    @Test
    void findMfaAwardReturnsMatchingAward() {
        when(searchServiceMock.findMfaByAwardNumber(1L)).thenReturn(mfaAwardResponse);

        ResponseEntity<MFAAwardResponse> response = searchController.findMfaAward("1");

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isSameAs(mfaAwardResponse);
        verify(searchServiceMock).findMfaByAwardNumber(1L);
    }

    @Test
    void findMfaAwardRejectsMissingAwardNumber() {
        Assertions.assertThrows(InvalidRequestException.class, () -> searchController.findMfaAward(null));
    }

    @Test
    void exportEndpointsReturnServiceResponses() {
        Filter mfaFilter = new Filter();
        Filter awardFilter = new Filter();
        Filter schemeFilter = new Filter();
        MFAAwardsExportResponse mfaResponse = new MFAAwardsExportResponse(Collections.emptyList());
        AwardsExportResponse awardExportResponse = new AwardsExportResponse(Collections.emptyList());
        SubsidyMeasuresExportResponse schemeResponse = new SubsidyMeasuresExportResponse(Collections.emptyList());

        when(searchServiceMock.findMfaAwardsForExport(mfaFilter)).thenReturn(mfaResponse);
        when(searchServiceMock.findAwardsForExport(awardFilter)).thenReturn(awardExportResponse);
        when(searchServiceMock.findSchemesForExport(schemeFilter)).thenReturn(schemeResponse);

        assertThat(searchController.exportMfaAwards(mfaFilter).getBody()).isSameAs(mfaResponse);
        assertThat(searchController.exportAwards(awardFilter).getBody()).isSameAs(awardExportResponse);
        assertThat(searchController.exportSchemes(schemeFilter).getBody()).isSameAs(schemeResponse);

        verify(searchServiceMock).findMfaAwardsForExport(mfaFilter);
        verify(searchServiceMock).findAwardsForExport(awardFilter);
        verify(searchServiceMock).findSchemesForExport(schemeFilter);
    }

    @Test
    void findAwardsMapsUnsortedDefaultAndRecipientSorts() {
        Filter unsortedFilter = new Filter();
        Filter recipientFilter = new Filter();
        Filter schemeNameFilter = new Filter();
        Filter fallbackFilter = new Filter();
        AwardsResponse expectedResponse = new AwardsResponse(Collections.emptyList(), 0, 0, 0);
        Pageable unsorted = PageRequest.of(0, 10, Sort.unsorted());
        Pageable recipientSort = PageRequest.of(0, 10, Sort.by("recipientName"));
        Pageable schemeNameSort = PageRequest.of(0, 10, Sort.by("subsidySchemeName"));
        Pageable fallbackSort = PageRequest.of(0, 10, Sort.by("other"));

        when(searchServiceMock.findAwards(any(Filter.class), any(Pageable.class))).thenReturn(expectedResponse);

        assertThat(searchController.findAwards(unsortedFilter, unsorted).getBody()).isSameAs(expectedResponse);
        assertMappedSort(unsortedFilter, "publishedAwardDate");
        assertThat(searchController.findAwards(recipientFilter, recipientSort).getBody()).isSameAs(expectedResponse);
        assertMappedSort(recipientFilter, "beneficiary.beneficiaryName");
        assertThat(searchController.findAwards(schemeNameFilter, schemeNameSort).getBody()).isSameAs(expectedResponse);
        assertMappedSort(schemeNameFilter, "beneficiary.beneficiaryName");
        assertThat(searchController.findAwards(fallbackFilter, fallbackSort).getBody()).isSameAs(expectedResponse);
        assertMappedSort(fallbackFilter, "publishedAwardDate");
    }

    @Test
    void findMfaAwardsMapsRecipientSortAndNullSort() {
        Filter recipientFilter = new Filter();
        Filter nullSortFilter = new Filter();
        Pageable recipientSort = PageRequest.of(0, 10, Sort.by("recipientName"));
        Pageable nullSort = mock(Pageable.class);
        when(nullSort.getPageNumber()).thenReturn(0);
        when(nullSort.getPageSize()).thenReturn(10);
        when(nullSort.getSort()).thenReturn(null);
        when(searchServiceMock.findMfaAwards(any(Filter.class), any(Pageable.class))).thenReturn(mfaAwardsResponse);

        assertThat(searchController.findMfaAwards(recipientFilter, recipientSort).getStatusCode()).isEqualTo(HttpStatus.OK);
        assertMappedMfaSort(recipientFilter, "recipientName");
        assertThat(searchController.findMfaAwards(nullSortFilter, nullSort).getStatusCode()).isEqualTo(HttpStatus.OK);
        assertMappedMfaSort(nullSortFilter, "publishedDate");
    }

    @Test
    void findSchemesMapsSchemeRecipientSort() {
        Filter schemeNameFilter = new Filter();
        Pageable pageable = PageRequest.of(0, 10, Sort.by("subsidySchemeName"));
        SubsidyMeasuresResponse expectedResponse = new SubsidyMeasuresResponse(Collections.emptyList(), 0, 0, 0);
        when(searchServiceMock.findSchemes(any(Filter.class), any(Pageable.class))).thenReturn(expectedResponse);

        assertThat(searchController.findSchemes(schemeNameFilter, pageable).getBody()).isSameAs(expectedResponse);
        verify(searchServiceMock).findSchemes(
                eq(schemeNameFilter),
                argThat(mappedPageable ->
                        "subsidyMeasureTitle".equals(mappedPageable.getSort().iterator().next().getProperty())
                )
        );
    }

    private void assertMappedSort(Filter filter, String expectedProperty) {
        verify(searchServiceMock).findAwards(
                eq(filter),
                argThat(pageable -> expectedProperty.equals(pageable.getSort().iterator().next().getProperty()))
        );
    }

    private void assertMappedMfaSort(Filter filter, String expectedProperty) {
        verify(searchServiceMock).findMfaAwards(
                eq(filter),
                argThat(pageable -> expectedProperty.equals(pageable.getSort().iterator().next().getProperty()))
        );
    }
}
