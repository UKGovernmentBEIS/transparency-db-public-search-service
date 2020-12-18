package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BeneficiaryResponse {

    @JsonProperty
    private String beneficiaryName;
    @JsonProperty
    private String nationalId;
    @JsonProperty
    private String nationalIdType;
    @JsonProperty
    private String orgSize;
    @JsonProperty
    private String region;

    public BeneficiaryResponse(Beneficiary beneficiary, boolean flag) {

        this.beneficiaryName  = beneficiary.getBeneficiaryName();
        if (flag) {
            this.nationalId = beneficiary.getNationalId();
            this.nationalIdType = beneficiary.getNationalIdType();
            this.orgSize = beneficiary.getOrgSize();
            this.region = beneficiary.getRegion();
        }
    }
}
