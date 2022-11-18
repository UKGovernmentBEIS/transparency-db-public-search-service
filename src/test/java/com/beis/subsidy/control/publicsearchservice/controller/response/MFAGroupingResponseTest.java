package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.MFAGrouping;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MFAGroupingResponseTest {
    @Test
    public void testSubsidyMeasureResponse() {

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("TEST GA");

        //MFA Grouping
        MFAGrouping mfaGrouping = new MFAGrouping();
        mfaGrouping.setMfaGroupingName("MFA Grouping");
        mfaGrouping.setMfaGroupingNumber("MFA10001");
        mfaGrouping.setGaId(1L);
        mfaGrouping.setGrantingAuthority(grantingAuthority);
        mfaGrouping.setCreatedBy("A Person");
        mfaGrouping.setStatus("Active");
        mfaGrouping.setCreatedTimestamp(LocalDateTime.now());
        mfaGrouping.setLastModifiedTimestamp(LocalDateTime.now());
        mfaGrouping.setDeletedBy("Another Person");
        mfaGrouping.setDeletedTimestamp(LocalDateTime.now());

        MFAGroupingResponse mfaGroupingResponse = new MFAGroupingResponse(mfaGrouping);

        assertThat(mfaGroupingResponse.getMfaGroupingNumber()).isNotNull();
        assertThat(mfaGroupingResponse.getCreatedBy()).isNotNull();
        assertThat(mfaGroupingResponse.getStatus()).isNotNull();
        assertThat(mfaGroupingResponse.getMfaGroupingName()).isNotNull();
        assertThat(mfaGroupingResponse.getGrantingAuthorityName()).isNotNull();
        assertThat(mfaGroupingResponse.getLastModifiedTimestamp()).isNotNull();
        assertThat(mfaGroupingResponse.getCreatedTimestamp()).isNotNull();
        assertThat(mfaGroupingResponse.getDeletedBy()).isNotNull();
        assertThat(mfaGroupingResponse.getDeletedTimestamp()).isNotNull();
    }
}
