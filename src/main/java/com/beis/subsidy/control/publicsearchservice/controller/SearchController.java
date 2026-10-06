package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.Filter;
import com.beis.subsidy.control.publicsearchservice.controller.response.*;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;


import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import lombok.extern.slf4j.Slf4j;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.*;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This is rest controller for Public Search service - which has exposed required APIs for front end to talk to backend APIs.
 *
 */
@RequestMapping(
		path = "/searchResults"
)
@Slf4j
@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;

	@Autowired
	private HttpServletRequest request;

	@Autowired
	private GrantingAuthorityRepository grantingAuthorityRepository;
	
	/**
	 * To get health of app 
	 * @return ResponseEntity - Return response status and description
	 */
	@GetMapping("/health")
	public ResponseEntity<String> getHealth() {
		return new ResponseEntity<>("Successful health check - Public Search API", HttpStatus.OK);
	}

	/**
	 *
	 * @return response with list of granting authorities and HTTP status
	 */
	@GetMapping("/all_gas")
	public ResponseEntity<GrantingAuthorityListResponse> allGas() {
		List<GrantingAuthority> gaList = grantingAuthorityRepository.findAll();

		SearchUtils.removeRolesFromGaList(gaList);
		SearchUtils.removeInactiveFromGaList(gaList);

		return new ResponseEntity<GrantingAuthorityListResponse>(new GrantingAuthorityListResponse(gaList), HttpStatus.OK);
	}

	/**
	 * To get search input from UI and return search results based on search criteria
	 * 
	 * @param searchInput - Input as SearchInput object from front end 
	 * @return ResponseEntity - Return response status and description
	 */
	@PostMapping
	public ResponseEntity<SearchResults> findSearchResults(@Valid @RequestBody SearchInput searchInput) {

			//Set Default Page records
			if(searchInput.getTotalRecordsPerPage() == 0) {
				searchInput.setTotalRecordsPerPage(10);
			}
			log.trace("inside  findSearchResults::::");
			SearchResults searchResults = searchService.findMatchingAwards(searchInput);
			
			return new ResponseEntity<SearchResults>(searchResults, HttpStatus.OK);
	}

	@PostMapping("/standaloneawards")
	public ResponseEntity<SearchResults> findStandaloneAwards(@Valid @RequestBody SearchInput searchInput) {

		String[] sort = new String[1];
		String[] sortParam = {searchInput.getSortBy()[0]};
		int limit = 10;
		int page = 1;

		if (sortParam[0] != ""){
			String sortOrder = "asc";
			String sortString = sortParam[0];
			if(sortString.startsWith("-")){
				sortOrder="desc";
				sortString = sortString.substring(1);
			}
			sort[0] = sortString + "," + sortOrder;
		}else{
			sort[0] = "awardNumber,desc";
		}

		if(searchInput.getTotalRecordsPerPage() == 0) {
			searchInput.setTotalRecordsPerPage(limit);
		}

		if(searchInput.getPageNumber() == 0) {
			searchInput.setPageNumber(page);
		}

		searchInput.setSortBy(sort);

		log.trace("inside  findSearchResults::::");
		SearchResults searchResults = searchService.findStandaloneAwards(searchInput);

		return new ResponseEntity<SearchResults>(searchResults, HttpStatus.OK);
	}

	/**
	 * To get details of award based on awardNumber
	 * @return ResponseEntity - Return associated award details in the response
	 */
	@GetMapping(
			path = "/award/{awardNumber}",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<AwardResponse> getAwardDetailsByAwardNumber(@PathVariable("awardNumber") Long awardNumber) {

		if(StringUtils.isEmpty(awardNumber)) {
			throw new InvalidRequestException("Invalid Request");
		}
		log.trace("inside  getAwardDetailsByAwardNumber::::{}",awardNumber);
		AwardResponse awardResponse = searchService.findByAwardNumber(awardNumber);
		return new ResponseEntity<AwardResponse>(awardResponse, HttpStatus.OK);
	}

	@GetMapping(
			path = "/mfaawards"
	)
	public ResponseEntity<MFAAwardsResponse> findMfaAwards(@ModelAttribute Filter filter, Pageable pageable){
		filter.normalise();
		Pageable mappedPageable = mapSort(pageable, "mfa");
		return new ResponseEntity<MFAAwardsResponse>(searchService.findMfaAwards(filter, mappedPageable),HttpStatus.OK);
	}

	@GetMapping(
			value = "/mfaawards/export",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<MFAAwardsExportResponse> exportMfaAwards(@ModelAttribute Filter filter) {
		filter.normalise();
		return new ResponseEntity<MFAAwardsExportResponse>(searchService.findMfaAwardsForExport(filter),HttpStatus.OK);
	}

	@GetMapping(
			value = "/mfa/{mfaAwardNumber}",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<MFAAwardResponse> findMfaAward(@PathVariable("mfaAwardNumber") String mfaAwardNumber) {
		if (StringUtils.isEmpty(mfaAwardNumber)) {
			throw new InvalidRequestException("Bad Request MFA Award is null");
		}

		if(!SearchUtils.isNumeric(mfaAwardNumber)){
			return new ResponseEntity<MFAAwardResponse>(new MFAAwardResponse(), HttpStatus.BAD_REQUEST);
		}

		Long awardNumber = Long.parseLong(mfaAwardNumber);

		MFAAwardResponse mfaAwardById = searchService.findMfaByAwardNumber(awardNumber);

		return new ResponseEntity<MFAAwardResponse>(mfaAwardById, HttpStatus.OK);
	}

	@GetMapping(
			value = "/awards",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<AwardsResponse> findAwards(@ModelAttribute Filter filter, Pageable pageable){
		filter.normalise();
		Pageable mappedPageable = mapSort(pageable, "award");
		return new ResponseEntity<AwardsResponse>(searchService.findAwards(filter, mappedPageable),HttpStatus.OK);
	}

	@GetMapping(
			value = "/awards/export",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<AwardsExportResponse> exportAwards(@ModelAttribute Filter filter) {
		filter.normalise();
		return new ResponseEntity<AwardsExportResponse>(searchService.findAwardsForExport(filter),HttpStatus.OK);
	}

	@GetMapping(
			value = "/schemes",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<SubsidyMeasuresResponse> findSchemes(@ModelAttribute Filter filter, Pageable pageable){
		filter.normalise();
		Pageable mappedPageable = mapSort(pageable, "scheme");
		return new ResponseEntity<SubsidyMeasuresResponse>(searchService.findSchemes(filter, mappedPageable),HttpStatus.OK);
	}

	@GetMapping(
			value = "/schemes/export",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<SubsidyMeasuresExportResponse> exportSchemes(@ModelAttribute Filter filter) {
		filter.normalise();
		return new ResponseEntity<SubsidyMeasuresExportResponse>(searchService.findSchemesForExport(filter),HttpStatus.OK);
	}

	private Pageable mapSort(Pageable pageable, String type) {
		Sort mappedSort = mapSort(pageable.getSort(), type);

		return PageRequest.of(
				pageable.getPageNumber(),
				pageable.getPageSize(),
				mappedSort
		);
	}

	private Sort mapSort(Sort sort, String type) {
        if (sort == null || sort.isUnsorted()) {
			return Sort.by(Sort.Direction.DESC, mapSortField(null, type));
		}

		List<Sort.Order> mappedOrders = new ArrayList<>();

		for (Sort.Order order : sort) {
			String frontendField = order.getProperty();
			String backendField = mapSortField(frontendField, type);

			mappedOrders.add(new Sort.Order(order.getDirection(), backendField));
		}

		return Sort.by(mappedOrders);
	}

	private String mapSortField(String frontendField, String type) {
        // Default to award mappings
        String recipientField = "beneficiary.beneficiaryName";
        String publishedField = "publishedAwardDate";
        switch (type) {
            case "scheme":
				publishedField = "publishedMeasureDate";
				recipientField = "subsidyMeasureTitle";
                break;
            case "mfa":
                recipientField = "recipientName";
                publishedField = "publishedDate";
                break;
            default:
                break;
        }
		if (frontendField == null) {
			return publishedField;
		}

		if (frontendField.equalsIgnoreCase("recipientName") || frontendField.equalsIgnoreCase("subsidySchemeName")){
			return recipientField;
		}else{
			return publishedField;
		}
	}
}
