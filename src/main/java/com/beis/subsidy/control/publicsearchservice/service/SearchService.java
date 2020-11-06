package com.beis.subsidy.control.publicsearchservice.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.contains;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.SearchInput;
import com.beis.subsidy.control.publicsearchservice.model.SearchResults;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;
import com.beis.subsidy.control.publicsearchservice.repository.SubsidyMeasureRepository;

@Service
public class SearchService {

	@Autowired
	private AwardRepository awardRepository;
	
	@Autowired
	private GrantingAuthorityRepository gaRepository;
	
	@Autowired
	private SubsidyMeasureRepository smRepository;
	
	public SearchResults findMatchingAwards(SearchInput searchinput) {

		ExampleMatcher searchMatcher = ExampleMatcher
				.matchingAll()
				.withMatcher("beneficiaryName", contains().ignoreCase())
				.withMatcher("subsidyMeasureTitle", contains().ignoreCase())
				.withMatcher("susidyObjective", contains().ignoreCase())
				.withMatcher("spendingRegion", contains().ignoreCase());
			

			Award awardExample = Award
				.builder()
				.subsidyObjective(searchinput.getSusidyObjective())
				.spendingRegion(searchinput.getSpendingRegion())
				.beneficiary(Beneficiary.builder().beneficiaryName(searchinput.getBeneficiaryName()).build())
				.subsidyMeasure(SubsidyMeasure.builder().subsidyMeasureTitle(searchinput.getSubsidyMeasureTitle()).build())
				.build();
			
			List<Award> awards = awardRepository.findAll(Example.of(awardExample, searchMatcher));
			
			return (null != awards) ? SearchResults.builder().awards(awards).totalSearchResults(awards.size()).build() : null;
			
	}

	public List<SubsidyMeasure> getAllSubsidyMeasures() {
		return smRepository.findAll();
	}

	public List<GrantingAuthority> findMatchingGrantingAuthorities(String name, String legalBasis, String status) {
		
		ExampleMatcher matcher = ExampleMatcher
			.matchingAll()
			.withMatcher("grantingAuthorityName", contains().ignoreCase())
			.withMatcher("legalBasis", contains().ignoreCase())
			.withMatcher("status", contains().ignoreCase());
		

		GrantingAuthority example = GrantingAuthority
			.builder()
			.grantingAuthorityName(name)
			.legalBasis(legalBasis)
			.status(status)
			.build();
		
		return gaRepository.findAll(Example.of(example, matcher));
	}
	
	public List<Award> getAllAwards() {
		return awardRepository.findAll();
	}


	
	
	
}
