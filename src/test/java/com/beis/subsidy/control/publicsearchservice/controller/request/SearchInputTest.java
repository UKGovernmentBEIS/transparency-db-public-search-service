package com.beis.subsidy.control.publicsearchservice.controller.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SearchInputTest {

    @Test
    public void testSearchInput() {

        SearchInput input = new SearchInput();
        input.setTotalRecordsPerPage(1);
        input.setBeneficiaryName("bName");
        input.setLegalGrantingDate("12/21/2020");
        input.setLegalGrantingFromDate("12/21/2020");
        input.setLegalGrantingToDate("12/21/2020");
        input.setPageNumber(0);
        List<String> sortValues = new ArrayList();
        sortValues.add("desc");
        sortValues.add("bName");
        String[] itemsArray = new String[sortValues.size()];
        itemsArray = sortValues.toArray(itemsArray);
        input.setSortBy(itemsArray);
        List<String> spendingRegion = new ArrayList();
        spendingRegion.add("spendingRegion");
        input.setSpendingRegion(spendingRegion);
        input.setSubsidyMeasureTitle("title");
        List<String> spendingSector = new ArrayList();
        spendingSector.add("spendingSector");
        input.setSpendingSector(spendingSector);

        List<String> subsidyInstruments= new ArrayList();
        subsidyInstruments.add("subsidyInstrument");
        input.setSubsidyInstrument(subsidyInstruments);

        List<String> subsidyObjectives= new ArrayList();
        subsidyObjectives.add("subsidyObjective");
        input.setSubsidyObjective(subsidyObjectives);

        input.setLegalGrantingFromDate("2022-06-01");
        input.setScNumber("SC10001");
        input.setGrantingAuthorityName("GA Name");
        input.setSubsidyStartDateFrom(LocalDate.parse("2022-01-01"));
        input.setSubsidyStartDateTo(LocalDate.parse("2022-12-31"));
        input.setSubsidyEndDateFrom(LocalDate.parse("2022-01-01"));
        input.setSubsidyEndDateTo(LocalDate.parse("2022-12-31"));
        input.setSubsidyStatus("Active");
        input.setAdHoc("yes");
        input.setBudgetFrom(new BigDecimal("500"));
        input.setBudgetTo(new BigDecimal("1000"));

        assertThat(input).isNotNull();
        assertThat(input.getSpendingRegion()).isNotNull();
        assertThat(input.getSpendingRegion().size()).isGreaterThanOrEqualTo(1);
        assertThat(input.getSpendingSector().size()).isGreaterThanOrEqualTo(1);
        assertThat(input.getTotalRecordsPerPage()).isNotNull();
        assertThat(input.getBeneficiaryName()).isNotNull();
        assertThat(input.getLegalGrantingFromDate()).isNotNull();
        assertThat(input.getLegalGrantingToDate()).isNotNull();
        assertThat(input.getSubsidyMeasureTitle()).isNotNull();
        assertThat(input.getSortBy()).isNotNull();
        assertThat(input.getSortBy().length).isGreaterThanOrEqualTo(1);
        assertThat(input.getSubsidyObjective()).isNotNull();
        assertThat(input.getSubsidyInstrument().size()).isGreaterThanOrEqualTo(1);
        assertThat(input.getSubsidyObjective().size()).isGreaterThanOrEqualTo(1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        assertThat(input.getLegalGrantingFromDate()).isEqualTo("2022-06-01");
        assertThat(input.getScNumber()).isEqualTo("SC10001");
        assertThat(input.getGrantingAuthorityName()).isEqualTo("GA Name");
        assertThat(input.getSubsidyStartDateFrom().format(formatter)).isEqualTo("2022-01-01");
        assertThat(input.getSubsidyStartDateTo().format(formatter)).isEqualTo("2022-12-31");
        assertThat(input.getSubsidyEndDateFrom().format(formatter)).isEqualTo("2022-01-01");
        assertThat(input.getSubsidyEndDateTo().format(formatter)).isEqualTo("2022-12-31");
        assertThat(input.getSubsidyStatus()).isEqualTo("Active");
        assertThat(input.getAdHoc()).isEqualTo("yes");
        assertThat(input.getBudgetFrom()).isEqualTo(new BigDecimal("500"));
        assertThat(input.getBudgetTo()).isEqualTo(new BigDecimal("1000"));



    }

}
