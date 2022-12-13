package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.*;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class MFAAwardResponseTest {
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

        MFAAwardResponse mfaAwardResponse = new MFAAwardResponse(mfaAward);

        assertThat(mfaAwardResponse.getMfaAwardNumber()).isNotNull();
        assertThat(mfaAwardResponse.getIsSpeiAssistance()).isNotNull();
        assertThat(mfaAwardResponse.isHasMfaGrouping()).isNotNull();
        assertThat(mfaAwardResponse.getMfaGroupingNumber()).isNotNull();
        assertThat(mfaAwardResponse.getMfaGroupingResponse().getMfaGroupingName()).isNotNull();
        assertThat(mfaAwardResponse.getGrantingAuthorityName()).isNotNull();
        assertThat(mfaAwardResponse.getAwardAmount()).isNotNull();
        assertThat(mfaAwardResponse.getConfirmationDate()).isNotNull();
        assertThat(mfaAwardResponse.getPublishedDate()).isNotNull();
        assertThat(mfaAwardResponse.getRecipientName()).isNotNull();
        assertThat(mfaAwardResponse.getRecipientIdType()).isNotNull();
        assertThat(mfaAwardResponse.getRecipientIdNumber()).isNotNull();
        assertThat(mfaAwardResponse.getStatus()).isNotNull();
        assertThat(mfaAwardResponse.getCreatedBy()).isNotNull();
        assertThat(mfaAwardResponse.getApprovedBy()).isNotNull();
        assertThat(mfaAwardResponse.getCreatedTimestamp()).isNotNull();
        assertThat(mfaAwardResponse.getLastModifiedTimestamp()).isNotNull();
        assertThat(mfaAwardResponse.getAwardAmountFormatted()).isNotNull();
        assertThat(mfaAwardResponse.getReason()).isNotNull();
        assertThat(mfaAwardResponse.getDeletedBy()).isNotNull();
        assertThat(mfaAwardResponse.getDeletedTimestamp()).isNotNull();
        assertThat(mfaAwardResponse.getCanApprove()).isNotNull();
    }
}
