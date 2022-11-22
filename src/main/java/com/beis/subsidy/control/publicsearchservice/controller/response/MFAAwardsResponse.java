package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Getter
@AllArgsConstructor
@NoArgsConstructor
public class MFAAwardsResponse {
    public long totalSearchResults;
    public int currentPage;
    public int totalPages;
    @JsonProperty
    public List<MFAAwardResponse> mfaAwards;

    public MFAAwardsResponse(List<MFAAward> mfaAwards, long totalSearchResults,
                             int currentPage, int totalPages) {
        this.mfaAwards = mfaAwards.stream().map(mfaAward ->
                new MFAAwardResponse(mfaAward)).collect(Collectors.toList());
        this.totalSearchResults = totalSearchResults;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}