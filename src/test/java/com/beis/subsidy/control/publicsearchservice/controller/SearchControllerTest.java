package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.exception.SearchResultNotFoundException;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.text.ParseException;

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

    @BeforeEach
    public void setUp() throws Exception {

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

}
