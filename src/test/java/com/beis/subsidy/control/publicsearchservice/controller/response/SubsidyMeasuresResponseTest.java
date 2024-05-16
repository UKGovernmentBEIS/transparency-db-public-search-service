package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.LegalBasis;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.junit.jupiter.api.Test;

import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class SubsidyMeasuresResponseTest {
    @Test
    public void testSubsidyMeasureResponse() {
        //SubsidyMeasure
        SubsidyMeasure subsidyMeasure = new  SubsidyMeasure();
        subsidyMeasure.setScNumber("SC10001");
        subsidyMeasure.setSubsidyMeasureTitle("title");
        subsidyMeasure.setAdhoc(false);
        subsidyMeasure.setDuration(new BigInteger("200"));
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setGaSubsidyWebLink("www.BEIS.com");
        subsidyMeasure.setStatus("DEFAULT");
        subsidyMeasure.setStartDate(LocalDate.now());
        subsidyMeasure.setEndDate(LocalDate.now());
        subsidyMeasure.setCreatedTimestamp(LocalDateTime.now());
        subsidyMeasure.setLastModifiedTimestamp(LocalDateTime.now());
        subsidyMeasure.setBudget("1000000");
        subsidyMeasure.setPublishedMeasureDate(LocalDate.now());
        subsidyMeasure.setCreatedBy("SYSTEM");
        subsidyMeasure.setApprovedBy("SYSTEM");
        subsidyMeasure.setSchemeVersions(new ArrayList<>());

        LegalBasis legalBasis = new LegalBasis();
        legalBasis.setLegalBasisText("legal text");
        subsidyMeasure.setLegalBases(legalBasis);

        //GrantingAuthority details
        GrantingAuthority grantingAuthority = new GrantingAuthority();
        grantingAuthority.setGrantingAuthorityName("ganame");
        subsidyMeasure.setGrantingAuthority(grantingAuthority);

        List<SubsidyMeasure> subsidyMeasureList = new ArrayList<>();

        subsidyMeasureList.add(subsidyMeasure);

        SubsidyMeasuresResponse subsidyMeasuresResponse = new SubsidyMeasuresResponse(subsidyMeasureList, 1, 2, 3);
        assertThat(subsidyMeasuresResponse).isNotNull();
        assertThat(subsidyMeasuresResponse.getSubsidySchemes().size()).isEqualTo(1);
        assertThat(subsidyMeasuresResponse.getTotalResults()).isEqualTo(1);
        assertThat(subsidyMeasuresResponse.getCurrentPage()).isEqualTo(2);
        assertThat(subsidyMeasuresResponse.getTotalPages()).isEqualTo(3);

        SubsidyMeasureResponse subsidyMeasureResponse = new SubsidyMeasureResponse(subsidyMeasure, true);
        List<SubsidyMeasureResponse> subsidyMeasureResponseList = new ArrayList<>();
        subsidyMeasureResponseList.add(subsidyMeasureResponse);

        SubsidyMeasuresResponse subsidyMeasuresResponse1 = new SubsidyMeasuresResponse(1, 2, 3, subsidyMeasureResponseList);
        assertThat(subsidyMeasuresResponse1.getSubsidySchemes().size()).isEqualTo(1);
        assertThat(subsidyMeasuresResponse1.getTotalResults()).isEqualTo(1);
        assertThat(subsidyMeasuresResponse1.getCurrentPage()).isEqualTo(2);
        assertThat(subsidyMeasuresResponse1.getTotalPages()).isEqualTo(3);

        SubsidyMeasuresResponse subsidyMeasuresResponse2 = new SubsidyMeasuresResponse();
        subsidyMeasuresResponse2.setTotalResults(1);
        subsidyMeasuresResponse2.setCurrentPage(2);
        subsidyMeasuresResponse2.setTotalPages(3);

        assertThat(subsidyMeasuresResponse2.getTotalResults()).isEqualTo(1);
        assertThat(subsidyMeasuresResponse2.getCurrentPage()).isEqualTo(2);
        assertThat(subsidyMeasuresResponse2.getTotalPages()).isEqualTo(3);
    }
}
