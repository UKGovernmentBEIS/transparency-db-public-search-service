package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class GrantingAuthorityResponse {

    @JsonProperty
    private String grantingAuthorityName ;

    public GrantingAuthorityResponse(GrantingAuthority grantingAuthority) {
        this.grantingAuthorityName = grantingAuthority.getGrantingAuthorityName();
    }
}
