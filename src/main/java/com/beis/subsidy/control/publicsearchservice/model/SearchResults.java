package com.beis.subsidy.control.publicsearchservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResults {

	public int totalSearchResults;
	public List<Award> awards;
	
}
