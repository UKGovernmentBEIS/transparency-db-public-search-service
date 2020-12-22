package com.beis.subsidy.control.publicsearchservice.utils;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SearchUtilsTest {

    @Test
    public void testDecimalNumberFormat() {
        BigDecimal subsidyFullAmountExact = new BigDecimal("150000.0");
        String fullAmountExact = SearchUtils.decimalNumberFormat(subsidyFullAmountExact);
        assertThat(fullAmountExact).isNotNull();
        assertTrue(fullAmountExact.contains(","));
    }

    @Test
    public void testDateToFullMonthNameInDate() {
        LocalDate toDayDate = LocalDate.now();
        String monthNameInDate = SearchUtils.dateToFullMonthNameInDate(toDayDate);
        assertThat(monthNameInDate).isNotNull();
        assertTrue(monthNameInDate.contains(" "));
    }

    @Test
    public void testStringToDate() {
        String dateIp ="2020-12-01";
        LocalDate monthDate = SearchUtils.stringToDate(dateIp);
        assertThat(monthDate).isNotNull();
    }

    @Test
    public void testCheckNullOrEmptyString() {
        String dataIp ="input";
        boolean outPut = SearchUtils.checkNullOrEmptyString(dataIp);
        assertThat(outPut).isNotNull();
        assertThat(outPut).isFalse();

        String dataEmpty= null;
        boolean outPut1 = SearchUtils.checkNullOrEmptyString(dataEmpty);
        assertThat(outPut1).isNotNull();
        assertThat(outPut1).isTrue();
    }

    @Test
    public void testFormatAmountRange() {
        String dataIp ="50000-4000";
        String formatAmountRange = null;
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp);
        assertThat(formatAmountRange).isNotNull();
        assertThat(formatAmountRange.contains("-")).isTrue();
        assertThat(formatAmountRange.contains("£")).isTrue();
        String dataIp1 ="50000";
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp1);
        assertThat(formatAmountRange.contains("-")).isFalse();
        assertThat(formatAmountRange.contains("£")).isTrue();
    }
}
