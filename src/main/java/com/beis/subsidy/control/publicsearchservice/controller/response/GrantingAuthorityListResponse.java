package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * Subsidy Schemes object - Represents list of subsidy schemes (subsidy measures)
 *
 */

@Setter
@Getter
@NoArgsConstructor
public class GrantingAuthorityListResponse {
    public List<GrantingAuthority> gaList;

    public GrantingAuthorityListResponse(List<GrantingAuthority> gaList) {
        this.gaList = gaList;
    }
}
