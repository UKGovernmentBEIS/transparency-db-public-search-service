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
    private String schemeStatus;

    private List<String> geoLocations;

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
        schemeStatus = blankToNull(schemeStatus);
    }

    private String blankToNull(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }
}
