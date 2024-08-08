package com.beis.subsidy.control.publicsearchservice.service;

import com.beis.subsidy.control.publicsearchservice.controller.response.*;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;

public interface SearchService {

    SearchResults findMatchingAwards(SearchInput searchinput);

    AwardResponse findByAwardNumber(Long awardNumber);

    SubsidyMeasuresResponse findAllSchemes(SearchInput searchInput);

    SubsidyMeasureResponse findSchemeByScNumber(String scNumber);
    SubsidyMeasureResponse findSchemeByScNumberWithAwards(String scNumber, SearchInput awardsSearchInput);

    MFAAwardsResponse findMatchingMfaAwards(SearchInput searchInput);

    MFAAwardResponse findMfaByAwardNumber(Long mfaAwardNumber);

    SearchResults findStandaloneAwards(SearchInput searchInput);

    SubsidyMeasureVersionResponse findSubsidySchemeVersion(String scNumber, String version);
}
