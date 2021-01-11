package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
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
import org.springframework.web.bind.annotation.CrossOrigin;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

/**
 * This is rest controller for Public Search service - which has exposed required APIs for front end to talk to backend APIs.
 *
 */
@RequestMapping(
		path = "/searchResults"
)
@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;
	
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
	@CrossOrigin
	@PostMapping
	public ResponseEntity<SearchResults> findSearchResults(@Valid @RequestBody SearchInput searchInput) {

			//Set Default Page records
			if(searchInput.getTotalRecordsPerPage() == 0) {
				searchInput.setTotalRecordsPerPage(10);
			}
			SearchResults searchResults = searchService.findMatchingAwards(searchInput);
			
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
		AwardResponse awardResponse = searchService.findByAwardNumber(awardNumber);
		return new ResponseEntity<AwardResponse>(awardResponse, HttpStatus.OK);
	}
}
