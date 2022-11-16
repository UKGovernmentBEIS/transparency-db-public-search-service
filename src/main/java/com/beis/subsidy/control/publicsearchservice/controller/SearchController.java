package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.MFAAwardsResponse;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;


import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
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


import java.math.BigDecimal;
import java.time.LocalDate;

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

	@GetMapping(
			path = "/mfaawards"
	)
	public ResponseEntity<MFAAwardsResponse> findMfaAwards() {

		SearchInput searchInput = new SearchInput();

		String[] sortParam = {request.getParameter("sort")};
		String[] sort = new String[1];

		int limit = 10;
		int page = 1;

		String startDateFromString = "";
		String startDateToString = "";
		LocalDate confirmationDateFrom = null;
		LocalDate confirmationDateTo = null;

		try{
			if (request.getParameter("page") != null){
				page = Integer.parseInt(request.getParameter("page"));
			}
			if(request.getParameter("limit") != null) {
				limit = Integer.parseInt(request.getParameter("limit"));
			}
			if(!SearchUtils.checkNullOrEmptyString(request.getParameter("amount-from"))){
				searchInput.setBudgetFrom(new BigDecimal(request.getParameter("amount-from")));
			}
			if(!SearchUtils.checkNullOrEmptyString(request.getParameter("amount-to"))){
				searchInput.setBudgetTo(new BigDecimal(request.getParameter("amount-to")));
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			return new ResponseEntity<MFAAwardsResponse>(new MFAAwardsResponse(), HttpStatus.BAD_REQUEST);
		}

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("speia"))){
			switch(request.getParameter("speia").toLowerCase()){
				case "yes":
				case "no":
					searchInput.setIsSpei(request.getParameter("speia").toLowerCase().equals("yes"));
					break;
				default:
					log.error("Invalid value given SPEIA: " + request.getParameter("speia"));
					return new ResponseEntity<MFAAwardsResponse>(new MFAAwardsResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("ga"))){
			searchInput.setGrantingAuthorityName(request.getParameter("ga"));
		}

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("recipient"))){
			searchInput.setBeneficiaryName(request.getParameter("recipient"));
		}

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("mfa-grouping"))){
			searchInput.setMfaGroupingName(request.getParameter("mfa-grouping"));
		}

		// start of "Start Date" filter

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("confirmation-day-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("confirmation-month-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("confirmation-year-from"))) {

			startDateFromString = request.getParameter("confirmation-year-from") + "-" +
					request.getParameter("confirmation-month-from") + "-" +
					request.getParameter("confirmation-day-from");


			if(SearchUtils.isDateValid(startDateFromString)) {
				confirmationDateFrom = SearchUtils.stringToDate(startDateFromString);
				searchInput.setSubsidyStartDateFrom(confirmationDateFrom);
			}else{
				log.error("Invalid date format given for Confirmation Date (From): " + startDateFromString);
				return new ResponseEntity<MFAAwardsResponse>(new MFAAwardsResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("confirmation-day-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("confirmation-month-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("confirmation-year-to"))) {

			startDateToString = request.getParameter("confirmation-year-to") + "-" +
					request.getParameter("confirmation-month-to") + "-" +
					request.getParameter("confirmation-day-to");


			if(SearchUtils.isDateValid(startDateToString)) {
				confirmationDateTo = SearchUtils.stringToDate(startDateToString);
				searchInput.setSubsidyStartDateTo(confirmationDateTo);
			}else{
				log.error("Invalid date format given for Confirmation Date (To): " + startDateToString);
				return new ResponseEntity<MFAAwardsResponse>(new MFAAwardsResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		// if start date provided by no end date, assume view all after this date, and vice versa
		if(confirmationDateFrom != null && confirmationDateTo == null){
			confirmationDateTo = SearchUtils.stringToDate("9999-12-31");
			searchInput.setSubsidyStartDateTo(confirmationDateTo);
		}else if(confirmationDateFrom == null && confirmationDateTo != null){
			confirmationDateFrom = SearchUtils.stringToDate("0000-01-01");
			searchInput.setSubsidyStartDateFrom(confirmationDateFrom);
		}

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("status"))){
			switch(request.getParameter("status").toLowerCase()){
				case "published":
				case "deleted":
					searchInput.setSubsidyStatus(request.getParameter("status"));
					break;
				default:
					log.error("Invalid value given Status: " + request.getParameter("status"));
					return new ResponseEntity<MFAAwardsResponse>(new MFAAwardsResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		// end of "Start Date" filter

		if (sortParam[0] != null){
			String sortOrder = "asc";
			String sortString = sortParam[0];
			if(sortString.startsWith("-")){
				sortOrder="desc";
				sortString = sortString.substring(1);
			}
			sort[0] = sortString + "," + sortOrder;
		}else{
			sort[0] = "mfaAwardNumber,desc";
		}

		searchInput.setTotalRecordsPerPage(limit);
		searchInput.setPageNumber(page);
		searchInput.setSortBy(sort);
		log.info("inside  findSearchResults::::");
		MFAAwardsResponse response = searchService.findMatchingMfaAwards(searchInput);

		return new ResponseEntity<MFAAwardsResponse>(response, HttpStatus.OK);
	}
}
