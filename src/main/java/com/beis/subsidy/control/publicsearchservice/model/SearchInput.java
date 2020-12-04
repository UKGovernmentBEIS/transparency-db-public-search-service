package com.beis.subsidy.control.publicsearchservice.model;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Search Input object - represents search input criteria for public search service
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchInput {

	private String beneficiaryName;
	
	private String subsidyMeasureTitle;
	
	private List<String> subsidyObjective;
	
	private List<String> spendingRegion;
	
	private List<String> subsidyInstrument;
	
	private List<String> spendingSector;
		
	private String legalGrantingFromDate;
	
	private String legalGrantingToDate;

	private int pageNumber;
	
	private int totalRecordsPerPage;
	
	private String[] sortBy;
	
}
