package com.beis.subsidy.control.publicsearchservice.controller.request;

import java.util.List;

import lombok.Setter;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 
 * Search Input object - represents search input criteria for public search service
 *
 */
@NoArgsConstructor
@Setter
@Getter
public class SearchInput {

	private String beneficiaryName;
	
	private String subsidyMeasureTitle;
	
	private List<String> subsidyObjective;

	private List<String> otherSubsidyObjective;
	
	private List<String> spendingRegion;
	
	private List<String> subsidyInstrument;

	private List<String> otherSubsidyInstrument;
	
	private List<String> spendingSector;

	private String legalGrantingDate;

	private String legalGrantingFromDate;
	
	private String legalGrantingToDate;

	private int pageNumber;
	
	private int totalRecordsPerPage;
	
	private String[] sortBy;
	
}
