package com.beis.subsidy.control.publicsearchservice.controller.request;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class FilterTest {

    @Test
    public void testPopulatedFilter() {
        Filter filter = new Filter();

        String[] geoLocations = {"UK-Wide", "England"};

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

        // normalized values should be null before normalise
        assertThat(filter.getAwardFullAmountFrom()).isNull();
        assertThat(filter.getAwardFullAmountTo()).isNull();
        assertThat(filter.getConfirmationDateFrom()).isNull();
        assertThat(filter.getConfirmationDateTo()).isNull();
        assertThat(filter.getGeoLocations()).isNull();
        assertThat(filter.getIsSpei()).isFalse();

        filter.normalise();

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

        assertThat(filter).isNotNull();
        assertThat(filter.getAwardFullAmountFrom()).isEqualTo(100);
        assertThat(filter.getAwardFullAmountTo()).isEqualTo(200);
        assertThat(filter.getConfirmationDateFrom()).isEqualTo("2026-09-30");
        assertThat(filter.getConfirmationDateTo()).isEqualTo("2026-12-31");
        assertThat(filter.getGeoLocations()).isNotEmpty();
        assertThat(filter.getGeoLocations()).containsAll(new ArrayList<String>(Arrays.asList(geoLocations)));
        assertThat(filter.getIsSpei()).isTrue();
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

        assertThat(filter.getAwardFullAmountFrom()).isNull();
        assertThat(filter.getAwardFullAmountTo()).isNull();
        assertThat(filter.getConfirmationDateFrom()).isNull();
        assertThat(filter.getConfirmationDateTo()).isNull();
        assertThat(filter.getGeoLocations()).isNull();
        assertThat(filter.getIsSpei()).isFalse();
    }
}
