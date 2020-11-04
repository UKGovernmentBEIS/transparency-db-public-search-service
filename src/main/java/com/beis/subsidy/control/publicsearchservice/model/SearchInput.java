package com.beis.subsidy.control.publicsearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchInput {

	private String beneficiaryName;
	
	private String subsidyMeasureTitle;
	
	private String susidyObjective;
	
	private String spendingRegion;
	
}
