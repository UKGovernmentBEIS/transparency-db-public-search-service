package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.*;
import com.beis.subsidy.control.publicsearchservice.exception.InvalidRequestException;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.repository.GrantingAuthorityRepository;
import com.beis.subsidy.control.publicsearchservice.service.SearchService;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDate;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

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

		String startDateFromString = "";
		String startDateToString = "";
		String endDateFromString = "";
		String endDateToString = "";
		LocalDate startDateFrom = null;
		LocalDate startDateTo = null;
		LocalDate endDateFrom = null;
		LocalDate endDateTo = null;

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

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-scnumber"))){
			searchInput.setScNumber(request.getParameter("filter-scnumber"));
		}
		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-name"))){
			searchInput.setSubsidyMeasureTitle(request.getParameter("filter-name"));
		}
		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-ga"))){
			searchInput.setGrantingAuthorityName(request.getParameter("filter-ga"));
		}

		//TODO: Refactor the start and end date filters into a single function

		// start of "Start Date" filter

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-start-day-from"))
			&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-start-month-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-start-year-from"))) {

				startDateFromString = request.getParameter("filter-start-year-from") + "-" +
						request.getParameter("filter-start-month-from") + "-" +
						request.getParameter("filter-start-day-from");


			if(SearchUtils.isDateValid(startDateFromString)) {
				startDateFrom = SearchUtils.stringToDate(startDateFromString);
				searchInput.setSubsidyStartDateFrom(startDateFrom);
			}else{
				log.error("Invalid date format given for Start Date (From): " + startDateFromString);
				return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-start-day-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-start-month-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-start-year-to"))) {

			startDateToString = request.getParameter("filter-start-year-to") + "-" +
					request.getParameter("filter-start-month-to") + "-" +
					request.getParameter("filter-start-day-to");


			if(SearchUtils.isDateValid(startDateToString)) {
				startDateTo = SearchUtils.stringToDate(startDateToString);
				searchInput.setSubsidyStartDateTo(startDateTo);
			}else{
				log.error("Invalid date format given for Start Date (To): " + startDateToString);
				return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		// if start date provided by no end date, assume view all after this date, and vice versa
		if(startDateFrom != null && startDateTo == null){
			startDateTo = SearchUtils.stringToDate("9999-12-31");
			searchInput.setSubsidyStartDateTo(startDateTo);
		}else if(startDateFrom == null && startDateTo != null){
			startDateFrom = SearchUtils.stringToDate("0000-01-01");
			searchInput.setSubsidyStartDateFrom(startDateFrom);
		}

		// end of "Start Date" filter
		// start of "End Date" filter

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-end-day-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-end-month-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-end-year-from"))) {

			endDateFromString = request.getParameter("filter-end-year-from") + "-" +
					request.getParameter("filter-end-month-from") + "-" +
					request.getParameter("filter-end-day-from");


			if(SearchUtils.isDateValid(endDateFromString)) {
				endDateFrom = SearchUtils.stringToDate(endDateFromString);
				searchInput.setSubsidyEndDateFrom(endDateFrom);
			}else{
				log.error("Invalid date format given for Start Date (From): " + endDateFromString);
				return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-end-day-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-end-month-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("filter-end-year-to"))) {

			endDateToString = request.getParameter("filter-end-year-to") + "-" +
					request.getParameter("filter-end-month-to") + "-" +
					request.getParameter("filter-end-day-to");


			if(SearchUtils.isDateValid(endDateToString)) {
				endDateTo = SearchUtils.stringToDate(endDateToString);
				searchInput.setSubsidyEndDateTo(endDateTo);
			}else{
				log.error("Invalid date format given for Start Date (To): " + endDateToString);
				return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		// if from date provided by no to date, assume view all after this date, and vice versa
		if(endDateFrom != null && endDateTo == null){
			endDateTo = SearchUtils.stringToDate("9999-12-31");
			searchInput.setSubsidyEndDateTo(endDateTo);
		}else if(endDateFrom == null && endDateTo != null){
			endDateFrom = SearchUtils.stringToDate("0000-01-01");
			searchInput.setSubsidyEndDateFrom(endDateFrom);
		}

		// end of "End Date" filter

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("filter-status"))){
			searchInput.setSubsidyStatus(request.getParameter("filter-status"));
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

	/**
	 * To get details of scheme based on schemeNumber
	 * @return ResponseEntity - Return associated scheme details in the response
	 */
	@GetMapping(
			path = "/scheme/{schemeNumber}",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<SubsidyMeasureResponse> getSchemeDetailsByScNumber(@PathVariable("schemeNumber") String scNumber) {

		if(StringUtils.isEmpty(scNumber)) {
			throw new InvalidRequestException("Invalid Request");
		}
		log.info("inside  getAwardDetailsByAwardNumber::::{}",scNumber);
		SubsidyMeasureResponse schemeResponse = searchService.findSchemeByScNumber(scNumber);
		return new ResponseEntity<SubsidyMeasureResponse>(schemeResponse, HttpStatus.OK);
	}
}
