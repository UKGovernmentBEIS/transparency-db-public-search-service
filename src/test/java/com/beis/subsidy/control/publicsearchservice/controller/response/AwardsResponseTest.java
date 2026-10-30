package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class AwardsResponseTest {
    @Test
    public void testAwardsResponse() {
        //SubsidyMeasure
        SubsidyMeasure subsidyMeasure = new  SubsidyMeasure();
        subsidyMeasure.setScNumber("SC10001");
        subsidyMeasure.setSubsidyMeasureTitle("title");
        subsidyMeasure.setAdhoc(false);
        subsidyMeasure.setDuration(new BigInteger("200"));
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setGaSubsidyWebLink("www.BEIS.com");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStartDate(LocalDate.now());
        subsidyMeasure.setEndDate(LocalDate.now());
        subsidyMeasure.setCreatedTimestamp(LocalDateTime.now());
        subsidyMeasure.setLastModifiedTimestamp(LocalDateTime.now());
        subsidyMeasure.setBudget("1000000");
        subsidyMeasure.setPublishedMeasureDate(LocalDate.now());
        subsidyMeasure.setCreatedBy("SYSTEM");
        subsidyMeasure.setApprovedBy("SYSTEM");
        subsidyMeasure.setSubsidySchemeInterest("Neither");
        subsidyMeasure.setSchemeVersions(new ArrayList<>());

        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        subsidyMeasure.setGrantingAuthority(grantingAuthority);

        // Award details
        Award award = new Award();
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
        award.setSubsidyFullAmountExact(new BigDecimal("100000.0"));
        award.setSpendingSector("spendingSector");
        award.setLegalGrantingDate(LocalDate.now());
        award.setStatus("status");
        award.setReason("rejectReason");
        award.setPublishedAwardDate(LocalDate.now());
        award.setSpendingRegion("region");
        award.setAuthorityURL("test.com");
        award.setAuthorityURLDescription("test.com description");
        award.setSpecificPolicyObjective("policy objective");
        award.setSubsidyMeasure(subsidyMeasure);
        award.setGrantingAuthority(grantingAuthority);
        //beneficiary details
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryId(1l);
        beneficiary.setNationalId("nationalId");
        beneficiary.setBeneficiaryName("bName");
        beneficiary.setOrgSize("1");
        award.setBeneficiary(beneficiary);
        award.setLegalBasis("legal basis");

        List<Award> awardList = new ArrayList<>();

        awardList.add(award);

        AwardsResponse awardsResponse = new AwardsResponse(awardList, 1, 2, 3);
        assertThat(awardsResponse).isNotNull();
        assertThat(awardsResponse.getAwards().size()).isEqualTo(1);
        assertThat(awardsResponse.getTotalSearchResults()).isEqualTo(1);
        assertThat(awardsResponse.getCurrentPage()).isEqualTo(2);
        assertThat(awardsResponse.getTotalPages()).isEqualTo(3);
    }

    @Test
    public void testGeneratedConstructorsAndSetters() {
        AwardsResponse allArgsResponse = new AwardsResponse(
                4L, 5, 6, Collections.<AwardResponse>emptyList());

        assertThat(allArgsResponse.getTotalSearchResults()).isEqualTo(4L);
        assertThat(allArgsResponse.getCurrentPage()).isEqualTo(5);
        assertThat(allArgsResponse.getTotalPages()).isEqualTo(6);
        assertThat(allArgsResponse.getAwards()).isEmpty();

        AwardsResponse noArgsResponse = new AwardsResponse();
        noArgsResponse.setTotalSearchResults(7L);
        noArgsResponse.setCurrentPage(8);
        noArgsResponse.setTotalPages(9);
        noArgsResponse.setAwards(Collections.<AwardResponse>emptyList());

        assertThat(noArgsResponse.getTotalSearchResults()).isEqualTo(7L);
        assertThat(noArgsResponse.getCurrentPage()).isEqualTo(8);
        assertThat(noArgsResponse.getTotalPages()).isEqualTo(9);
        assertThat(noArgsResponse.getAwards()).isEmpty();
    }
}