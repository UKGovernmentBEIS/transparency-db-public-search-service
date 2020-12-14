package com.beis.subsidy.control.publicsearchservice.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Search results object - Represents search results for award search
 *
 */
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResults {

	public long totalSearchResults;
	public int currentPage;
	public int totalPages;
	@JsonProperty
	private List<AwardResponse> award;

	public SearchResults(List<Award> awards, long totalSearchResults,
						 int currentPage, int totalPages) {

		this.award = awards.stream().map(award ->
				new AwardResponse(award, true)).collect(Collectors.toList());
		this.totalSearchResults = totalSearchResults;
		this.currentPage = currentPage;
		this.totalPages = totalPages;
	}
}