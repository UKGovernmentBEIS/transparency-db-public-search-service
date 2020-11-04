package com.beis.subsidy.control.publicsearchservice.service;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.beis.subsidy.control.publicsearchservice.model.SearchInput;
import com.beis.subsidy.control.publicsearchservice.model.SearchResults;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import com.beis.subsidy.control.publicsearchservice.repository.BeneficiaryRepository;
import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;
import com.beis.subsidy.control.publicsearchservice.repository.SubsidyMeasureRepository;

@Service
public class SearchService {

	private static final String CONSTANT_SYSTEM = "SYSTEM";
	
	@Autowired
	private AwardRepository awardRepository;
	
	@Autowired
	private BeneficiaryRepository beneficiaryRepository;
	
	@Autowired
	private GrantingAuthorityRepository gaRepository;
	
	@Autowired
	private SubsidyMeasureRepository smRepository;
	
	public List<SearchResults> getSearchResults(SearchInput seachinput) {
		//TODO - Change below to take in search input
		//TODO - add predicates for Criteria API - one for each search criteria
		//TODO - Build dynamic Query
		//TODO - See if View can speed things up here??
		return Arrays.asList(new SearchResults());
	}

	public List<SubsidyMeasure> getAllSubsidyMeasures() {
		return smRepository.findAll();
	}

	
	
	
}
