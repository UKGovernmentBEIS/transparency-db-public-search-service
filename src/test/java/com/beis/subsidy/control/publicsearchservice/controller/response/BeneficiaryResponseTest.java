package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BeneficiaryResponseTest {

    @Test
    public void testBeneficiaryResponse() {
        Award award = new Award();
        //beneficiary details
        Beneficiary beneficiary = new Beneficiary();
        beneficiary.setBeneficiaryId(1l);
        beneficiary.setNationalId("nationalId");
        beneficiary.setNationalIdType("nationalIdType");
        beneficiary.setBeneficiaryName("bName");
        beneficiary.setOrgSize("1");
        award.setBeneficiary(beneficiary);
        BeneficiaryResponse beneficiaryResponse = new BeneficiaryResponse(award.getBeneficiary(),false);
        assertThat(beneficiaryResponse).isNotNull();
        assertThat(beneficiaryResponse.getBeneficiaryName()).isNotNull();
        assertThat(beneficiaryResponse.getNationalIdType()).isNull();
        assertThat(beneficiaryResponse.getNationalId()).isNull();
        assertThat(beneficiaryResponse.getNationalIdType()).isNull();
        assertThat(beneficiaryResponse.getOrgSize()).isNull();

        BeneficiaryResponse beneficiaryResponse1 = new BeneficiaryResponse(award.getBeneficiary(),true);
        assertThat(beneficiaryResponse1).isNotNull();
        assertThat(beneficiaryResponse1.getBeneficiaryName()).isNotNull();
        assertThat(beneficiaryResponse1.getNationalIdType()).isNotNull();
        assertThat(beneficiaryResponse1.getNationalId()).isNotNull();
        assertThat(beneficiaryResponse1.getNationalIdType()).isNotNull();

    }
}
