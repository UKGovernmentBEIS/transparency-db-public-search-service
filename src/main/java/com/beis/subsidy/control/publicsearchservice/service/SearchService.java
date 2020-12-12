package com.beis.subsidy.control.publicsearchservice.service;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasureResponse;

import java.text.ParseException;

public interface SearchService {

    SearchResults findMatchingAwards(SearchInput searchinput) throws ParseException;

    AwardResponse findByAwardNumber(Long awardNumber);

    SubsidyMeasureResponse getSubsidyMeasureDetails(String scNumber);
}
