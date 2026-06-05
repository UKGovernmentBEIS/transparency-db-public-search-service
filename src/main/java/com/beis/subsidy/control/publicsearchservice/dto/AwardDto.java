package com.beis.subsidy.control.publicsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor

public class AwardDto {

    private BeneficiaryDto beneficiary;

    private SubsidyMeasureSummaryDto subsidyScheme;

    private Long awardNumber;

    private String standaloneAwardTitle;

    private String publicAuthority;

    private String subsidyAmountRange;

    private BigDecimal subsidyAmountExact;

    private String subsidyPurposes;

    private String goodsOrServices;

    private LocalDate awardedDate;

    private LocalDate publishedDate;

    private String geographicalLocation;

    private String subsidyForm;

    private String sector;

    private String status;

    private LocalDate createdTimestamp;

    private LocalDate lastModifiedTimestamp;

    private String standaloneAward;

    private String subsidyAwardDescription;

    private String policyObjective;

    private String subsidyAwardInterest;

    private String authorityURL;

    private String authorityURLDescription;

    private String servicesOfPublicEconomicInterest;

    private String legalBasis;
}