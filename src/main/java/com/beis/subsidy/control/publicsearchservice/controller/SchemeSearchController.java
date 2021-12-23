package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasuresResponse;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * This is rest controller for Public Search service - which has exposed required APIs for front end to talk to backend APIs.
 *
 */
@RequestMapping(
		path = "/schemes"
)
@Slf4j
@RestController
public class SchemeSearchController {

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
	 *
	 * @param searchInput - Input as SearchInput object from front end
	 * @return response with list of schemes and HTTP status
	 */
	@PostMapping
	public ResponseEntity<SubsidyMeasuresResponse> allSchemes(@Valid @RequestBody SearchInput searchInput) {

		//Set Default Page records
		if(searchInput.getTotalRecordsPerPage() == 0) {
			searchInput.setTotalRecordsPerPage(10);
		}
		log.info("inside  allSchemes::::");
		SubsidyMeasuresResponse allSchemes = searchService.findAllSchemes(searchInput);

		return new ResponseEntity<SubsidyMeasuresResponse>(allSchemes, HttpStatus.OK);
	}
}
