package com.beis.subsidy.control.publicsearchservice.service;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;

public interface SearchService {

    SearchResults findMatchingAwards(SearchInput searchinput);

    AwardResponse findByAwardNumber(Long awardNumber);

  }
