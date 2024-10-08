package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;

public class AwardResponseTest {

    @Test
    public void testAwardResponse() {
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
        award.setSubsidyFullAmountExact(new BigDecimal(100000.0));
        award.setSpendingSector("spendingSector");
        award.setLegalGrantingDate(LocalDate.now());
        award.setStatus("status");
        award.setReason("rejectReason");
        award.setPublishedAwardDate(LocalDate.now());
        award.setSpendingRegion("region");
        award.setAuthorityURL("test.com");
        award.setAuthorityURLDescription("test.com description");
        award.setSpecificPolicyObjective("policy objective");
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
        subsidyMeasure.setSpecificPolicyObjective("policy objective for a scheme");
        subsidyMeasure.setBudget("1000000");
        subsidyMeasure.setPublishedMeasureDate(LocalDate.now());
        subsidyMeasure.setCreatedBy("SYSTEM");
        subsidyMeasure.setApprovedBy("SYSTEM");
        subsidyMeasure.setCreatedTimestamp(LocalDateTime.now());
        subsidyMeasure.setLastModifiedTimestamp(LocalDateTime.now());
        subsidyMeasure.setSchemeVersions(new ArrayList<>());

        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);
        award.setSubsidyMeasure(subsidyMeasure);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        award.setGrantingAuthority(grantingAuthority);
        subsidyMeasure.setGrantingAuthority(grantingAuthority);

        GrantingAuthorityResponse gaResponse = new GrantingAuthorityResponse(award.getGrantingAuthority());
        SubsidyMeasureResponse subsidyMeasureRes = new SubsidyMeasureResponse(award.getSubsidyMeasure(),true);
        BeneficiaryResponse beneficiaryResponse = new BeneficiaryResponse(award.getBeneficiary(),false);

        AwardResponse awardResponse = new AwardResponse(award, true);
        assertThat(awardResponse).isNotNull();
        assertThat(awardResponse.getApprovedBy()).isNotNull();
        assertThat(awardResponse.getAwardNumber()).isNotNull();
        assertThat(awardResponse.getBeneficiary()).isNotNull();
        assertThat(awardResponse.getCreatedBy()).isNotNull();
        assertThat(awardResponse.getCreatedTimestamp()).isNotNull();
        assertThat(awardResponse.getLastModifiedTimestamp()).isNotNull();
        assertThat(awardResponse.getGoodsServicesFilter()).isNotNull();
        assertThat(awardResponse.getGrantingAuthorityResponse()).isNotNull();
        assertThat(awardResponse.getLegalGrantingDate()).isNotNull();
        assertThat(awardResponse.getPublishedAwardDate()).isNotNull();
        assertThat(awardResponse.getSpendingSector()).isNotNull();
        assertThat(awardResponse.getStatus()).isNotNull();
        assertThat(awardResponse.getRejectReason()).isNotNull();
        assertThat(awardResponse.getSubsidyFullAmountExact()).isNotNull();
        assertThat(awardResponse.getSubsidyFullAmountRange()).isNotNull();
        assertThat(awardResponse.getSubsidyMeasure()).isNotNull();
        assertThat(awardResponse.getSubsidyObjective()).isNotNull();
        assertThat(awardResponse.getSubsidyInstrument()).isNotNull();
        assertThat(awardResponse.getSpendingRegion()).isNotNull();
        assertThat(awardResponse.getAuthorityURL()).isNull();
        assertThat(awardResponse.getAuthorityURLDescription()).isNull();
        assertThat(awardResponse.getSpecificPolicyObjective()).isNotNull();

