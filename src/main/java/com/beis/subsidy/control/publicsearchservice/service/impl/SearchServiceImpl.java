package com.beis.subsidy.control.publicsearchservice.service.impl;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.exception.SearchResultNotFoundException;
import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import com.beis.subsidy.control.publicsearchservice.utils.AwardSpecificationUtils;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Public Search service 
 *
 */
@Service
public class SearchServiceImpl implements SearchService {

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
			    			? null : AwardSpecificationUtils.subsidyObjectiveIn(searchinput.getSubsidyObjective()))
			    
			    // getSpendingRegion from input parameter
			    .and(searchinput.getSpendingRegion() == null || searchinput.getSpendingRegion().isEmpty()
    						? null : AwardSpecificationUtils.spendingRegionIn(searchinput.getSpendingRegion()))
			    
			    // getSpendingSector from input parameter
			    .and(searchinput.getSpendingSector() == null || searchinput.getSpendingSector().isEmpty()  
    						? null : AwardSpecificationUtils.spendingSectorIn(searchinput.getSpendingSector()))
			    
			    // getSubsidyInstrument from input parameter
			    .and(searchinput.getSubsidyInstrument() == null || searchinput.getSubsidyInstrument().isEmpty()
    						? null : AwardSpecificationUtils.subsidyInstrumentIn(searchinput.getSubsidyInstrument()))
			    
			    // getSubsidyMeasureTitle from input parameter
			    .and(SearchUtils.checkNullOrEmptyString(searchinput.getSubsidyMeasureTitle())
						? null : AwardSpecificationUtils.subsidyMeasureTitle(searchinput.getSubsidyMeasureTitle().trim()))
			    
			    // LegalGranting Date Range from/to input parameter
			    .and(SearchUtils.checkNullOrEmptyString(searchinput.getLegalGrantingFromDate())
			    		|| SearchUtils.checkNullOrEmptyString(searchinput.getLegalGrantingToDate())
						? null : AwardSpecificationUtils
								.legalGrantingDateRange(
										SearchUtils.stringToDate(searchinput.getLegalGrantingFromDate()), 
										SearchUtils.stringToDate(searchinput.getLegalGrantingToDate())
										))
			    
			    // getBeneficiaryName from input parameter
			    .and(SearchUtils.checkNullOrEmptyString(searchinput.getBeneficiaryName())
						? null : AwardSpecificationUtils.beneficiaryName(searchinput.getBeneficiaryName().trim()))
			    ;
			
			List<Order> orders = getOrderByCondition(searchinput.getSortBy());
			
			Pageable pagingSortAwards = PageRequest.of(searchinput.getPageNumber() - 1, searchinput.getTotalRecordsPerPage(), Sort.by(orders));
			
			Page<Award> pageAwards = awardRepository.findAll(awardSpecifications, pagingSortAwards);
			
			List<Award> awardResults = pageAwards.getContent();
			
			SearchResults searchResults = null; 
			
			if (!awardResults.isEmpty()) {
				
					searchResults = new SearchResults(awardResults, pageAwards.getTotalElements(),
							pageAwards.getNumber() + 1, pageAwards.getTotalPages());
			} else {

				throw new SearchResultNotFoundException("AwardResults NotFound");
			}

			return searchResults;
	}

	@Override
	public AwardResponse findByAwardNumber(Long awardNumber) {

		Award award = awardRepository.findByAwardNumber(awardNumber);
		if (award == null) {
			throw new SearchResultNotFoundException("AwardResults NotFound");
		}
		return new AwardResponse(award, true);
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

		return orders;
	}

	/**
	 * 
	 * @param direction - direction of sort
	 * @return Sort.Direction - sort direction
	 */
	private Sort.Direction getSortDirection(String direction) {
		Sort.Direction sortDir = Sort.Direction.ASC;
		if (direction.equals("desc")) {
			sortDir = Sort.Direction.DESC;
	    }
	    return sortDir;
	}
}
