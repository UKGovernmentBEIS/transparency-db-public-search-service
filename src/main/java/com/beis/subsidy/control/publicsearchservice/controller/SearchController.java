package com.beis.subsidy.control.publicsearchservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.SearchInput;
import com.beis.subsidy.control.publicsearchservice.model.SearchResults;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;


@RestController
public class SearchController {

	@Autowired
	private SearchService searchService;
	
	@GetMapping("/health")
	public ResponseEntity<String> getHealth() {
		return new ResponseEntity<>("Successful health check - Public Search API", HttpStatus.OK);
	}
	
	@GetMapping("/searchResults")
	public ResponseEntity<List<SearchResults>> getSearchResults(@Valid @RequestBody SearchInput searchInput) {
		
		return new ResponseEntity<List<SearchResults>>(searchService.getSearchResults(searchInput), HttpStatus.OK);
	}

	@GetMapping("/grantingAuthorities")
	public ResponseEntity<List<GrantingAuthority>> getGrantingAuthorities(@RequestParam(required = false) String name, 
		@RequestParam(required = false) String legalBasis,
		@RequestParam(required = false) String status) {
	
		return new ResponseEntity<List<GrantingAuthority>>(searchService.findMatchingGrantingAuthorities(
			name, legalBasis, status), HttpStatus.OK);
	}
	
}
