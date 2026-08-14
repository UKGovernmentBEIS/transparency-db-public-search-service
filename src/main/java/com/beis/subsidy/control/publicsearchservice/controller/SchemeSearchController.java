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
	 * @return response with list of granting authorities and HTTP status
	 */
	@GetMapping("/all_gas")
	public ResponseEntity<GrantingAuthorityListResponse> allGas() {
		List<GrantingAuthority> gaList = grantingAuthorityRepository.findAll();

        SearchUtils.removeRolesFromGaList(gaList);
        SearchUtils.removeInactiveFromGaList(gaList);

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
		log.trace("inside getSchemeDetailsByScNumberWithAwards::::{}",scNumber);
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
