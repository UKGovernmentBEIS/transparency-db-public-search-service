package com.beis.subsidy.control.publicsearchservice.service;

import com.beis.subsidy.control.publicsearchservice.controller.response.AwardResponse;
import com.beis.subsidy.control.publicsearchservice.controller.response.SearchResults;
import com.beis.subsidy.control.publicsearchservice.controller.request.SearchInput;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.ParseException;

public interface SearchService {

    SearchResults findMatchingAwards(SearchInput searchinput);

    AwardResponse findByAwardNumber(Long awardNumber);

    XSSFWorkbook exportMatchingAwards(SearchInput searchInput);
}
