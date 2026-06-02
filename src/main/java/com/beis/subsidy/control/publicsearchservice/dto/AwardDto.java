package com.beis.subsidy.control.publicsearchservice.dto;

import com.beis.subsidy.control.publicsearchservice.model.AdminProgram;
import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@AllArgsConstructor

public class AwardDto {

    private BeneficiaryDto beneficiary;

    private SubsidyMeasureDto subsidyScheme;

    private Long awardNumber;

    private String grantingAuthority;

    private String subsidyFullAmountRange;

    private BigDecimal subsidyFullAmountExact;

    private String subsidyObjective;

    private String goodsServicesFilter;

    private LocalDate legalGrantingDate;

    private LocalDate publishedAwardDate;

    private String spendingRegion;

    private String subsidyInstrument;

    private String spendingSector;

    private String status;

    private LocalDate createdTimestamp;

    private LocalDate lastModifiedTimestamp;

    private String standaloneAward;

    private String subsidyAwardDescription;

    private String specificPolicyObjective;

    private String adminProgramName;

    private String subsidyAwardInterest;

    private String authorityURL;

    private String authorityURLDescription;

    private String spei;

    private String legalBasis;

    private String standaloneAwardTitle;
}