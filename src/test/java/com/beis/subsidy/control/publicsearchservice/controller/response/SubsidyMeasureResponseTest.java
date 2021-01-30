package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.LegalBasis;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class SubsidyMeasureResponseTest {

    @Test
    public void testSubsidyMeasureResponse() {

        Award award = new Award();
        //SubsidyMeasure
        SubsidyMeasure subsidyMeasure = new  SubsidyMeasure();
        subsidyMeasure.setScNumber("SC10001");
        subsidyMeasure.setSubsidyMeasureTitle("title");
        subsidyMeasure.setAdhoc(false);
        subsidyMeasure.setDuration(new BigInteger("200"));
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setGaSubsidyWebLink("www.BEIS.com");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStartDate(LocalDate.now());
        subsidyMeasure.setEndDate(LocalDate.now());
        subsidyMeasure.setBudget("budget");
        subsidyMeasure.setPublishedMeasureDate(LocalDate.now());
        subsidyMeasure.setCreatedBy("SYSTEM");
        subsidyMeasure.setApprovedBy("SYSTEM");
        award.setSubsidyMeasure(subsidyMeasure);

        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);

        SubsidyMeasureResponse subsidyMeasureRes = new SubsidyMeasureResponse(award.getSubsidyMeasure(),false);
        assertThat(subsidyMeasureRes).isNotNull();
        assertThat(subsidyMeasureRes.getScNumber()).isNotNull();
        assertThat(subsidyMeasureRes.getSubsidyMeasureTitle()).isNotNull();
        assertThat(subsidyMeasureRes.isAdhoc());
        assertThat(subsidyMeasureRes.getDuration()).isNull();
        assertThat(subsidyMeasureRes.getStatus()).isNull();
        assertThat(subsidyMeasureRes.getGaSubsidyWebLink()).isNull();
        assertThat(subsidyMeasureRes.getStartDate()).isNull();
        assertThat(subsidyMeasureRes.getEndDate()).isNull();
        assertThat(subsidyMeasureRes.getBudget()).isNull();
        assertThat(subsidyMeasureRes.getPublishedMeasureDate()).isNull();
        assertThat(subsidyMeasureRes.getCreatedBy()).isNull();
        assertThat(subsidyMeasureRes.getApprovedBy()).isNull();
        assertThat(subsidyMeasureRes.getLegalBasis()).isNotNull();

        SubsidyMeasureResponse subsidyMeasureRes1 = new SubsidyMeasureResponse(award.getSubsidyMeasure(),true);
        assertThat(subsidyMeasureRes1).isNotNull();
        assertThat(subsidyMeasureRes1.getScNumber()).isNotNull();
        assertThat(subsidyMeasureRes1.getSubsidyMeasureTitle()).isNotNull();
        assertThat(subsidyMeasureRes1.isAdhoc());
        assertThat(subsidyMeasureRes1.getDuration()).isNotNull();
        assertThat(subsidyMeasureRes1.getGaSubsidyWebLink()).isNotNull();
        assertThat(subsidyMeasureRes1.getStartDate()).isNotNull();
        assertThat(subsidyMeasureRes1.getEndDate()).isNotNull();
        assertThat(subsidyMeasureRes1.getBudget()).isNotNull();
        assertThat(subsidyMeasureRes1.getPublishedMeasureDate()).isNotNull();
        assertThat(subsidyMeasureRes1.getCreatedBy()).isNotNull();
        assertThat(subsidyMeasureRes1.getApprovedBy()).isNotNull();

    }
}
