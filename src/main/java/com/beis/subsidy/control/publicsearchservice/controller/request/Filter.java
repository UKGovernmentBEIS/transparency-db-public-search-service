package com.beis.subsidy.control.publicsearchservice.controller.request;

public class Filter {
    private String keyword;
    private String ga;
    private String geoLocation;

    public void normalise() {
        keyword = blankToNull(keyword);
        ga = blankToNull(ga);
        geoLocation = blankToNull(geoLocation);
    }

    private String blankToNull(String value) {
        return value == null || value.trim().isEmpty() ? null : value.trim();
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getGa() {
        return ga;
    }

    public void setGa(String ga) {
        this.ga = ga;
    }

    public String getGeoLocation() {
        return geoLocation;
    }

    public void setGeoLocation(String geoLocation) {
        this.geoLocation = geoLocation;
    }
}
