package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.LegalBasis;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

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
        subsidyMeasure.setGaSubsidyWebLinkDescription("www.BEIS.com description");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStartDate(LocalDate.now());
        subsidyMeasure.setEndDate(LocalDate.now());
        subsidyMeasure.setCreatedTimestamp(new Date());
        subsidyMeasure.setLastModifiedTimestamp(new Date());
        subsidyMeasure.setBudget("1000000");
        subsidyMeasure.setPublishedMeasureDate(LocalDate.now());
        subsidyMeasure.setCreatedBy("SYSTEM");
        subsidyMeasure.setApprovedBy("SYSTEM");
        award.setSubsidyMeasure(subsidyMeasure);

        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        subsidyMeasure.setGrantingAuthority(grantingAuthority);

        SubsidyMeasureResponse subsidyMeasureRes = new SubsidyMeasureResponse(award.getSubsidyMeasure(),false);
        assertThat(subsidyMeasureRes).isNotNull();
        assertThat(subsidyMeasureRes.getScNumber()).isNotNull();
        assertThat(subsidyMeasureRes.getSubsidyMeasureTitle()).isNotNull();
        assertThat(subsidyMeasureRes.isAdhoc());
        assertThat(subsidyMeasureRes.getDuration()).isNull();
        assertThat(subsidyMeasureRes.getStatus()).isNull();
        assertThat(subsidyMeasureRes.getGaSubsidyWebLink()).isNull();
        assertThat(subsidyMeasureRes.getGaSubsidyWebLinkDescription()).isNull();
        assertThat(subsidyMeasureRes.getStartDate()).isNull();
        assertThat(subsidyMeasureRes.getEndDate()).isNull();
        assertThat(subsidyMeasureRes.getBudget()).isNull();
        assertThat(subsidyMeasureRes.getPublishedMeasureDate()).isNull();
        assertThat(subsidyMeasureRes.getCreatedBy()).isNull();
        assertThat(subsidyMeasureRes.getApprovedBy()).isNull();
        assertThat(subsidyMeasureRes.getLegalBasis()).isNotNull();
        assertThat(subsidyMeasureRes.getCreatedTimestamp()).isNull();
        assertThat(subsidyMeasureRes.getLastModifiedTimestamp()).isNull();
        assertThat(subsidyMeasureRes.getAwardSearchResults()).isNull();

        SubsidyMeasureResponse subsidyMeasureRes1 = new SubsidyMeasureResponse(award.getSubsidyMeasure(),true);
        SearchResults searchResults = new SearchResults();
        searchResults.setAwards(Arrays.asList(mock(AwardResponse.class)));
        subsidyMeasureRes1.setAwardSearchResults(searchResults);
        assertThat(subsidyMeasureRes1).isNotNull();
        assertThat(subsidyMeasureRes1.getScNumber()).isNotNull();
        assertThat(subsidyMeasureRes1.getSubsidyMeasureTitle()).isNotNull();
        assertThat(subsidyMeasureRes1.isAdhoc());
        assertThat(subsidyMeasureRes1.getDuration()).isNotNull();
        assertThat(subsidyMeasureRes1.getGaSubsidyWebLink()).isNotNull();
        assertThat(subsidyMeasureRes1.getGaSubsidyWebLinkDescription()).isNotNull();
        assertThat(subsidyMeasureRes1.getStartDate()).isNotNull();
        assertThat(subsidyMeasureRes1.getEndDate()).isNotNull();
        assertThat(subsidyMeasureRes1.getBudget()).isNotNull();
        assertThat(subsidyMeasureRes1.getPublishedMeasureDate()).isNotNull();
        assertThat(subsidyMeasureRes1.getCreatedBy()).isNotNull();
        assertThat(subsidyMeasureRes1.getApprovedBy()).isNotNull();
        assertThat(subsidyMeasureRes1.getCreatedTimestamp()).isNotNull();
        assertThat(subsidyMeasureRes1.getLastModifiedTimestamp()).isNotNull();
        assertThat(subsidyMeasureRes1.getAwardSearchResults()).isNotNull();

    }
}
