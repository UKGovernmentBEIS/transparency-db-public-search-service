package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import com.beis.subsidy.control.publicsearchservice.model.MFAGrouping;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

public class MFAAwardsResponseTest {
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

        MFAAward mfaAward = new MFAAward();
        //MFA Award
        mfaAward.setMfaAwardNumber(1L);
        mfaAward.setSPEI(true);
        mfaAward.setMfaGroupingPresent(true);
        mfaAward.setMfaGroupingNumber("MFA10001");
        mfaAward.setMfaGrouping(mfaGrouping);
        mfaAward.setGrantingAuthority(grantingAuthority);
        mfaAward.setAwardAmount(BigDecimal.valueOf(100000));
        mfaAward.setConfirmationDate(LocalDate.now());
        mfaAward.setPublishedDate(LocalDate.now());
        mfaAward.setRecipientName("A Charity");
        mfaAward.setRecipientIdType("Charity Number");
        mfaAward.setRecipientId("12345678");
        mfaAward.setStatus("Published");
        mfaAward.setCreatedBy("A Person");
        mfaAward.setApprovedBy("Another Person");
        mfaAward.setCreatedTimestamp(LocalDateTime.now());
        mfaAward.setLastModifiedTimestamp(LocalDateTime.now());
        mfaAward.setReason("Reason");
        mfaAward.setDeletedBy("Another Person");
        mfaAward.setDeletedTimestamp(LocalDateTime.now());

        MFAAwardsResponse mfaAwardsResponse = new MFAAwardsResponse(new ArrayList<>(Arrays.asList(mfaAward,mfaAward,mfaAward)),3,1,1);

        assertThat(mfaAwardsResponse.getMfaAwards().size()).isEqualTo(3);
        assertThat(mfaAwardsResponse.getTotalSearchResults()).isEqualTo(3);
        assertThat(mfaAwardsResponse.getCurrentPage()).isEqualTo(1);
        assertThat(mfaAwardsResponse.getTotalPages()).isEqualTo(1);

    }
}
