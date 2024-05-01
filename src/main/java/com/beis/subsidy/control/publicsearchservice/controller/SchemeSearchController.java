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
import javax.validation.Valid;
import java.math.BigDecimal;
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
			if(!SearchUtils.checkNullOrEmptyString(request.getParameter("budget-from"))){
				searchInput.setBudgetFrom(new BigDecimal(request.getParameter("budget-from")));
			}
			if(!SearchUtils.checkNullOrEmptyString(request.getParameter("budget-to"))){
				searchInput.setBudgetTo(new BigDecimal(request.getParameter("budget-to")));
			}
		}catch(NumberFormatException e){
			e.printStackTrace();
			return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
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

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("scnumber"))){
			searchInput.setScNumber(request.getParameter("scnumber"));
		}
		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("name"))){
			searchInput.setSubsidyMeasureTitle(request.getParameter("name"));
		}
		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("ga"))){
			searchInput.setGrantingAuthorityName(request.getParameter("ga"));
		}

		//TODO: Refactor the start and end date filters into a single function

		// start of "Start Date" filter

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("start-day-from"))
			&& !SearchUtils.checkNullOrEmptyString(request.getParameter("start-month-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("start-year-from"))) {

				startDateFromString = request.getParameter("start-year-from") + "-" +
						request.getParameter("start-month-from") + "-" +
						request.getParameter("start-day-from");


			if(SearchUtils.isDateValid(startDateFromString)) {
				startDateFrom = SearchUtils.stringToDate(startDateFromString);
				searchInput.setSubsidyStartDateFrom(startDateFrom);
			}else{
				log.error("Invalid date format given for Start Date (From): " + startDateFromString);
				return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("start-day-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("start-month-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("start-year-to"))) {

			startDateToString = request.getParameter("start-year-to") + "-" +
					request.getParameter("start-month-to") + "-" +
					request.getParameter("start-day-to");


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

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("end-day-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("end-month-from"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("end-year-from"))) {

			endDateFromString = request.getParameter("end-year-from") + "-" +
					request.getParameter("end-month-from") + "-" +
					request.getParameter("end-day-from");


			if(SearchUtils.isDateValid(endDateFromString)) {
				endDateFrom = SearchUtils.stringToDate(endDateFromString);
				searchInput.setSubsidyEndDateFrom(endDateFrom);
			}else{
				log.error("Invalid date format given for End Date (From): " + endDateFromString);
				return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
			}
		}

		if (!SearchUtils.checkNullOrEmptyString(request.getParameter("end-day-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("end-month-to"))
				&& !SearchUtils.checkNullOrEmptyString(request.getParameter("end-year-to"))) {

			endDateToString = request.getParameter("end-year-to") + "-" +
					request.getParameter("end-month-to") + "-" +
					request.getParameter("end-day-to");


			if(SearchUtils.isDateValid(endDateToString)) {
				endDateTo = SearchUtils.stringToDate(endDateToString);
				searchInput.setSubsidyEndDateTo(endDateTo);
			}else{
				log.error("Invalid date format given for End Date (To): " + endDateToString);
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

		if(!SearchUtils.checkNullOrEmptyString(request.getParameter("status"))){
			switch(request.getParameter("status").toLowerCase()){
				case "active":
				case "inactive":
					searchInput.setSubsidyStatus(request.getParameter("status"));
					break;
				default:
					log.error("Invalid value given Status: " + request.getParameter("status"));
					return new ResponseEntity<SubsidyMeasuresResponse>(new SubsidyMeasuresResponse(), HttpStatus.BAD_REQUEST);
			}
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

	/**
	 * To get details of scheme based on schemeNumber
	 * @return ResponseEntity - Return associated scheme details in the response
	 */
	@PostMapping(
			path = "/scheme/withawards/{schemeNumber}",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<SubsidyMeasureResponse> getSchemeDetailsByScNumberWithAwards(@PathVariable("schemeNumber") String scNumber, @Valid @RequestBody SearchInput searchInput) {

		if(StringUtils.isEmpty(scNumber)) {
			throw new InvalidRequestException("Invalid Request");
		}
		log.info("inside  getAwardDetailsByAwardNumber::::{}",scNumber);
		SubsidyMeasureResponse schemeResponse = searchService.findSchemeByScNumberWithAwards(scNumber, searchInput);
		return new ResponseEntity<SubsidyMeasureResponse>(schemeResponse, HttpStatus.OK);
	}

	@GetMapping(
			value = "/scheme/{scNumber}/version/{version}",
			produces = APPLICATION_JSON_VALUE
	)
	public ResponseEntity<SubsidyMeasureVersionResponse> findSubsidySchemeVersion(@PathVariable("scNumber") String scNumber,@PathVariable("version") String version) {
		log.info("inside  findSubsidySchemeVersion::::{} version {}", scNumber, version);

		if (StringUtils.isEmpty(scNumber)) {
			throw new InvalidRequestException("Bad Request SC Number is null");
		}
		if (StringUtils.isEmpty(version)) {
			throw new InvalidRequestException("Bad Request version is null");
		}
		SubsidyMeasureVersionResponse schemeVersion = searchService.findSubsidySchemeVersion(scNumber,version);

		return new ResponseEntity<SubsidyMeasureVersionResponse>(schemeVersion, HttpStatus.OK);
	}
}
