package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class GrantingAuthorityResponseTest {

    @Test
    public void testGrantingAuthorityResponse() {
        Award award = new Award();
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        award.setGrantingAuthority(grantingAuthority);

        GrantingAuthorityResponse gaResponse = new GrantingAuthorityResponse(award.getGrantingAuthority());
        assertThat(gaResponse).isNotNull();
        assertThat(gaResponse.getGrantingAuthorityName()).isNotNull();

    }
}
