package com.beis.subsidy.control.publicsearchservice.service;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;

import java.io.ByteArrayInputStream;

public interface SearchService {

    SearchResults findMatchingAwards(SearchInput searchinput);

    AwardResponse findByAwardNumber(Long awardNumber);

    ByteArrayInputStream exportMatchingAwards(SearchInput searchInput);
}
