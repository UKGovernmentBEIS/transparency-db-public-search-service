package com.beis.subsidy.control.publicsearchservice.service;

import static org.springframework.data.domain.ExampleMatcher.GenericPropertyMatchers.exact;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import com.beis.subsidy.control.publicsearchservice.model.SearchInput;
import com.beis.subsidy.control.publicsearchservice.model.SearchResults;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;

@Service
public class SearchService {

	@Autowired
	private AwardRepository awardRepository;
	
	
	public SearchResults findMatchingAwards(SearchInput searchinput) {
		
		ExampleMatcher searchMatcher = ExampleMatcher
				.matchingAll()
				.withMatcher("beneficiaryName", exact())
				.withMatcher("subsidyMeasureTitle", exact())
				.withMatcher("susidyObjective", exact())
				.withMatcher("spendingRegion", exact())	;
			

			Award awardExample = Award
				.builder()
				.subsidyObjective((searchinput.getSusidyObjective() == null || searchinput.getSusidyObjective().isEmpty())  ? null : searchinput.getSusidyObjective().trim() )
				.spendingRegion((searchinput.getSpendingRegion() == null || searchinput.getSpendingRegion().isEmpty())  ? null : searchinput.getSpendingRegion().trim())
				.beneficiary((searchinput.getBeneficiaryName() == null || searchinput.getBeneficiaryName().isEmpty())  ? null : Beneficiary.builder().beneficiaryName(searchinput.getBeneficiaryName().trim()).build())
				.subsidyMeasure((searchinput.getSubsidyMeasureTitle() == null || searchinput.getSubsidyMeasureTitle().isEmpty())  ? null : SubsidyMeasure.builder().subsidyMeasureTitle(searchinput.getSubsidyMeasureTitle().trim()).build())
				.build();
			
			List<Award> awards = awardRepository.findAll(Example.of(awardExample, searchMatcher.withIgnoreCase()));
			
			return (null != awards) ? SearchResults.builder().awards(awards).totalSearchResults(awards.size()).build() : null;
			
	}

	
	/* Commenting below method - as the requirements need to be discussed and later it will be used for impl. 

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
	*/
	
	public List<Award> getAllAwards() {
		return awardRepository.findAll();
	}


	
	
	
}
