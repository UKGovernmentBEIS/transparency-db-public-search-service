package com.beis.subsidy.control.publicsearchservice.service.impl;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.*;
import com.beis.subsidy.control.publicsearchservice.exception.SearchResultNotFoundException;
import com.beis.subsidy.control.publicsearchservice.model.*;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import com.beis.subsidy.control.publicsearchservice.repository.MFAAwardRepository;
import com.beis.subsidy.control.publicsearchservice.repository.SubsidyMeasureRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

public class SearchServiceImplTest {

    private final AwardRepository awardRepository = mock(AwardRepository.class);
    private final SubsidyMeasureRepository subsidyMeasureRepositoryMock = mock(SubsidyMeasureRepository.class);
    private final AwardRepository awardRepositoryMock = mock(AwardRepository.class);
    private final PageRequest pageRequestMock = mock(PageRequest.class);
    @Mock
    MFAAwardRepository mfaAwardRepositoryMock;

    @InjectMocks
    private SearchServiceImpl sut;

    Award award;
    SubsidyMeasure subsidyMeasure;
    SubsidyMeasure subsidyMeasureWithoutAwards;
    List<Award> awards = new ArrayList<>();
    List<SubsidyMeasure> subsidyMeasures = new ArrayList<>();
    List<MFAAward> mfaAwards = new ArrayList<>();

    MFAAward mfaAward;

    @BeforeEach
    public void setUp() {

        award = new Award();
        award.setAwardNumber(1l);
        award.setApprovedBy("system");
        award.setCreatedBy("system");
        award.setCreatedTimestamp(LocalDate.now());
        award.setLastModifiedTimestamp(LocalDate.now());
        award.setGoodsServicesFilter("serviceFilter");
        award.setLegalGrantingDate(LocalDate.now());
        award.setSubsidyFullAmountRange("5000");
        award.setSubsidyInstrument("subsidyInstrument");
        award.setSubsidyObjective("subsidyObj");
        award.setSubsidyFullAmountExact(new BigDecimal(100000.0));
        award.setSpendingSector("spendingSector");
        award.setLegalGrantingDate(LocalDate.now());
        award.setStatus("Active");
        award.setPublishedAwardDate(LocalDate.now());
        //beneficiary details
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryId(1l);
        beneficiary.setNationalId("nationalId");
        beneficiary.setBeneficiaryName("bName");
        beneficiary.setOrgSize("1");
        award.setBeneficiary(beneficiary);

        //SubsidyMeasure
        subsidyMeasure = getSubsidyMeasure();

        subsidyMeasureWithoutAwards = getSubsidyMeasure();
        // Legal Basis text
        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);
        subsidyMeasureWithoutAwards.setLegalBases(legalBasis);

        award.setSubsidyMeasure(subsidyMeasure);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        award.setGrantingAuthority(grantingAuthority);
        awards.add(award);
        subsidyMeasure.setGrantingAuthority(grantingAuthority);
        subsidyMeasureWithoutAwards.setGrantingAuthority(grantingAuthority);
        MockitoAnnotations.openMocks(this);

        subsidyMeasures.add(subsidyMeasure);

        // MFA Awards
        mfaAward = new MFAAward();
        mfaAward.setMfaAwardNumber(1L);
        mfaAward.setAwardAmount(BigDecimal.valueOf(1000));
        mfaAward.setGrantingAuthority(grantingAuthority);
        mfaAward.setCreatedTimestamp(LocalDateTime.now());
        mfaAward.setLastModifiedTimestamp(LocalDateTime.now());

