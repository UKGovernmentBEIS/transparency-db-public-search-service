package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
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
    public void testTimestampToFullMonthNameInDate(){
        Date date = new GregorianCalendar(2022, Calendar.FEBRUARY, 22).getTime();
        String dateString = SearchUtils.timestampToFullMonthNameInDate(date);
        assertThat(dateString).isNotNull();
        assertThat(dateString).isEqualTo("22 February 2022");
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
        String dataIp =">1000000 - 2000000";
        String formatAmountRange = null;
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp);
        assertThat(formatAmountRange).isNotNull();
        assertThat(formatAmountRange.contains(">")).isTrue();
        assertThat(formatAmountRange.contains("-")).isTrue();
        assertThat(formatAmountRange.contains("£")).isTrue();
        assertThat(formatAmountRange.contains(" ")).isTrue();

        String dataIp1 ="1000000 - 2000000";
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp1);
        assertThat(formatAmountRange).isNotNull();
        assertThat(formatAmountRange.contains("-")).isTrue();
        assertThat(formatAmountRange.contains("£")).isTrue();

        String dataIp2 ="50000";
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp2);
        assertThat(formatAmountRange.contains("-")).isFalse();
        assertThat(formatAmountRange.contains("£")).isTrue();

        String dataIp3 = null;
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp3);
        assertThat(formatAmountRange).isNotNull();
        assertThat(formatAmountRange).isEqualTo("NA");

        String dataIp4 = "0";
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp4);
        assertThat(formatAmountRange).isNotNull();
        assertThat(formatAmountRange).isEqualTo("0");

        String dataIp5 ="1000000 or more";
        formatAmountRange = SearchUtils.formatedFullAmountRange(dataIp5);
        assertThat(formatAmountRange).isNotNull();
        assertThat(formatAmountRange.contains("or more")).isTrue();
        assertThat(formatAmountRange.contains("£")).isTrue();
    }

    @Test
    public void testIsDateValid(){
        String validDateString = "2022-02-22";
        String invalidDateString = "2022-13-32";

        Boolean isDateValid = SearchUtils.isDateValid(validDateString);
        assertTrue(isDateValid);

        isDateValid = SearchUtils.isDateValid(invalidDateString);
        assertFalse(isDateValid);
    }

    @Test
    public void testRemoveRolesFromGaList(){
        List<String> gaNames = Arrays.asList("Test GA", "BEIS Administrator", "Granting Authority Encoder",
                "Granting Authority Administrator", "Granting Authority Approver", "BEIS");
        List<GrantingAuthority> gaList = new ArrayList<>();

        for (String gaName:gaNames) {
            GrantingAuthority ga = new GrantingAuthority();
            ga.setGrantingAuthorityName(gaName);
            gaList.add(ga);
        }

        SearchUtils.removeRolesFromGaList(gaList);

        for(GrantingAuthority ga:gaList) {
            assertThat(ga.getGrantingAuthorityName()).isNotEqualTo("BEIS Administrator");
            assertThat(ga.getGrantingAuthorityName()).isNotEqualTo("Granting Authority Encoder");
            assertThat(ga.getGrantingAuthorityName()).isNotEqualTo("Granting Authority Administrator");
            assertThat(ga.getGrantingAuthorityName()).isNotEqualTo("Granting Authority Approver");
        }
    }

    @Test
    public void testisNumeric() {
        String numberString = "100";
        Boolean isNumeric = SearchUtils.isNumeric(numberString);
        assertThat(isNumeric).isTrue();
    }

    @Test
    public void testisNumericNull() {
        String numberString = null;
        Boolean isNumeric = SearchUtils.isNumeric(numberString);
        assertThat(isNumeric).isFalse();
    }

    @Test
    public void testisNumericNotNumeric() {
        String numberString = "X";
        Boolean isNumeric = SearchUtils.isNumeric(numberString);
        assertThat(isNumeric).isFalse();
    }
}
