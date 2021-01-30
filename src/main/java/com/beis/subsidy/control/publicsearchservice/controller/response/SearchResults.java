package com.beis.subsidy.control.publicsearchservice.controller.response;

import java.util.List;
import java.util.stream.Collectors;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * Search results object - Represents search results for award search
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SearchResults {

	public long totalSearchResults;
	public int currentPage;
	public int totalPages;
	@JsonProperty
	private List<AwardResponse> awards;

	public SearchResults(List<Award> awards, long totalSearchResults,
						 int currentPage, int totalPages) {

		this.awards = awards.stream().map(award ->
				new AwardResponse(award, false)).collect(Collectors.toList());
		this.totalSearchResults = totalSearchResults;
		this.currentPage = currentPage;
		this.totalPages = totalPages;
	}
}
