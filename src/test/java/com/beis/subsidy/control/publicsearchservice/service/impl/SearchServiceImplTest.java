package com.beis.subsidy.control.publicsearchservice.service.impl;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.exception.SearchResultNotFoundException;
import com.beis.subsidy.control.publicsearchservice.model.*;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class SearchServiceImplTest {

    private final AwardRepository awardRepository = mock(AwardRepository.class);

    @InjectMocks
    private SearchServiceImpl sut;

    Award award;
    List<Award> awards = new ArrayList<>();

    @BeforeEach
    public void setUp() {

        award = new Award();
        award.setAwardNumber(1l);
        award.setApprovedBy("system");
        award.setCreatedBy("system");
        award.setCreatedTimestamp(LocalDate.now());
        award.setGoodsServicesFilter("serviceFilter");
        award.setLegalGrantingDate(LocalDate.now());
        award.setSubsidyFullAmountRange("5000");
        award.setSubsidyInstrument("subsidyInstrument");
        award.setSubsidyObjective("subsidyObj");
        award.setSubsidyFullAmountExact(new BigDecimal(100000.0));
        award.setSpendingSector("spendingSector");
        award.setLegalGrantingDate(LocalDate.now());
        award.setStatus("status");
        award.setPublishedAwardDate(LocalDate.now());
        //beneficiary details
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryId(1l);
        beneficiary.setNationalId("nationalId");
        beneficiary.setBeneficiaryName("bName");
        beneficiary.setOrgSize("1");
        award.setBeneficiary(beneficiary);

        //SubsidyMeasure
        SubsidyMeasure subsidyMeasure = new  SubsidyMeasure();
        subsidyMeasure.setScNumber("SC10001");
        subsidyMeasure.setSubsidyMeasureTitle("title");
        subsidyMeasure.setAdhoc(false);
        subsidyMeasure.setDuration(new BigInteger("200"));
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setGaSubsidyWebLink("www.BEIS.com");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStartDate(LocalDate.now());
        subsidyMeasure.setEndDate(LocalDate.now());
        subsidyMeasure.setBudget("budget");
        subsidyMeasure.setPublishedMeasureDate(LocalDate.now());
        subsidyMeasure.setCreatedBy("SYSTEM");
        subsidyMeasure.setApprovedBy("SYSTEM");
        // Legal Basis text
        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);

        award.setSubsidyMeasure(subsidyMeasure);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        award.setGrantingAuthority(grantingAuthority);
        awards.add(award);
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testFindMatchingAwards() throws ParseException {
        Specification<Award> awardSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);

        SearchInput input = new SearchInput();
        List<String> spendingRegion = new ArrayList();
        spendingRegion.add("spendingRegion");
        input.setSpendingRegion(spendingRegion);
        input.setSubsidyMeasureTitle("title");
        input.setLegalGrantingFromDate("2019-11-01");
        input.setLegalGrantingFromDate("2019-11-02");
        List<String> spendingSector = new ArrayList();
        spendingSector.add("spendingSector");
        input.setSpendingSector(spendingSector);
        input.setPageNumber(1);
        input.setTotalRecordsPerPage(1);
        List<String> subsidyInstruments= new ArrayList();
        subsidyInstruments.add("subsidyInstrument");
        input.setSubsidyInstrument(subsidyInstruments);

        List<String> subsidyObjectives= new ArrayList();
        subsidyObjectives.add("subsidyObjective");
        input.setSubsidyObjective(subsidyObjectives);
        input.setBeneficiaryName("bName");

        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(awards);
        SearchResults searchResults = sut.findMatchingAwards(input);
        assertThat(searchResults).isNotNull();
        verify(awardRepository, times(1)).findAll(any(Specification.class),any(Pageable.class));
    }

    @Test
    public void testFindMatchingAwardsWithOtherSubsidyObjAndInstrument() throws ParseException {
        Specification<Award> awardSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);

        SearchInput input = new SearchInput();
        List<String> spendingRegion = new ArrayList();
        spendingRegion.add("spendingRegion");
        input.setSpendingRegion(spendingRegion);
        input.setSubsidyMeasureTitle("title");
        List<String> spendingSector = new ArrayList();
        spendingSector.add("spendingSector");
        input.setSpendingSector(spendingSector);
        input.setPageNumber(1);
        input.setTotalRecordsPerPage(1);
        List<String> subsidyInstruments= new ArrayList();
        subsidyInstruments.add("Other-subsidyInstrument");
        input.setSubsidyInstrument(subsidyInstruments);

        List<String> subsidyObjectives= new ArrayList();
        subsidyObjectives.add("Other-subsidyObjective");
        input.setSubsidyObjective(subsidyObjectives);
        input.setBeneficiaryName("bName");

        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(awards);
        SearchResults searchResults = sut.findMatchingAwards(input);
        assertThat(searchResults).isNotNull();
        verify(awardRepository, times(1)).findAll(any(Specification.class),any(Pageable.class));
    }

    @Test
    public void testFindMatchingAwardsWithOnlyOther() throws ParseException {
        Specification<Award> awardSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);

        SearchInput input = new SearchInput();
        List<String> spendingRegion = new ArrayList();
        spendingRegion.add("spendingRegion");
        input.setSpendingRegion(spendingRegion);
        input.setSubsidyMeasureTitle("title");
        List<String> spendingSector = new ArrayList();
        spendingSector.add("spendingSector");
        input.setSpendingSector(spendingSector);
        input.setPageNumber(1);
        input.setTotalRecordsPerPage(1);
        List<String> subsidyInstruments= new ArrayList();
        subsidyInstruments.add("Other");
        input.setSubsidyInstrument(subsidyInstruments);

        List<String> subsidyObjectives= new ArrayList();
        subsidyObjectives.add("Other");
        input.setSubsidyObjective(subsidyObjectives);
        input.setBeneficiaryName("bName");

        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(awards);
        SearchResults searchResults = sut.findMatchingAwards(input);
        assertThat(searchResults).isNotNull();
        verify(awardRepository, times(1)).findAll(any(Specification.class),any(Pageable.class));
    }

    @Test
    public void testFindMatchingAwardsByBnameAndSortOrder() throws ParseException {
        Specification<Award> awardSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);

        SearchInput input = new SearchInput();
        input.setPageNumber(1);
        input.setTotalRecordsPerPage(1);
        input.setBeneficiaryName("bName");
        String[] itemsArray = new String[2];
        itemsArray[0]="beneficiary.beneficiaryName,asc";
        itemsArray[1]="spendingSector,desc";
        input.setSortBy(itemsArray);

        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(awards);
        SearchResults searchResults = sut.findMatchingAwards(input);
        assertThat(searchResults).isNotNull();
        verify(awardRepository, times(1)).findAll(any(Specification.class),any(Pageable.class));
    }

    @Test
    public void testFindMatchingAwardsShouldThrowSearchResultsNotFoundException() {
        Specification<Award> awardSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);

        SearchInput input = new SearchInput();
        input.setPageNumber(1);
        input.setTotalRecordsPerPage(1);
        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(new ArrayList<Award>());
        Assertions.assertThrows(SearchResultNotFoundException.class, () -> {
            sut.findMatchingAwards(input);
        });
    }

    @Test
    public void testGetAwardDetails() {
        Long awardNumber = new Long(12);
        when(awardRepository.findByAwardNumber(awardNumber)).thenReturn(award);
        AwardResponse response = sut.findByAwardNumber(awardNumber);
        assertThat(response).isNotNull();
        verify(awardRepository, times(1)).findByAwardNumber(awardNumber);
    }

    @Test
    public void testGetAwardDetailsThrowExceptionWhenAwardIsNull() {
        Long awardNumber = new Long(12);
        when(awardRepository.findByAwardNumber(awardNumber)).thenReturn(null);
        Assertions.assertThrows(SearchResultNotFoundException.class, () -> {
            sut.findByAwardNumber(awardNumber);
        });
    }
}
