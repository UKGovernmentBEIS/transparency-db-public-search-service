package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasuresResponse;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
	 *
	 * @return response with list of schemes and HTTP status
	 */
	@GetMapping
	public ResponseEntity<SubsidyMeasuresResponse> allSchemes() {
		SearchInput searchInput = new SearchInput();

		String[] sortBy = {request.getParameter("sortBy")};

		int numRecords = 10;
		int page = 1;

		try{
			if (request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			if(request.getParameter("numRecords") != null) {
				numRecords = Integer.parseInt(request.getParameter("numRecords"));
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		if (sortBy[0] == null){
			sortBy[0] = "scNumber,desc";
		}

		searchInput.setTotalRecordsPerPage(numRecords);
		searchInput.setPageNumber(page);
		searchInput.setSortBy(sortBy);
		log.info("inside  allSchemes::::");
		SubsidyMeasuresResponse allSchemes = searchService.findAllSchemes(searchInput);

		return new ResponseEntity<SubsidyMeasuresResponse>(allSchemes, HttpStatus.OK);
	}
}
