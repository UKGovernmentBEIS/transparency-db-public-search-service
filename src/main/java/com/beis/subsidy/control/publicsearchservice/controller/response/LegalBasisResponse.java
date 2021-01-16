package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.LegalBasis;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LegalBasisResponse {

    @JsonProperty
    private String legalBasisText;

    public LegalBasisResponse(LegalBasis legalBasis) {

        this.legalBasisText =  legalBasis.getLegalBasisText();
    }

}
