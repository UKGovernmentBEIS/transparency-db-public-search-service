package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;


import lombok.extern.slf4j.Slf4j;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;


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
	
	/**
	 * To get health of app 
	 * @return ResponseEntity - Return response status and description
	 */
	@GetMapping("/health")
	public ResponseEntity<String> getHealth() {
		return new ResponseEntity<>("Successful health check - Public Search API", HttpStatus.OK);
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
			log.info("inside  findSearchResults::::");
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

		log.info("inside  findSearchResults::::");
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
		log.info("inside  getAwardDetailsByAwardNumber::::{}",awardNumber);
		AwardResponse awardResponse = searchService.findByAwardNumber(awardNumber);
		return new ResponseEntity<AwardResponse>(awardResponse, HttpStatus.OK);
	}
}
