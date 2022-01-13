package com.beis.subsidy.control.publicsearchservice.service.impl;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasureResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasuresResponse;
import com.beis.subsidy.control.publicsearchservice.exception.SearchResultNotFoundException;
import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import com.beis.subsidy.control.publicsearchservice.repository.SubsidyMeasureRepository;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import com.beis.subsidy.control.publicsearchservice.utils.AwardSpecificationUtils;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;

import com.beis.subsidy.control.publicsearchservice.utils.SubsidyMeasureSpecificationUtils;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Service class for Public Search service 
 *
 */
@Slf4j
@Service
public class SearchServiceImpl implements SearchService {

	@Autowired
	private AwardRepository awardRepository;
	@Autowired
	private SubsidyMeasureRepository schemeRepository;

	/**
	 * To return matching awards based on search inputs 
	 * @param searchInput - Search input object, that contains search criteria
	 * @return SearchResults - Returns search result based on search criteria
	 */
	public SearchResults findMatchingAwards(SearchInput searchInput) {
			List<String> otherSubsidyObjectiveList = new ArrayList<>();
		    List<String> otherSubsidyInstrumentList = new ArrayList<>();
			if(searchInput != null && searchInput.getSubsidyObjective() != null && searchInput.getSubsidyObjective().size() > 0){
				for(String subsidyObjective : searchInput.getSubsidyObjective()){
					if(subsidyObjective.contains("Other")){
						if(subsidyObjective.split("-").length > 1){
							otherSubsidyObjectiveList.add(subsidyObjective.split("-")[1].trim());
						} else {
							otherSubsidyObjectiveList.add("Other");
						}

					}
				}
	        }
			if(searchInput != null && searchInput.getSubsidyInstrument() != null && searchInput.getSubsidyInstrument().size() > 0){
				for(String subsidyInstrument : searchInput.getSubsidyInstrument()){
					if(subsidyInstrument.contains("Other")){
						if(subsidyInstrument.split("-").length > 1){
							otherSubsidyInstrumentList.add(subsidyInstrument.split("-")[1].trim());
						} else {
							otherSubsidyInstrumentList.add("Other");
						}
					}
				}
			}
		    searchInput.setOtherSubsidyObjective(otherSubsidyObjectiveList);
		    searchInput.setOtherSubsidyInstrument(otherSubsidyInstrumentList);

			Specification<Award> awardSpecifications = null;

			if(searchInput.getOtherSubsidyInstrument() != null && searchInput.getOtherSubsidyInstrument().size() > 0){
				awardSpecifications = getSpecificationAwardDetailsWithOtherInstrument(searchInput);
			} else {
				awardSpecifications = getSpecificationAwardDetails(searchInput);
			}

			List<Order> orders = getOrderByCondition(searchInput.getSortBy());

			Pageable pagingSortAwards = PageRequest.of(searchInput.getPageNumber() - 1, searchInput.getTotalRecordsPerPage(), Sort.by(orders));
			
			Page<Award> pageAwards = awardRepository.findAll(awardSpecifications, pagingSortAwards);
			
			List<Award> awardResults = pageAwards.getContent();
			log.info("inside  awardResults.size::::" +awardResults.size());
			SearchResults searchResults = null; 
			
			if (!awardResults.isEmpty()) {
				log.info("SearchResult Found::::" );
					searchResults = new SearchResults(awardResults, pageAwards.getTotalElements(),
							pageAwards.getNumber() + 1, pageAwards.getTotalPages());
			} else {

				log.info("SearchResultNotFoundException::::" );
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

	@Override
	public SubsidyMeasureResponse findSchemeByScNumber(String scNumber){
		SubsidyMeasure scheme = schemeRepository.findByScNumber(scNumber);

		if (scheme == null){
			throw new SearchResultNotFoundException("Scheme NotFound");
		}
		return new SubsidyMeasureResponse(scheme, true);
	}

	@Override
	public SubsidyMeasuresResponse findAllSchemes(SearchInput searchInput) {
		Specification<SubsidyMeasure> subsidyMeasureSpecification = null;
		List<Order> orders = getOrderByCondition(searchInput.getSortBy());
		Pageable pagingSortSchemes = PageRequest.of(searchInput.getPageNumber() - 1, searchInput.getTotalRecordsPerPage(), Sort.by(orders));

		subsidyMeasureSpecification = getSpecificationSchemeDetails(searchInput);

		Page<SubsidyMeasure> pageSchemes = schemeRepository.findAll(subsidyMeasureSpecification,pagingSortSchemes);

		List<SubsidyMeasure> schemeResults = pageSchemes.getContent();
		return new SubsidyMeasuresResponse(schemeResults, pageSchemes.getTotalElements(),
				pageSchemes.getNumber() + 1, pageSchemes.getTotalPages());
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

	public Specification<SubsidyMeasure>  getSpecificationSchemeDetails(SearchInput searchinput) {
		Specification<SubsidyMeasure> schemeSpecifications = Specification
				.where(searchinput.getScNumber() == null || searchinput.getScNumber().isEmpty()
				? null : SubsidyMeasureSpecificationUtils.scNumber(searchinput.getScNumber()))

				.and(searchinput.getSubsidyMeasureTitle() == null || searchinput.getSubsidyMeasureTitle().isEmpty()
						? null : SubsidyMeasureSpecificationUtils.subsidyMeasureTitle(searchinput.getSubsidyMeasureTitle()))

				.and(searchinput.getGrantingAuthorityName() == null || searchinput.getGrantingAuthorityName().isEmpty()
						? null : SubsidyMeasureSpecificationUtils.subsidyMeasureGaName(searchinput.getGrantingAuthorityName()))

				.and(searchinput.getSubsidyStartDateFrom() == null || searchinput.getSubsidyStartDateTo() == null
						? null : SubsidyMeasureSpecificationUtils.subsidyStartEndDate("startDate", searchinput.getSubsidyStartDateFrom(), searchinput.getSubsidyStartDateTo()))

				.and(searchinput.getSubsidyEndDateFrom() == null || searchinput.getSubsidyEndDateTo() == null
						? null : SubsidyMeasureSpecificationUtils.subsidyStartEndDate("endDate", searchinput.getSubsidyEndDateFrom(), searchinput.getSubsidyEndDateTo()))

				.and(searchinput.getSubsidyStatus() == null || searchinput.getSubsidyStatus().isEmpty()
						? null : SubsidyMeasureSpecificationUtils.subsidyStatus(searchinput.getSubsidyStatus()))

				.and(searchinput.getAdHoc() == null || searchinput.getAdHoc().isEmpty()
						? null : SubsidyMeasureSpecificationUtils.subsidyAdhoc(searchinput.getAdHoc()));
		return schemeSpecifications;
	}

	public Specification<Award>  getSpecificationAwardDetails(SearchInput searchinput) {
		Specification<Award> awardSpecifications = Specification

				// getSubsidyObjective from input parameter
				.where(searchinput.getSubsidyObjective() == null || searchinput.getSubsidyObjective().isEmpty()
						? null : AwardSpecificationUtils.subsidyObjectiveIn(searchinput.getSubsidyObjective())
						//Like search for other subsidy objective
						.or(searchinput.getOtherSubsidyObjective() == null || searchinput.getOtherSubsidyObjective().isEmpty()
								? null : AwardSpecificationUtils.otherSubsidyObjective(searchinput.getOtherSubsidyObjective())))

				// getSpendingRegion from input parameter
				.and(searchinput.getSpendingRegion() == null || searchinput.getSpendingRegion().isEmpty()
						? null : AwardSpecificationUtils.spendingRegionIn(searchinput.getSpendingRegion()))

				// getSpendingSector from input parameter
				.and(searchinput.getSpendingSector() == null || searchinput.getSpendingSector().isEmpty()
						? null : AwardSpecificationUtils.spendingSectorIn(searchinput.getSpendingSector()))

				// getSubsidyInstrument from input parameter
				.and(searchinput.getSubsidyInstrument() == null || searchinput.getSubsidyInstrument().isEmpty()
						? null : AwardSpecificationUtils.subsidyInstrumentIn(searchinput.getSubsidyInstrument()))
				// Like search for other instrument

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
				// get by published status
				.and(AwardSpecificationUtils.statusIn(getStatusValues()));
		return awardSpecifications;
	}
	public Specification<Award>  getSpecificationAwardDetailsWithOtherInstrument(SearchInput searchinput) {
		Specification<Award> awardSpecifications = Specification

				// getSubsidyObjective from input parameter
				.where(searchinput.getSubsidyObjective() == null || searchinput.getSubsidyObjective().isEmpty()
						? null : AwardSpecificationUtils.subsidyObjectiveIn(searchinput.getSubsidyObjective())
						//Like search for other subsidy objective
						.or(searchinput.getOtherSubsidyObjective() == null || searchinput.getOtherSubsidyObjective().isEmpty()
								? null : AwardSpecificationUtils.otherSubsidyObjective(searchinput.getOtherSubsidyObjective())))

				// getSpendingRegion from input parameter
				.and(searchinput.getSpendingRegion() == null || searchinput.getSpendingRegion().isEmpty()
						? null : AwardSpecificationUtils.spendingRegionIn(searchinput.getSpendingRegion()))

				// getSpendingSector from input parameter
				.and(searchinput.getSpendingSector() == null || searchinput.getSpendingSector().isEmpty()
						? null : AwardSpecificationUtils.spendingSectorIn(searchinput.getSpendingSector()))

				// getSubsidyInstrument from input parameter
				.and(searchinput.getSubsidyInstrument() == null || searchinput.getSubsidyInstrument().isEmpty()
						? null : AwardSpecificationUtils.subsidyInstrumentIn(searchinput.getSubsidyInstrument())
						.or(searchinput.getOtherSubsidyInstrument() == null || searchinput.getOtherSubsidyInstrument().isEmpty()
						? null : AwardSpecificationUtils.otherSubsidyInstrumentIn(searchinput.getOtherSubsidyInstrument()))
				)
				// Like search for other instrument

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
				.and(AwardSpecificationUtils.statusIn(getStatusValues()));
		return awardSpecifications;
	}

	public List<String> getStatusValues() {

		List<String> statuses = new ArrayList<>();
		statuses.add("Published");
		statuses.add("Deleted");
		return statuses;
	}
}
