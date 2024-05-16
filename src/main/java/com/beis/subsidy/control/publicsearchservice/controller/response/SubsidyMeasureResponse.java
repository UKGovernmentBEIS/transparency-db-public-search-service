package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubsidyMeasureResponse {

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
    private boolean adhoc;

    @JsonProperty
    private String gaSubsidyWebLink;

    @JsonProperty
    private String gaSubsidyWebLinkDescription;

    @JsonProperty
    private LegalBasisResponse legalBasis;

    @JsonProperty
    private String publishedMeasureDate;

    @JsonProperty
    private String createdBy;

    @JsonProperty
    private String approvedBy;

    @JsonProperty
    private String status;

    @JsonProperty
    private String grantingAuthorityName;

    @JsonProperty
    private String deletedBy;

    @JsonProperty
    private String deletedTimestamp;

    @JsonProperty
    private String lastModifiedTimestamp;

    @JsonProperty
    private String createdTimestamp;

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
    private List<SubsidyMeasureVersionResponse> schemeVersions;

    @JsonProperty
    private SearchResults awardSearchResults;

    public SubsidyMeasureResponse(SubsidyMeasure subsidyMeasure, boolean showAll) {

        this.scNumber = subsidyMeasure.getScNumber();
        this.subsidyMeasureTitle  = subsidyMeasure.getSubsidyMeasureTitle();
        this.adhoc = subsidyMeasure.isAdhoc();
        this.hasNoEndDate = subsidyMeasure.isHasNoEndDate();
        if (showAll) {
            this.duration = subsidyMeasure.getDuration();
            this.status = subsidyMeasure.getStatus();
            this.gaSubsidyWebLink = subsidyMeasure.getGaSubsidyWebLink() == null ? "" : subsidyMeasure.getGaSubsidyWebLink();
            this.gaSubsidyWebLinkDescription = subsidyMeasure.getGaSubsidyWebLinkDescription() == null ? "" : subsidyMeasure.getGaSubsidyWebLinkDescription();
            this.confirmationDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getConfirmationDate());
            this.startDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getStartDate());
            this.endDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getEndDate());
            BigDecimal budgetDecimal = new BigDecimal(subsidyMeasure.getBudget());
            this.budget = SearchUtils.decimalNumberFormat(budgetDecimal);
            this.publishedMeasureDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getPublishedMeasureDate());
            this.createdBy = subsidyMeasure.getCreatedBy();
            this.approvedBy = subsidyMeasure.getApprovedBy();
            this.deletedBy = subsidyMeasure.getDeletedBy();
            if(subsidyMeasure.getDeletedTimestamp() != null) {
                this.deletedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasure.getDeletedTimestamp());
            }
            this.createdTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasure.getCreatedTimestamp()).replaceAll(" 00:00:00", "");
            this.lastModifiedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasure.getLastModifiedTimestamp()).replaceAll(" 00:00:00", "");
        }
        this.legalBasis = new LegalBasisResponse(subsidyMeasure.getLegalBases());
        this.grantingAuthorityName = subsidyMeasure.getGrantingAuthority().getGrantingAuthorityName();
        this.subsidySchemeDescription = subsidyMeasure.getSubsidySchemeDescription();
        this.spendingSectors = subsidyMeasure.getSpendingSectors();
        this.maximumAmountUnderScheme = subsidyMeasure.getMaximumAmountUnderScheme();
        this.schemeVersions = SearchUtils.getSchemeVersionResponseList(subsidyMeasure);
    }
    public SubsidyMeasureResponse(SubsidyMeasure subsidyMeasure, boolean showAll, SearchResults awardSearchResults) {

        this.scNumber = subsidyMeasure.getScNumber();
        this.subsidyMeasureTitle  = subsidyMeasure.getSubsidyMeasureTitle();
        this.adhoc = subsidyMeasure.isAdhoc();
        this.hasNoEndDate = subsidyMeasure.isHasNoEndDate();
        if (showAll) {
            this.duration = subsidyMeasure.getDuration();
            this.status = subsidyMeasure.getStatus();
            this.gaSubsidyWebLink = subsidyMeasure.getGaSubsidyWebLink() == null ? "" : subsidyMeasure.getGaSubsidyWebLink();
            this.gaSubsidyWebLinkDescription = subsidyMeasure.getGaSubsidyWebLinkDescription() == null ? "" : subsidyMeasure.getGaSubsidyWebLinkDescription();
            this.confirmationDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getConfirmationDate());
            this.startDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getStartDate());
            this.endDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getEndDate());
            BigDecimal budgetDecimal = new BigDecimal(subsidyMeasure.getBudget());
            this.budget = SearchUtils.decimalNumberFormat(budgetDecimal);
            this.publishedMeasureDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getPublishedMeasureDate());
            this.createdBy = subsidyMeasure.getCreatedBy();
            this.approvedBy = subsidyMeasure.getApprovedBy();
            this.deletedBy = subsidyMeasure.getDeletedBy();
            if(subsidyMeasure.getDeletedTimestamp() != null) {
                this.deletedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasure.getDeletedTimestamp());
            }
            this.createdTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasure.getCreatedTimestamp()).replaceAll(" 00:00:00", "");
            this.lastModifiedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(subsidyMeasure.getLastModifiedTimestamp()).replaceAll(" 00:00:00", "");
        }
        this.legalBasis = new LegalBasisResponse(subsidyMeasure.getLegalBases());
        this.grantingAuthorityName = subsidyMeasure.getGrantingAuthority().getGrantingAuthorityName();
        this.subsidySchemeDescription = subsidyMeasure.getSubsidySchemeDescription();
        this.spendingSectors = subsidyMeasure.getSpendingSectors();
        this.maximumAmountUnderScheme = subsidyMeasure.getMaximumAmountUnderScheme();
        this.awardSearchResults = awardSearchResults;
        this.schemeVersions = SearchUtils.getSchemeVersionResponseList(subsidyMeasure);
    }
}