package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class MFAAwardsExportResponse {

    @JsonProperty
    private List<MFAAwardResponse> mfaAwards;

    public MFAAwardsExportResponse(List<MFAAward> mfaAwards) {

        this.mfaAwards = mfaAwards.stream().map(MFAAwardResponse::new).collect(Collectors.toList());
    }
}
