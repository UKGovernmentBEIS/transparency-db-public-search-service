package com.beis.subsidy.control.publicsearchservice.controller.request;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Getter
@Setter

public class Filter {
    private String keyword;
    private String pa;
    private String[] geoLocation;
    private String mfaAssistance;
    private String awardFullFromAmount;
    private String awardFullToAmount;


    private List<String> geoLocations;
    private Boolean isSpei = false;
    private Integer awardFullAmountFrom;
    private Integer awardFullAmountTo;

    public void normalise() {
        keyword = blankToNull(keyword);
        pa = blankToNull(pa);
        if (geoLocation != null) {
            geoLocations = Arrays.stream(geoLocation)
                    .map(this::blankToNull)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());

            if (geoLocations.isEmpty()) {
                geoLocations = null;
            }
        }
        mfaAssistance = blankToNull(mfaAssistance);
        if (mfaAssistance != null && mfaAssistance.equalsIgnoreCase("spei")){
            isSpei = true;
        }
        awardFullAmountFrom = stringToInteger(awardFullFromAmount);
        awardFullAmountTo = stringToInteger(awardFullToAmount);
    }

    private String blankToNull(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }

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
}
