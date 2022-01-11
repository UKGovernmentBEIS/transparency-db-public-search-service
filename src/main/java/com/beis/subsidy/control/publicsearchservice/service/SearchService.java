package com.beis.subsidy.control.publicsearchservice.service;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasureResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SubsidyMeasuresResponse;

public interface SearchService {

    SearchResults findMatchingAwards(SearchInput searchinput);

    AwardResponse findByAwardNumber(Long awardNumber);

    SubsidyMeasuresResponse findAllSchemes(SearchInput searchInput);

    SubsidyMeasureResponse findSchemeByScNumber(String scNumber);
}
