package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasureVersion;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Slf4j
public class SubsidyMeasureVersionResponse {
    @JsonProperty
    private String version;
    @JsonProperty
    private String subsidyMeasureTitle;

    @JsonProperty
    private String scNumber;

    @JsonProperty
    private String startDate;

    @JsonProperty
    private String endDate;

    @JsonProperty
    private BigInteger duration;

    @JsonProperty
    private String budget;

    @JsonProperty
    private String gaName;

    @JsonProperty
    private String adhoc;

    @JsonProperty
    private String gaSubsidyWebLink;

    @JsonProperty
    private String gaSubsidyWebLinkDescription;

    @JsonProperty
    private String lastModifiedDate;

    @JsonProperty
    private String createdDate;

    @JsonProperty
    private String legalBasisText;

    @JsonProperty
    private String status;

    @JsonProperty
    private String publishedMeasureDate;

    @JsonProperty
    private String deletedBy;

    @JsonProperty
    private String deletedTimestamp;

    @JsonProperty
    private boolean hasNoEndDate;

    @JsonProperty
    private String subsidySchemeDescription;

    @JsonProperty
    private String confirmationDate;

    @JsonProperty
    private String spendingSectors;

    @JsonProperty
    private String maximumAmountUnderScheme;

    @JsonProperty
    private String subsidySchemeInterest;

    @JsonProperty
    private String reason;

    public SubsidyMeasureVersionResponse(SubsidyMeasureVersion subsidyMeasureVersion) {
        this.version = subsidyMeasureVersion.getVersion().toString();
        this.scNumber = subsidyMeasureVersion.getScNumber();
        this.subsidyMeasureTitle  = subsidyMeasureVersion.getSubsidyMeasureTitle();
        this.startDate =  SearchUtils.dateToFullMonthNameInDate(subsidyMeasureVersion.getStartDate());

        if(subsidyMeasureVersion.getEndDate() == null){
            this.endDate = "";
        } else {
            this.endDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasureVersion.getEndDate());
        }
        this.duration = subsidyMeasureVersion.getDuration();
        if(SearchUtils.isNumeric(subsidyMeasureVersion.getBudget())){
            this.budget = subsidyMeasureVersion.getBudget().contains(",") ? subsidyMeasureVersion.getBudget():
                    SearchUtils.decimalNumberFormat(new BigDecimal(subsidyMeasureVersion.getBudget().trim()));
        }else{
            log.error("Budget for scheme {} is not numeric, Budget is {}",subsidyMeasureVersion.getScNumber(),subsidyMeasureVersion.getBudget());
            this.budget = subsidyMeasureVersion.getBudget();
        }
        this.gaName = subsidyMeasureVersion.getGrantingAuthority().getGrantingAuthorityName();
        this.adhoc = "" + subsidyMeasureVersion.isAdhoc();
        this.status = subsidyMeasureVersion.getStatus();
        this.gaSubsidyWebLink = subsidyMeasureVersion.getGaSubsidyWebLink() == null ? "" : subsidyMeasureVersion.getGaSubsidyWebLink();
        this.gaSubsidyWebLinkDescription = subsidyMeasureVersion.getGaSubsidyWebLinkDescription() == null ? "" : subsidyMeasureVersion.getGaSubsidyWebLinkDescription();
        this.legalBasisText = subsidyMeasureVersion.getLegalBasisText();
        this.lastModifiedDate = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasureVersion.getLastModifiedTimestamp()).replaceAll(" 00:00:00", "");
        this.createdDate = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasureVersion.getCreatedTimestamp()).replaceAll(" 00:00:00", "");
        this.publishedMeasureDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasureVersion.getPublishedMeasureDate());
        this.hasNoEndDate =subsidyMeasureVersion.isHasNoEndDate();
        if(subsidyMeasureVersion.getDeletedBy() != null) {
            this.deletedBy = subsidyMeasureVersion.getDeletedBy();
        }
        if(subsidyMeasureVersion.getDeletedTimestamp() != null) {
            this.deletedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasureVersion.getDeletedTimestamp());
        }
        this.spendingSectors = subsidyMeasureVersion.getSpendingSectors();
        this.subsidySchemeDescription = subsidyMeasureVersion.getSubsidySchemeDescription();
        if(subsidyMeasureVersion.getConfirmationDate() == null){
            this.confirmationDate = "";
        } else {
            this.confirmationDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasureVersion.getConfirmationDate());
        }
        this.maximumAmountUnderScheme = subsidyMeasureVersion.getMaximumAmountUnderScheme();
        this.subsidySchemeInterest = subsidyMeasureVersion.getSubsidySchemeInterest() == null ? "" : subsidyMeasureVersion.getSubsidySchemeInterest();
        this.reason = subsidyMeasureVersion.getReason();
    }
}
