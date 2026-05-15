package com.beis.subsidy.control.publicsearchservice.utils;

import javax.servlet.http.HttpServletRequest;

public class ApiUtils {
    public static String getSiteUrl(HttpServletRequest request) {
        return request.getScheme() + "://" + request.getServerName()
                + (request.getServerPort() == 80 || request.getServerPort() == 443
                ? ""
                : ":" + request.getServerPort());
    }
}
