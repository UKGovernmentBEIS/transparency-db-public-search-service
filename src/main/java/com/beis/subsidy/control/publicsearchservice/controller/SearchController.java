package com.beis.subsidy.control.publicsearchservice.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.beis.subsidy.control.publicsearchservice.model.Award;
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
	
	@GetMapping("/awards")
	public ResponseEntity<List<Award>> getAllAwards() {
		return new ResponseEntity<List<Award>>(searchService.getAllAwards(), HttpStatus.OK);
	}
	
	@GetMapping("/searchResults")
	public ResponseEntity<List<SearchResults>> getSearchResults(@Valid @RequestBody SearchInput searchInput) {
		
		return new ResponseEntity<List<SearchResults>>(searchService.getSearchResults(searchInput), HttpStatus.OK);
	}
	
}
