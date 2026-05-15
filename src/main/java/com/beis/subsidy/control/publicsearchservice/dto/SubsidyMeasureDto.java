package com.beis.subsidy.control.publicsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class SubsidyMeasureDto {
    private String scNumber;

    private String subsidyMeasureTitle;

    private LocalDate startDate;

    private LocalDate endDate;

    private BigInteger duration;

    private String budget;

    private String gaSubsidyWebLink;

    private String gaSubsidyWebLinkDescription;

    private LocalDate publishedMeasureDate;

    private String status;

    private LocalDateTime createdTimestamp;

    private LocalDateTime lastModifiedTimestamp;

    private String subsidySchemeDescription;

    private String specificPolicyObjective;

    private LocalDate confirmationDate;

    private String spendingSectors;

    private String maximumAmountUnderScheme;

    private String purpose;

    private String subsidySchemeInterest;

    private String legalBasis;
}
