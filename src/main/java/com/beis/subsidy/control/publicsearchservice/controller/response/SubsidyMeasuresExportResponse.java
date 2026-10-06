package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class SubsidyMeasuresExportResponse {

    @JsonProperty
    private List<SubsidyMeasureResponse> schemes;

    public SubsidyMeasuresExportResponse(List<SubsidyMeasure> schemes) {

        this.schemes = schemes.stream().map(scheme ->
                new SubsidyMeasureResponse(scheme, true)).collect(Collectors.toList());
    }
}
