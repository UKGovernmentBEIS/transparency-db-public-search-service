package com.beis.subsidy.control.publicsearchservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beis.subsidy.control.publicsearchservice.exception.SearchResultNotFoundException;
import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.SearchInput;
import com.beis.subsidy.control.publicsearchservice.model.SearchResults;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;


/**
 * This is rest controller for Public Search service - which has exposed required APIs for front end to talk to backend APIs.
 *
 */
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
	 * To get details of all awards 
	 * @return ResponseEntity - Return response status and description
	 */
	@GetMapping("/awards")
	public ResponseEntity<List<Award>> getAllAwards() {
		return new ResponseEntity<List<Award>>(searchService.getAllAwards(), HttpStatus.OK);
	}
	
	/**
	 * To get search input from UI and return search results based on search criteria
	 * 
	 * @param searchInput - Input as SearchInput object from front end 
	 * @return ResponseEntity - Return response status and description
	 */
	@PostMapping("/searchResults")
	public ResponseEntity<SearchResults> getSearchResults(@Valid @RequestBody SearchInput searchInput) {
		
		SearchResults searchResults = searchService.findMatchingAwards(searchInput);
		
		if(searchResults.totalSearchResults == 0) {
			throw new SearchResultNotFoundException("No matching search result found.");
		}
		
		return new ResponseEntity<SearchResults>(searchResults, HttpStatus.OK);
	}

	/* Commenting below method - as the requirements need to be discussed and later it will be used for impl. 
	@GetMapping("/grantingAuthorities")
	public ResponseEntity<List<GrantingAuthority>> getGrantingAuthorities(@RequestParam(required = false) String name, 
		@RequestParam(required = false) String legalBasis,
		@RequestParam(required = false) String status) {
	
		return new ResponseEntity<List<GrantingAuthority>>(searchService.findMatchingGrantingAuthorities(
			name, legalBasis, status), HttpStatus.OK);
	}
	*/
	
}
