package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.GrantingAuthorityListResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.GrantingAuthorityResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasuresResponse;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
	private GrantingAuthorityRepository grantingAuthorityRepository;
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
	@GetMapping()
	public ResponseEntity<SubsidyMeasuresResponse> allSchemes() {
		SearchInput searchInput = new SearchInput();

		String[] sortParam = {request.getParameter("sort")};
		String[] sort = new String[1];

		int limit = 10;
		int page = 1;

		try{
			if (request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			if(request.getParameter("limit") != null) {
				limit = Integer.parseInt(request.getParameter("limit"));
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
		}

		if (sortParam[0] != null){
			String sortOrder = "asc";
			String sortString = sortParam[0];
			if(sortString.startsWith("-")){
				sortOrder="desc";
				sortString = sortString.substring(1);
			}
			sort[0] = sortString + "," + sortOrder;
		}else{
			sort[0] = "scNumber,desc";
		}

		if(request.getParameter("filter-scnumber") != null){
			searchInput.setScNumber(request.getParameter("filter-scnumber"));
		}
		if(request.getParameter("filter-name") != null){
			searchInput.setSubsidyMeasureTitle(request.getParameter("filter-name"));
		}

		searchInput.setTotalRecordsPerPage(limit);
		searchInput.setPageNumber(page);
		searchInput.setSortBy(sort);
		log.info("inside  allSchemes::::");
		SubsidyMeasuresResponse allSchemes = searchService.findAllSchemes(searchInput);

		return new ResponseEntity<SubsidyMeasuresResponse>(allSchemes, HttpStatus.OK);
	}

	/**
	 *
	 * @return response with list of granting authorities and HTTP status
	 */
	@GetMapping("/all_gas")
	public ResponseEntity<GrantingAuthorityListResponse> allGas() {
		List<GrantingAuthority> gaList = grantingAuthorityRepository.findAll();

		gaList = SearchUtils.removeRolesFromGaList(gaList);

		return new ResponseEntity<GrantingAuthorityListResponse>(new GrantingAuthorityListResponse(gaList), HttpStatus.OK);
	}
}
