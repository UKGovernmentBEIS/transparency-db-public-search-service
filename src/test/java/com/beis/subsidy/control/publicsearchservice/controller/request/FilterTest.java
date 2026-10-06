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
        filter.setFromDay("30");
        filter.setFromMonth("09");
        filter.setFromYear("2026");
        filter.setToDay("31");
        filter.setToMonth("12");
        filter.setToYear("2026");
        filter.setSchemeStatus("Active");
        filter.setSchemeBudgetFromAmount("1000000");
        filter.setSchemeBudgetToAmount("2000000");
        filter.setSector(sectors);
        filter.setSubsidyPurpose(purposes);
        filter.setSubsidyPurposeOther("Other purpose");

        // normalized values should be null before normalise
        assertThat(filter.getAwardFullAmountFrom()).isNull();
        assertThat(filter.getAwardFullAmountTo()).isNull();
        assertThat(filter.getFromDate()).isNull();
        assertThat(filter.getToDate()).isNull();
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
        assertThat(filter.getFromDay()).isEqualTo("30");
        assertThat(filter.getFromMonth()).isEqualTo("09");
        assertThat(filter.getFromYear()).isEqualTo("2026");
        assertThat(filter.getToDay()).isEqualTo("31");
        assertThat(filter.getToMonth()).isEqualTo("12");
        assertThat(filter.getToYear()).isEqualTo("2026");
        assertThat(filter.getSchemeStatus()).isEqualTo("Active");
        assertThat(filter.getSchemeBudgetFromAmount()).isEqualTo("1000000");
        assertThat(filter.getSchemeBudgetToAmount()).isEqualTo("2000000");
        assertThat(filter.getSector()).isEqualTo(sectors);
        assertThat(filter.getSubsidyPurpose()).isEqualTo(purposes);
        assertThat(filter.getSubsidyPurposeOther()).isEqualTo("Other purpose");

        assertThat(filter.getAwardFullAmountFrom()).isEqualTo(100);
        assertThat(filter.getAwardFullAmountTo()).isEqualTo(200);
        assertThat(filter.getFromDate()).isEqualTo("2026-09-30");
        assertThat(filter.getToDate()).isEqualTo("2026-12-31");
        assertThat(filter.getGeoLocations()).isNotEmpty();
        assertThat(filter.getGeoLocations()).containsAll(new ArrayList<>(Arrays.asList(geoLocations)));
        assertThat(filter.getIsSpei()).isTrue();
        assertThat(filter.getSectors()).containsAll(new ArrayList<>(Arrays.asList(sectors)));
        assertThat(filter.getSubsidyPurposes()).containsAll(new ArrayList<>(Arrays.asList(purposes)));
        assertThat(filter.getSchemeBudgetFrom()).isEqualTo(BigDecimal.valueOf(Long.parseLong("1000000")));
        assertThat(filter.getSchemeBudgetTo()).isEqualTo(BigDecimal.valueOf(Long.parseLong("2000000")));
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
        assertThat(filter.getFromDay()).isNull();
        assertThat(filter.getFromMonth()).isNull();
        assertThat(filter.getFromYear()).isNull();
        assertThat(filter.getToDay()).isNull();
        assertThat(filter.getToMonth()).isNull();
        assertThat(filter.getToYear()).isNull();
        assertThat(filter.getSchemeStatus()).isNull();
        assertThat(filter.getSchemeBudgetFromAmount()).isNull();
        assertThat(filter.getSchemeBudgetToAmount()).isNull();
        assertThat(filter.getSector()).isNull();
        assertThat(filter.getSubsidyPurpose()).isNull();
        assertThat(filter.getSubsidyPurposeOther()).isNull();

        assertThat(filter.getAwardFullAmountFrom()).isNull();
        assertThat(filter.getAwardFullAmountTo()).isNull();
        assertThat(filter.getFromDate()).isNull();
        assertThat(filter.getToDate()).isNull();
        assertThat(filter.getGeoLocations()).isNull();
        assertThat(filter.getIsSpei()).isFalse();
        assertThat(filter.getSectors()).isNull();
        assertThat(filter.getSubsidyPurposes()).isNull();
        assertThat(filter.getSchemeBudgetFrom()).isNull();
        assertThat(filter.getSchemeBudgetTo()).isNull();
    }

    @Test
    public void invalidFilterDate(){
        Filter filter = new Filter();

        filter.setFromDay("31");
        filter.setFromMonth("02");
        filter.setFromYear("2003");

        assertThrows(IllegalArgumentException.class, filter::normalise);

        filter.setFromDay("abc");

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
