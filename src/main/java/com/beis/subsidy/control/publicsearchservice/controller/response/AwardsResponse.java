package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 
 * Search results object - Represents search results for award search
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AwardsResponse {

	public long totalSearchResults;
	public int currentPage;
	public int totalPages;
	@JsonProperty
	private List<AwardResponse> awards;

	public AwardsResponse(List<Award> awards, long totalSearchResults,
                          int currentPage, int totalPages) {

		this.awards = awards.stream().map(award ->
				new AwardResponse(award, true)).collect(Collectors.toList());
		this.totalSearchResults = totalSearchResults;
		this.currentPage = currentPage;
		this.totalPages = totalPages;
	}
}
