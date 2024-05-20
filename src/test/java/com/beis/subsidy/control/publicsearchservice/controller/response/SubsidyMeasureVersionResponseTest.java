package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasureVersion;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class SubsidyMeasureVersionResponseTest {

    @Test
    public void testSubsidyMeasureVersionResponse(){
        SubsidyMeasureVersion smv = new SubsidyMeasureVersion();
        GrantingAuthority ga = new GrantingAuthority();

        ga.setGrantingAuthorityName("TEST GA");
        ga.setGaId(Long.parseLong("1"));

        UUID uuid = UUID.randomUUID();
        smv.setVersion(uuid);
        smv.setSubsidyMeasureTitle("Title");
        smv.setScNumber("SC10001");
        smv.setStartDate(LocalDate.now());
        smv.setEndDate(LocalDate.now());
        smv.setDuration(new BigInteger("365"));
        smv.setBudget("1000");
        smv.setGrantingAuthority(ga);
        smv.setGaId(ga.getGaId());
        smv.setAdhoc(false);
        smv.setGaSubsidyWebLink("Link");
        smv.setGaSubsidyWebLinkDescription("Description");
        smv.setLastModifiedTimestamp(LocalDateTime.now());
        smv.setCreatedTimestamp(LocalDateTime.now());
        smv.setLegalBasisText("Legal basis test");
        smv.setStatus("Status");
        smv.setPublishedMeasureDate(LocalDate.now());
        smv.setDeletedBy("Nobody");
        smv.setDeletedTimestamp(LocalDateTime.now());
        smv.setHasNoEndDate(false);
        smv.setSubsidySchemeDescription("Description");
        smv.setConfirmationDate(LocalDate.now());
        smv.setSpendingSectors("Spending sectors, another sector");
        smv.setMaximumAmountUnderScheme("10%");

        SubsidyMeasureVersionResponse subsidyMeasureVersionResponse = new SubsidyMeasureVersionResponse(smv);

        assertNotNull(subsidyMeasureVersionResponse.getVersion());
        assertNotNull(subsidyMeasureVersionResponse.getSubsidyMeasureTitle());
        assertNotNull(subsidyMeasureVersionResponse.getScNumber());
        assertNotNull(subsidyMeasureVersionResponse.getStartDate());
        assertNotNull(subsidyMeasureVersionResponse.getEndDate());
        assertNotNull(subsidyMeasureVersionResponse.getDuration());
        assertNotNull(subsidyMeasureVersionResponse.getBudget());
        assertNotNull(subsidyMeasureVersionResponse.getGaName());
        assertNotNull(subsidyMeasureVersionResponse.getAdhoc());
        assertNotNull(subsidyMeasureVersionResponse.getGaSubsidyWebLink());
        assertNotNull(subsidyMeasureVersionResponse.getGaSubsidyWebLinkDescription());
        assertNotNull(subsidyMeasureVersionResponse.getLastModifiedDate());
        assertNotNull(subsidyMeasureVersionResponse.getCreatedDate());
        assertNotNull(subsidyMeasureVersionResponse.getLegalBasisText());
        assertNotNull(subsidyMeasureVersionResponse.getStatus());
        assertNotNull(subsidyMeasureVersionResponse.getPublishedMeasureDate());
        assertNotNull(subsidyMeasureVersionResponse.getDeletedBy());
        assertNotNull(subsidyMeasureVersionResponse.getDeletedTimestamp());
        assertFalse(subsidyMeasureVersionResponse.isHasNoEndDate());
        assertNotNull(subsidyMeasureVersionResponse.getSubsidySchemeDescription());
        assertNotNull(subsidyMeasureVersionResponse.getConfirmationDate());
        assertNotNull(subsidyMeasureVersionResponse.getSpendingSectors());
        assertNotNull(subsidyMeasureVersionResponse.getMaximumAmountUnderScheme());
    }
}
