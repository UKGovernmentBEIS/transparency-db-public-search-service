package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class AwardsExportResponse {

    @JsonProperty
    private List<AwardResponse> awards;

    public AwardsExportResponse(List<Award> awards) {

        this.awards = awards.stream().map(award ->
                new AwardResponse(award, true)).collect(Collectors.toList());
    }
}
