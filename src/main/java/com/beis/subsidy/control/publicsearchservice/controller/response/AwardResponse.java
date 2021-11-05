package com.beis.subsidy.control.publicsearchservice.controller.response;

import java.time.LocalDate;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AwardResponse {
    @JsonProperty
    private Long awardNumber;

    @JsonProperty
    private BeneficiaryResponse beneficiary;

    @JsonProperty
    private SubsidyMeasureResponse subsidyMeasure;

    @JsonProperty
    private GrantingAuthorityResponse grantingAuthorityResponse;

    @JsonProperty
    private String subsidyFullAmountRange;

    @JsonProperty
    private String subsidyFullAmountExact;

    @JsonProperty
    private String subsidyObjective;

    @JsonProperty
    private String subsidyInstrument;

    @JsonProperty
    private String spendingSector;

    @JsonProperty
    private String goodsServicesFilter;

    @JsonProperty
    private String legalGrantingDate;

    @JsonProperty
    private String createdBy;

    @JsonProperty
    private String approvedBy;

    @JsonProperty
    private String status;

    @JsonProperty
    private String rejectReason;

    @JsonProperty
    private String spendingRegion;

    @JsonProperty
    private LocalDate createdTimestamp;

    @JsonProperty
    private LocalDate lastModifiedTimestamp;

    @JsonProperty
    private LocalDate publishedAwardDate;

    @JsonProperty
    private String gaName;

    @JsonProperty
    private String idType;

    @JsonProperty
    private String idNumber;


    public AwardResponse(Award award, boolean flag) {
    	
    	log.info("inside  AwardResponse::");
        this.awardNumber = award.getAwardNumber();
        this.subsidyFullAmountRange = SearchUtils.formatedFullAmountRange(award.getSubsidyFullAmountRange());
        this.subsidyFullAmountExact = SearchUtils.decimalNumberFormat(award.getSubsidyFullAmountExact());
        this.subsidyObjective = award.getSubsidyObjective();
        this.spendingSector = award.getSpendingSector();
        this.subsidyInstrument = award.getSubsidyInstrument();
        this.spendingRegion = award.getSpendingRegion();
        this.legalGrantingDate = SearchUtils.dateToFullMonthNameInDate(award.getLegalGrantingDate());
        this.gaName = award.getGrantingAuthority().getGrantingAuthorityName();
        if (flag) {
            this.goodsServicesFilter = award.getGoodsServicesFilter();
            this.status = award.getStatus();
            this.createdBy = award.getCreatedBy();
            this.approvedBy = award.getApprovedBy();
            this.createdTimestamp = award.getCreatedTimestamp();
            this.lastModifiedTimestamp = award.getLastModifiedTimestamp();
            this.publishedAwardDate = award.getPublishedAwardDate();
        }
        this.rejectReason = award.getReason()!= null ?  award.getReason().trim(): null;
        this.beneficiary = new BeneficiaryResponse(award.getBeneficiary(),flag);
        this.idType = award.getBeneficiary().getNationalIdType();
        this.idNumber = award.getBeneficiary().getNationalId();
        this.subsidyMeasure = new SubsidyMeasureResponse(award.getSubsidyMeasure(),flag);
        if (flag) {
         this.grantingAuthorityResponse = new GrantingAuthorityResponse(award.getGrantingAuthority());
        }
    }
}
