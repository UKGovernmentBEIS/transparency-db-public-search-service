package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchResultsTest {

    List<Award> awards = new ArrayList<>();

    @Test
    public void testSearchResults() {
        Award award = new Award();
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
        SubsidyMeasure subsidyMeasure = new SubsidyMeasure();
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
        award.setSubsidyMeasure(subsidyMeasure);

        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        award.setGrantingAuthority(grantingAuthority);

        awards.add(award);

        SearchResults results = new SearchResults(awards, 1,1,1);
        assertThat(results).isNotNull();
        assertThat(results.getAwards()).isNotNull();
        assertThat(results.getAwards().size()).isEqualTo(1);
        assertThat(results.getCurrentPage()).isEqualTo(1l);
        assertThat(results.getTotalPages()).isEqualTo(1l);
        assertThat(results.getTotalSearchResults()).isEqualTo(1l);

        SearchResults resultsDef = new SearchResults();
        AwardResponse response = new AwardResponse(award, false);
        List<AwardResponse> responses = new ArrayList<>();
        responses.add(response);
        resultsDef.setAwards(responses);
        resultsDef.setCurrentPage(1);
        resultsDef.setTotalPages(1);
        resultsDef.setTotalSearchResults(1);

        assertThat(resultsDef.getAwards().size()).isEqualTo(1);
        assertThat(resultsDef.getCurrentPage()).isEqualTo(1l);
        assertThat(resultsDef.getTotalPages()).isEqualTo(1l);
        assertThat(resultsDef.getTotalSearchResults()).isEqualTo(1l);
    }
}
