package com.beis.subsidy.control.publicsearchservice.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.SearchInput;
import com.beis.subsidy.control.publicsearchservice.model.SearchResults;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;

/**
 * Service class for Public Search service 
 *
 */
@Service
public class SearchService {

	@Autowired
	private AwardRepository awardRepository;
	
	/**
	 * To return matching awards based on search inputs 
	 * @param searchinput - Search input object, that contains search criteria
	 * @return SearchResults - Returns search result based on search criteria
	 */
	public SearchResults findMatchingAwards(SearchInput searchinput) throws ParseException{
		
		
		Specification<Award> awardSpecifications = Specification
			    
				// getSubsidyObjective from input parameter
			    .where(
			    		searchinput.getSubsidyObjective() == null || searchinput.getSubsidyObjective().isEmpty()
			    			? null : AwardSpecifications.subsidyObjectiveIn(searchinput.getSubsidyObjective()))
			    
			    // getSpendingRegion from input parameter
			    .and(searchinput.getSpendingRegion() == null || searchinput.getSpendingRegion().isEmpty()
    						? null : AwardSpecifications.spendingRegionIn(searchinput.getSpendingRegion()))
			    
			    // getSpendingSector from input parameter
			    .and(searchinput.getSpendingSector() == null || searchinput.getSpendingSector().isEmpty()  
    						? null : AwardSpecifications.spendingSectorIn(searchinput.getSpendingSector()))
			    
			    // getSubsidyInstrument from input parameter
			    .and(searchinput.getSubsidyInstrument() == null || searchinput.getSubsidyInstrument().isEmpty()
    						? null : AwardSpecifications.subsidyInstrumentIn(searchinput.getSubsidyInstrument()))
			    
			    // getSubsidyMeasureTitle from input parameter
			    .and(SearchUtils.checkNullorEmptyString(searchinput.getSubsidyMeasureTitle())  
						? null : AwardSpecifications.subsidyMeasureTitle(searchinput.getSubsidyMeasureTitle().trim()))
			    
			    // LegalGranting Date Range from/to input parameter
			    .and(SearchUtils.checkNullorEmptyString(searchinput.getLegalGrantingFromDate()) 
			    		|| SearchUtils.checkNullorEmptyString(searchinput.getLegalGrantingToDate()) 
						? null : AwardSpecifications
								.legalGrantingDateRange(
										SearchUtils.stringToDate(searchinput.getLegalGrantingFromDate()), 
										SearchUtils.stringToDate(searchinput.getLegalGrantingToDate())
										))
			    
			    // getBeneficiaryName from input parameter
			    .and(SearchUtils.checkNullorEmptyString(searchinput.getBeneficiaryName())  
						? null : AwardSpecifications.beneficiaryName(searchinput.getBeneficiaryName().trim()))
			    ;
			

			//TODO - re-factor below to separate method
			List<Order> orders = getOrderByCondition(searchinput.getSortBy());
			
			Pageable pagingSortAwards = PageRequest.of(searchinput.getPageNumber() - 1, searchinput.getTotalRecordsPerPage(), Sort.by(orders));
			
			Page<Award> pageAwards = awardRepository.findAll(awardSpecifications, pagingSortAwards);
			
			List<Award> awardResults = pageAwards.getContent();
			
			SearchResults searchResults = null; 
			
			if(!awardResults.isEmpty()) {
				
					searchResults = SearchResults.builder()
						.awards(awardResults)
						.totalSearchResults(pageAwards.getTotalElements())
						.currentPage(pageAwards.getNumber() + 1) //Added based on UI request
						.totalPages(pageAwards.getTotalPages())
						.build();
			
			}
			
			return searchResults;
	}

	/**
	 * 
	 * @param sortBy - Array of string with format "field, direction" - for example, "beneficiaryName, asc"
	 * @return List<Order> - List of order
	 */
	private List<Order> getOrderByCondition(String[] sortBy) {
		
		List<Order> orders = new ArrayList<Order>();

		  if (sortBy != null && sortBy.length > 0 && sortBy[0].contains(",")) {
	        // will sort more than 2 fields
	        // sortOrder="field, direction"
	        for (String sortOrder : sortBy) {
	          String[] _sort = sortOrder.split(",");
	          orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
	        }
	      } else {
	    	//Default sort - Legal Granting Date with recent one at top	
	        orders.add(new Order(getSortDirection("desc"), "legalGrantingDate"));
	      }

	      System.out.println("oders in service" + orders); 
		return orders;
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
	
	/**
	 * To get all awards from transparency database
	 * @return List<Award> - Returns list of awards
	 */
	public List<Award> getAllAwards() {
		return awardRepository.findAll();
	}

	/**
	 * 
	 * @param direction - direction of sort
	 * @return Sort.Direction - sort direction
	 */
	private Sort.Direction getSortDirection(String direction) {
	    
		if (direction.equals("asc")) {
	      return Sort.Direction.ASC;
	    } else if (direction.equals("desc")) {
	      return Sort.Direction.DESC;
	    }

	    return Sort.Direction.ASC;
	  }

	
	
	
}
