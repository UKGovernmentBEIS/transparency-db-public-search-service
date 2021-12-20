package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Subsidy Schemes object - Represents list of subsidy schemes (subsidy measures)
 *
 */

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SubsidyMeasuresResponse {
    public long totalResults;
    public int currentPage;
    public int totalPages;
    public List<SubsidyMeasureResponse> subsidySchemes;

    public SubsidyMeasuresResponse(List<SubsidyMeasure> schemes, long totalResults,
                                   int currentPage, int totalPages) {
        this.subsidySchemes = schemes.stream().map(scheme ->
                new SubsidyMeasureResponse(scheme, false)).collect(Collectors.toList());
        this.totalResults = totalResults;
        this.currentPage = currentPage;
        this.totalPages = totalPages;
    }
}
