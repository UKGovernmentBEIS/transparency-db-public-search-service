package com.beis.subsidy.control.publicsearchservice.controller.request;

import org.junit.jupiter.api.Test;
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

    }

}