        mfaAwards.add(mfaAward);
    }

    @Test
    public void testFindMatchingAwards() throws ParseException {
        Specification<Award> awardSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);

        SearchInput input = getAwardSearchInput();

        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(awards);
        SearchResults searchResults = sut.findMatchingAwards(input);
        assertThat(searchResults).isNotNull();
        verify(awardRepository, times(1)).findAll(any(Specification.class),any(Pageable.class));

        input.setLegalGrantingFromDate("2000-10-10");
        input.setLegalGrantingToDate("2010-10-10");
        searchResults = sut.findMatchingAwards(input);
        assertThat(searchResults).isNotNull();

        input.setSubsidyInstrument(Arrays.asList("Other-"));
        searchResults = sut.findMatchingAwards(input);
        assertThat(searchResults).isNotNull();
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

    @Test
    public void testFindSchemeByScNumber(){
        SubsidyMeasureResponse smr = new SubsidyMeasureResponse(subsidyMeasure, true);
        when(subsidyMeasureRepositoryMock.findByScNumber(Mockito.any(String.class))).thenReturn(subsidyMeasure);

        SubsidyMeasureResponse actual = sut.findSchemeByScNumber("SC10001");
        assertThat(actual.getScNumber()).isEqualTo("SC10001");

        when(subsidyMeasureRepositoryMock.findByScNumber(Mockito.any(String.class))).thenReturn(null);
        Exception exception = assertThrows(SearchResultNotFoundException.class, () -> sut.findSchemeByScNumber("SC99999"));

        String expectedMessage = "Scheme NotFound";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    public void testFindSchemeByScNumberWithAwards(){
        when(subsidyMeasureRepositoryMock.findByScNumber(Mockito.any(String.class))).thenReturn(subsidyMeasure);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);
        SearchInput input = getAwardSearchInput();

        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(awards);
        SubsidyMeasureResponse subsidyMeasureResponse = sut.findSchemeByScNumberWithAwards(subsidyMeasure.getScNumber(),input);
        assertThat(subsidyMeasureResponse).isNotNull();
        assertThat(subsidyMeasureResponse.getAwardSearchResults()).isNotNull();
        assertThat(subsidyMeasureResponse.getAwardSearchResults()).isInstanceOf(SearchResults.class);
        assertThat(subsidyMeasureResponse.getAwardSearchResults().getAwards() == Arrays.asList(new AwardResponse(award,true)));

        when(subsidyMeasureRepositoryMock.findByScNumber(Mockito.any(String.class))).thenReturn(null);
        Exception exception = assertThrows(SearchResultNotFoundException.class, () -> sut.findSchemeByScNumberWithAwards("SC99999", input));
        String expectedMessage = "Scheme NotFound";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

        when(awardPage.getContent()).thenReturn(new ArrayList<Award>());
        when(subsidyMeasureRepositoryMock.findByScNumber(Mockito.any(String.class))).thenReturn(subsidyMeasureWithoutAwards);
        assertThat(sut.findSchemeByScNumberWithAwards("SC10001", input).getAwardSearchResults()).isNull();

        assertTrue(actualMessage.contains(expectedMessage));
    }

    private static SearchInput getAwardSearchInput() {
        SearchInput searchInput = new SearchInput();
        List<String> spendingRegion = new ArrayList();
        spendingRegion.add("spendingRegion");
        searchInput.setSpendingRegion(spendingRegion);
        searchInput.setSubsidyMeasureTitle("title");
        searchInput.setLegalGrantingFromDate("2019-11-01");
        searchInput.setLegalGrantingFromDate("2019-11-02");
        List<String> spendingSector = new ArrayList();
        spendingSector.add("spendingSector");
        searchInput.setSpendingSector(spendingSector);
        searchInput.setPageNumber(1);
        searchInput.setTotalRecordsPerPage(1);
        List<String> subsidyInstruments= new ArrayList();
        subsidyInstruments.add("subsidyInstrument");
        searchInput.setSubsidyInstrument(subsidyInstruments);
        searchInput.setBeneficiaryName("bName");
        return searchInput;
    }

    private static SearchInput getSchemeSearchInput() {
        SearchInput searchInput = new SearchInput();
        String[] sortBy = new String[1];
        sortBy[0] = "scNumber,desc";
        searchInput.setSortBy(sortBy);
        searchInput.setPageNumber(1);
        searchInput.setTotalRecordsPerPage(1);
        searchInput.setScNumber("SC10001");
        searchInput.setSubsidyMeasureTitle("Title");
        searchInput.setGrantingAuthorityName("GA Name");
        searchInput.setSubsidyStartDateFrom(LocalDate.now());
        searchInput.setSubsidyStartDateTo(LocalDate.now());
        searchInput.setSubsidyEndDateFrom(LocalDate.now());
        searchInput.setSubsidyEndDateTo(LocalDate.now());
        searchInput.setSubsidyStatus("active");
        searchInput.setAdHoc("no");
        searchInput.setBudgetFrom(new BigDecimal("500"));
        searchInput.setBudgetTo(new BigDecimal("1000"));
        return searchInput;
    }

    private static SearchInput getMfaAwardSearchInput()
    {
        SearchInput searchInput = new SearchInput();
        String[] sortBy = new String[1];
        sortBy[0] = "mfaAwardNumber,desc";

        searchInput.setSortBy(sortBy);
        searchInput.setPageNumber(1);
        searchInput.setTotalRecordsPerPage(1);

        searchInput.setMfaAwardNumber("1");
        searchInput.setGrantingAuthorityName("TEST GA");
        searchInput.setSubsidyStartDateFrom(LocalDate.now());
        searchInput.setSubsidyStartDateTo(LocalDate.now());
        searchInput.setIsSpei(false);
        searchInput.setBudgetFrom(BigDecimal.valueOf(100000));
        searchInput.setBudgetTo(BigDecimal.valueOf(200000));
        searchInput.setSubsidyStatus("Published");
        searchInput.setBeneficiaryName("Recipient");
        searchInput.setMfaGroupingName("MFA Grouping Name");
        return searchInput;
    }

    private static SubsidyMeasure getSubsidyMeasure()
    {
        SubsidyMeasure subsidyMeasure = new SubsidyMeasure();
        subsidyMeasure.setScNumber(String.format("SC10001"));
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
        subsidyMeasure.setBudget("1000000");
        subsidyMeasure.setPublishedMeasureDate(LocalDate.now());
        subsidyMeasure.setCreatedBy("SYSTEM");
        subsidyMeasure.setApprovedBy("SYSTEM");
        subsidyMeasure.setCreatedTimestamp(new Date());
        subsidyMeasure.setLastModifiedTimestamp(new Date());

        return subsidyMeasure;
    }

    @Test
    public void testFindAllSchemes(){
        Specification<SubsidyMeasure> smSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<SubsidyMeasure> smPage = (Page<SubsidyMeasure>) mock(Page.class);

        SearchInput searchInput = getSchemeSearchInput();

        when(subsidyMeasureRepositoryMock.findAll(any(Specification.class),any(Pageable.class))).thenReturn(smPage);
        when(smPage.getContent()).thenReturn(subsidyMeasures);

        SubsidyMeasuresResponse actual = sut.findAllSchemes(searchInput);
        assertThat(actual).isNotNull();
        verify(subsidyMeasureRepositoryMock, times(1)).findAll(any(Specification.class),any(Pageable.class));

        searchInput.setScNumber("");
        searchInput.setSubsidyMeasureTitle("");
        searchInput.setGrantingAuthorityName("");
        searchInput.setSubsidyStatus("");
        searchInput.setBudgetFrom(null);
        searchInput.setBudgetTo(null);
        searchInput.setSubsidyStartDateFrom(null);
        searchInput.setSubsidyStartDateTo(null);
        searchInput.setSubsidyEndDateFrom(null);
        searchInput.setSubsidyEndDateTo(null);

        actual = sut.findAllSchemes(searchInput);
        assertThat(actual).isNotNull();
    }

    @Test
    public void testFindAllMfaAwards(){
        SearchInput searchInput = getMfaAwardSearchInput();
        Page<MFAAward> mfaAwardPage = (Page<MFAAward>) mock(Page.class);

        when(mfaAwardRepositoryMock.findAll(any(Specification.class),any(Pageable.class))).thenReturn(mfaAwardPage);
        when(mfaAwardPage.getContent()).thenReturn(mfaAwards);

        MFAAwardsResponse results = sut.findMatchingMfaAwards(searchInput);
        assertThat(results.mfaAwards.size()).isGreaterThan(0);
        assertThat(results.mfaAwards.get(0).getMfaAwardNumber()).isEqualTo(mfaAward.getMfaAwardNumber());
    }

    @Test
    public void testFindMfaAwardByNumber()
    {
        when(mfaAwardRepositoryMock.findByMfaAwardNumber(any(Long.class))).thenReturn(mfaAward);
        assertThat(sut.findMfaByAwardNumber(1L).getMfaAwardNumber()).isEqualTo(mfaAward.getMfaAwardNumber());

        when(mfaAwardRepositoryMock.findByMfaAwardNumber(any(Long.class))).thenReturn(null);
        Exception exception = assertThrows(SearchResultNotFoundException.class, () -> sut.findMfaByAwardNumber(1L));

        String expectedMessage = "AwardResults NotFound";
        String actualMessage = exception.getMessage();
        assertTrue(actualMessage.contains(expectedMessage));

    }

    @Test
    public void testFindStandaloneAwards(){
        Specification<Award> awardSpecifications = mock(Specification.class);
        Pageable pageableMock = mock(Pageable.class);
        Page<Award> awardPage = (Page<Award>) mock(Page.class);

        SearchInput input = getAwardSearchInput();
        input.setSubsidyInstrument(Arrays.asList("Other-"));
        input.setSubsidyObjective(Arrays.asList("Other-"));
        when(awardRepository.findAll(any(Specification.class),any(Pageable.class))).thenReturn(awardPage);
        when(awardPage.getContent()).thenReturn(awards);
        SearchResults searchResults = sut.findStandaloneAwards(input);
        assertThat(searchResults).isNotNull();
        verify(awardRepository, times(1)).findAll(any(Specification.class),any(Pageable.class));

        input.setSubsidyObjective(Arrays.asList("Other-Objective"));
        input.setOtherSubsidyObjective(Arrays.asList("ObjectiveOther"));
        input.setSubsidyInstrument(Arrays.asList("Other-Instrument"));
        input.setOtherSubsidyInstrument(Arrays.asList("InstrumentOther"));

        searchResults = sut.findStandaloneAwards(input);
        assertThat(searchResults).isNotNull();
    }
}
