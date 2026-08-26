package com.beis.subsidy.control.publicsearchservice.controller.request;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilterTest {

    @Test
    public void testPopulatedFilter() {
        Filter filter = new Filter();

        String[] geoLocations = {"UK-Wide", "England"};
        String[] sectors = {"Accommodation and food service activities",
                "Activities of households as employers; undifferentiated goods- and services-producing activities of households for own use",
                "Construction"
        };
        String[] purposes = {"Culture or Heritage", "Other"};

        filter.setKeyword("keyword");
        filter.setPa("TEST GA");
        filter.setGeoLocation(geoLocations);
        filter.setMfaAssistance("spei");
        filter.setAwardFullFromAmount("100");
        filter.setAwardFullToAmount("200");
        filter.setConfirmationFromDay("30");
        filter.setConfirmationFromMonth("09");
        filter.setConfirmationFromYear("2026");
        filter.setConfirmationToDay("31");
        filter.setConfirmationToMonth("12");
        filter.setConfirmationToYear("2026");
        filter.setSchemeStatus("Active");
        filter.setSchemeStartFromDay("01");
        filter.setSchemeStartFromMonth("02");
        filter.setSchemeStartFromYear("2003");
        filter.setSchemeStartToDay("04");
        filter.setSchemeStartToMonth("05");
        filter.setSchemeStartToYear("2006");
        filter.setSchemeBudgetFromAmount("1000000");
        filter.setSchemeBudgetToAmount("2000000");
        filter.setSector(sectors);
        filter.setSubsidyPurpose(purposes);
        filter.setSubsidyPurposeOther("Other purpose");

        // normalized values should be null before normalise
        assertThat(filter.getAwardFullAmountFrom()).isNull();
        assertThat(filter.getAwardFullAmountTo()).isNull();
        assertThat(filter.getConfirmationDateFrom()).isNull();
        assertThat(filter.getConfirmationDateTo()).isNull();
        assertThat(filter.getGeoLocations()).isNull();
        assertThat(filter.getIsSpei()).isFalse();
        assertThat(filter.getSectors()).isNull();
        assertThat(filter.getSubsidyPurposes()).isNull();

        filter.normalise();

        assertThat(filter).isNotNull();
        assertThat(filter.getKeyword()).isEqualTo("keyword");
        assertThat(filter.getPa()).isEqualTo("TEST GA");
        assertThat(filter.getGeoLocation()).isEqualTo(geoLocations);
        assertThat(filter.getMfaAssistance()).isEqualTo("spei");
        assertThat(filter.getAwardFullFromAmount()).isEqualTo("100");
        assertThat(filter.getAwardFullToAmount()).isEqualTo("200");
        assertThat(filter.getConfirmationFromDay()).isEqualTo("30");
        assertThat(filter.getConfirmationFromMonth()).isEqualTo("09");
        assertThat(filter.getConfirmationFromYear()).isEqualTo("2026");
        assertThat(filter.getConfirmationToDay()).isEqualTo("31");
        assertThat(filter.getConfirmationToMonth()).isEqualTo("12");
        assertThat(filter.getConfirmationToYear()).isEqualTo("2026");
        assertThat(filter.getSchemeStatus()).isEqualTo("Active");
        assertThat(filter.getSchemeStartFromDay()).isEqualTo("01");
        assertThat(filter.getSchemeStartFromMonth()).isEqualTo("02");
        assertThat(filter.getSchemeStartFromYear()).isEqualTo("2003");
        assertThat(filter.getSchemeStartToDay()).isEqualTo("04");
        assertThat(filter.getSchemeStartToMonth()).isEqualTo("05");
        assertThat(filter.getSchemeStartToYear()).isEqualTo("2006");
        assertThat(filter.getSchemeBudgetFromAmount()).isEqualTo("1000000");
        assertThat(filter.getSchemeBudgetToAmount()).isEqualTo("2000000");
        assertThat(filter.getSector()).isEqualTo(sectors);
        assertThat(filter.getSubsidyPurpose()).isEqualTo(purposes);
        assertThat(filter.getSubsidyPurposeOther()).isEqualTo("Other purpose");

        assertThat(filter.getAwardFullAmountFrom()).isEqualTo(100);
        assertThat(filter.getAwardFullAmountTo()).isEqualTo(200);
        assertThat(filter.getConfirmationDateFrom()).isEqualTo("2026-09-30");
        assertThat(filter.getConfirmationDateTo()).isEqualTo("2026-12-31");
        assertThat(filter.getGeoLocations()).isNotEmpty();
        assertThat(filter.getGeoLocations()).containsAll(new ArrayList<>(Arrays.asList(geoLocations)));
        assertThat(filter.getIsSpei()).isTrue();
        assertThat(filter.getSectors()).containsAll(new ArrayList<>(Arrays.asList(sectors)));
        assertThat(filter.getSubsidyPurposes()).containsAll(new ArrayList<>(Arrays.asList(purposes)));
        assertThat(filter.getSchemeBudgetFrom()).isEqualTo(BigDecimal.valueOf(Long.parseLong("1000000")));
        assertThat(filter.getSchemeBudgetTo()).isEqualTo(BigDecimal.valueOf(Long.parseLong("2000000")));
        assertThat(filter.getSchemeStartFromDate()).isEqualTo("2003-02-01");
        assertThat(filter.getSchemeStartToDate()).isEqualTo("2006-05-04");
    }

    @Test
    public void emptyFilterTest(){
        Filter filter = new Filter();

        filter.normalise();

        assertThat(filter).isNotNull();
        assertThat(filter.getKeyword()).isNull();
        assertThat(filter.getPa()).isNull();
        assertThat(filter.getGeoLocation()).isNull();
        assertThat(filter.getMfaAssistance()).isNull();
        assertThat(filter.getAwardFullFromAmount()).isNull();
        assertThat(filter.getAwardFullToAmount()).isNull();
        assertThat(filter.getConfirmationFromDay()).isNull();
        assertThat(filter.getConfirmationFromMonth()).isNull();
        assertThat(filter.getConfirmationFromYear()).isNull();
        assertThat(filter.getConfirmationToDay()).isNull();
        assertThat(filter.getConfirmationToMonth()).isNull();
        assertThat(filter.getConfirmationToYear()).isNull();
        assertThat(filter.getSchemeStatus()).isNull();
        assertThat(filter.getSchemeStartFromDay()).isNull();
        assertThat(filter.getSchemeStartFromMonth()).isNull();
        assertThat(filter.getSchemeStartFromYear()).isNull();
        assertThat(filter.getSchemeStartToDay()).isNull();
        assertThat(filter.getSchemeStartToMonth()).isNull();
        assertThat(filter.getSchemeStartToYear()).isNull();
        assertThat(filter.getSchemeBudgetFromAmount()).isNull();
        assertThat(filter.getSchemeBudgetToAmount()).isNull();
        assertThat(filter.getSector()).isNull();
        assertThat(filter.getSubsidyPurpose()).isNull();
        assertThat(filter.getSubsidyPurposeOther()).isNull();

        assertThat(filter.getAwardFullAmountFrom()).isNull();
        assertThat(filter.getAwardFullAmountTo()).isNull();
        assertThat(filter.getConfirmationDateFrom()).isNull();
        assertThat(filter.getConfirmationDateTo()).isNull();
        assertThat(filter.getGeoLocations()).isNull();
        assertThat(filter.getIsSpei()).isFalse();
        assertThat(filter.getSectors()).isNull();
        assertThat(filter.getSubsidyPurposes()).isNull();
        assertThat(filter.getSchemeBudgetFrom()).isNull();
        assertThat(filter.getSchemeBudgetTo()).isNull();
        assertThat(filter.getSchemeStartFromDate()).isNull();
        assertThat(filter.getSchemeStartToDate()).isNull();
    }

    @Test
    public void invalidFilterDate(){
        Filter filter = new Filter();

        filter.setSchemeStartFromDay("31");
        filter.setSchemeStartFromMonth("02");
        filter.setSchemeStartFromYear("2003");

        assertThrows(IllegalArgumentException.class, filter::normalise);

        filter.setSchemeStartFromDay("abc");

        assertThrows(IllegalArgumentException.class, filter::normalise);
    }

    @Test
    public void invalidFilterInteger(){
        Filter filter = new Filter();

        filter.setAwardFullFromAmount("abc");

        assertThrows(IllegalArgumentException.class, filter::normalise);
    }

    @Test
    public void invalidFilterBigDecimal(){
        Filter filter = new Filter();

        filter.setSchemeBudgetFromAmount("abc");

        assertThrows(IllegalArgumentException.class, filter::normalise);
    }
}