        AwardResponse awardResponse1 = new AwardResponse(award, false);
        assertThat(awardResponse1).isNotNull();
        assertThat(awardResponse1.getApprovedBy()).isNull();
        assertThat(awardResponse1.getAwardNumber()).isNotNull();
        assertThat(awardResponse1.getBeneficiary()).isNotNull();
        assertThat(awardResponse1.getCreatedBy()).isNull();
        assertThat(awardResponse1.getCreatedTimestamp()).isNull();
        assertThat(awardResponse1.getLastModifiedTimestamp()).isNull();
        assertThat(awardResponse1.getGoodsServicesFilter()).isNull();
        assertThat(awardResponse1.getGrantingAuthorityResponse()).isNull();
        assertThat(awardResponse1.getLegalGrantingDate()).isNotNull();
        assertThat(awardResponse1.getPublishedAwardDate()).isNull();
        assertThat(awardResponse1.getLastModifiedTimestamp()).isNull();
        assertThat(awardResponse1.getSpendingSector()).isNotNull();
        assertThat(awardResponse1.getStatus()).isNotNull();
        assertThat(awardResponse1.getSpecificPolicyObjective()).isNotNull();
        assertThat(awardResponse1.getSubsidyFullAmountExact()).isNotNull();
        assertThat(awardResponse1.getSubsidyFullAmountRange()).isNotNull();
        assertThat(awardResponse1.getSubsidyMeasure()).isNotNull();
        assertThat(awardResponse1.getSubsidyObjective()).isNotNull();
        assertThat(awardResponse1.getRejectReason()).isNotNull();
        assertThat(awardResponse1.getAuthorityURL()).isNull();
        assertThat(awardResponse1.getAuthorityURLDescription()).isNull();
    }

    @Test
    public void testStandaloneAwardResponse() {
        Award award = new Award();
        award.setAwardNumber(1l);
        award.setApprovedBy("system");
        award.setStandaloneAward("Yes");
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
        award.setStatus("status");
        award.setReason("rejectReason");
        award.setPublishedAwardDate(LocalDate.now());
        award.setSpendingRegion("region");
        award.setSpecificPolicyObjective("policy objective");
        award.setAuthorityURL("test.com");
        award.setAuthorityURLDescription("test.com description");
        award.setSubsidyAwardInterest("Neither");
        award.setSpei("Yes");

        //beneficiary details
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryId(1l);
        beneficiary.setNationalId("nationalId");
        beneficiary.setBeneficiaryName("bName");
        beneficiary.setOrgSize("1");
        award.setBeneficiary(beneficiary);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        award.setGrantingAuthority(grantingAuthority);

        //AdminProgram details
        AdminProgram adminProgram = new AdminProgram();
        adminProgram.setApNumber("10");
        adminProgram.setAdminProgramName("Admin Program");
        award.setAdminProgram(adminProgram);

        GrantingAuthorityResponse gaResponse = new GrantingAuthorityResponse(award.getGrantingAuthority());
        BeneficiaryResponse beneficiaryResponse = new BeneficiaryResponse(award.getBeneficiary(),false);

        AwardResponse awardResponse = new AwardResponse(award, true);
        assertThat(awardResponse).isNotNull();
        assertThat(awardResponse.getApprovedBy()).isNotNull();
        assertThat(awardResponse.getAwardNumber()).isNotNull();
        assertThat(awardResponse.getBeneficiary()).isNotNull();
        assertThat(awardResponse.getCreatedBy()).isNotNull();
        assertThat(awardResponse.getCreatedTimestamp()).isNotNull();
        assertThat(awardResponse.getLastModifiedTimestamp()).isNotNull();
        assertThat(awardResponse.getGoodsServicesFilter()).isNotNull();
        assertThat(awardResponse.getGrantingAuthorityResponse()).isNotNull();
        assertThat(awardResponse.getLegalGrantingDate()).isNotNull();
        assertThat(awardResponse.getPublishedAwardDate()).isNotNull();
        assertThat(awardResponse.getSpendingSector()).isNotNull();
        assertThat(awardResponse.getStatus()).isNotNull();
        assertThat(awardResponse.getRejectReason()).isNotNull();
        assertThat(awardResponse.getSubsidyFullAmountExact()).isNotNull();
        assertThat(awardResponse.getSubsidyFullAmountRange()).isNotNull();
        assertThat(awardResponse.getSubsidyMeasure()).isNotNull();
        assertThat(awardResponse.getSubsidyObjective()).isNotNull();
        assertThat(awardResponse.getSubsidyInstrument()).isNotNull();
        assertThat(awardResponse.getSpendingRegion()).isNotNull();
        assertThat(awardResponse.getAuthorityURL()).isNotNull();
        assertThat(awardResponse.getAuthorityURLDescription()).isNotNull();
        assertThat(awardResponse.getSubsidyAwardInterest()).isNotNull();
        assertThat(awardResponse.getSpecificPolicyObjective()).isNotNull();
        assertThat(awardResponse.getSpei()).isNotNull();

        AwardResponse awardResponse1 = new AwardResponse(award, false);
        assertThat(awardResponse1).isNotNull();
        assertThat(awardResponse1.getApprovedBy()).isNull();
        assertThat(awardResponse1.getAwardNumber()).isNotNull();
        assertThat(awardResponse1.getBeneficiary()).isNotNull();
        assertThat(awardResponse1.getCreatedBy()).isNull();
        assertThat(awardResponse1.getCreatedTimestamp()).isNull();
        assertThat(awardResponse1.getLastModifiedTimestamp()).isNull();
        assertThat(awardResponse1.getGoodsServicesFilter()).isNull();
        assertThat(awardResponse1.getGrantingAuthorityResponse()).isNull();
        assertThat(awardResponse1.getLegalGrantingDate()).isNotNull();
        assertThat(awardResponse1.getPublishedAwardDate()).isNull();
        assertThat(awardResponse1.getLastModifiedTimestamp()).isNull();
        assertThat(awardResponse1.getSpendingSector()).isNotNull();
        assertThat(awardResponse1.getStatus()).isNotNull();
        assertThat(awardResponse1.getSubsidyFullAmountExact()).isNotNull();
        assertThat(awardResponse1.getSubsidyFullAmountRange()).isNotNull();
        assertThat(awardResponse1.getSubsidyMeasure()).isNotNull();
        assertThat(awardResponse1.getSubsidyObjective()).isNotNull();
        assertThat(awardResponse1.getSpecificPolicyObjective()).isNotNull();
        assertThat(awardResponse1.getRejectReason()).isNotNull();
        assertThat(awardResponse1.getAuthorityURL()).isNotNull();
        assertThat(awardResponse1.getAuthorityURLDescription()).isNotNull();
    }
}
