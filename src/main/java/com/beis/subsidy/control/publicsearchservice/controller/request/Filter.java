package com.beis.subsidy.control.publicsearchservice.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.DateTimeException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter

public class Filter {
    // Values from front end
    private String keyword;
    private String pa;
    private String[] geoLocation;
    private String schemeStatus;
    private String schemeStartFromDay;
    private String schemeStartFromMonth;
    private String schemeStartFromYear;
    private String schemeStartToDay;
    private String schemeStartToMonth;
    private String schemeStartToYear;
    private String schemeBudgetFromAmount;
    private String schemeBudgetToAmount;
    private String[] sector;
    private String[] subsidyPurpose;
    private String subsidyPurposeOther;
    private String subsidyInterest;
    private String mfaAssistance;
    private String awardFullFromAmount;
    private String awardFullToAmount;
    private String confirmationFromDay;
    private String confirmationFromMonth;
    private String confirmationFromYear;
    private String confirmationToDay;
    private String confirmationToMonth;
    private String confirmationToYear;

    // Converted values for use in filtering
    private List<String> geoLocations;
    private List<String> sectors;
    private List<String> subsidyPurposes;

    private Boolean isSpei = false;

    private Integer awardFullAmountFrom;
    private Integer awardFullAmountTo;
    private BigDecimal schemeBudgetFrom;
    private BigDecimal schemeBudgetTo;

    private LocalDate confirmationDateFrom;
    private LocalDate confirmationDateTo;
    private LocalDate schemeStartFromDate;
    private LocalDate schemeStartToDate;

    public void normalise() {
        keyword = blankToNull(keyword);
        pa = blankToNull(pa);
        schemeStartFromDay = blankToNull(schemeStartFromDay);
        schemeStartFromMonth = blankToNull(schemeStartFromMonth);
        schemeStartFromYear = blankToNull(schemeStartFromYear);
        schemeStartToDay = blankToNull(schemeStartToDay);
        schemeStartToMonth = blankToNull(schemeStartToMonth);
        schemeStartToYear = blankToNull(schemeStartToYear);
        schemeBudgetFrom = stringToBigDecimal(schemeBudgetFromAmount);
        schemeBudgetTo = stringToBigDecimal(schemeBudgetToAmount);
        geoLocations = stringArrayToList(geoLocation);
        sectors = stringArrayToList(sector);
        subsidyPurposes = stringArrayToList(subsidyPurpose);
        subsidyPurposeOther = blankToNull(subsidyPurposeOther);
        subsidyInterest = blankToNull(subsidyInterest);
        schemeStatus = blankToNull(schemeStatus);
        schemeStartFromDate = stringToDate(schemeStartFromDay, schemeStartFromMonth, schemeStartFromYear);
        schemeStartToDate = stringToDate(schemeStartToDay, schemeStartToMonth, schemeStartToYear);
        mfaAssistance = blankToNull(mfaAssistance);
        if (mfaAssistance != null && mfaAssistance.equalsIgnoreCase("spei")){
            isSpei = true;
        }
        awardFullAmountFrom = stringToInteger(awardFullFromAmount);
        awardFullAmountTo = stringToInteger(awardFullToAmount);

        confirmationDateFrom = stringToDate(confirmationFromDay, confirmationFromMonth, confirmationFromYear);
        confirmationDateTo = stringToDate(confirmationToDay, confirmationToMonth, confirmationToYear);
    }

    private String blankToNull(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("dd/MM/yyyy");

    private Integer stringToInteger(String value) {
        String normalisedValue = blankToNull(value);

        if (normalisedValue == null) {
            return null;
        }

        try {
            return Integer.valueOf(normalisedValue);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Value must be a valid whole number: " + value,
                    exception
            );
        }
    }

    private BigDecimal stringToBigDecimal(String value){
        String normalisedValue = blankToNull(value);

        if (normalisedValue == null) {
            return null;
        }

        try {
            return BigDecimal.valueOf(Long.parseLong(normalisedValue));
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(
                    "Value must be a valid whole number: " + value,
                    exception
            );
        }
    }

    private LocalDate stringToDate(String day, String month, String year) {
        if (isBlank(day) && isBlank(month) && isBlank(year)) {
            return null;
        }

        try {
            return LocalDate.of(
                    Integer.parseInt(year.trim()),
                    Integer.parseInt(month.trim()),
                    Integer.parseInt(day.trim())
            );
        } catch (NumberFormatException | DateTimeException exception) {
            throw new IllegalArgumentException(
                    "Invalid date: " + day + "-" + month + "-" + year,
                    exception
            );
        }
    }

    private List<String> stringArrayToList(String[] array){
        List<String> list = null;
        if (array != null) {
            list = Arrays.stream(array)
                    .map(this::blankToNull)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }
        return list;
    }

    private boolean isBlank(String value) {
        return value == null || value.trim().isEmpty();
    }
}
