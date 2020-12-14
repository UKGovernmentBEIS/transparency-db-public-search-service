package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.math.BigInteger;
import java.util.Date;

@NoArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SubsidyMeasureResponse {

    @JsonProperty
    private String subsidyMeasureTitle;

    @JsonProperty
    private String scNumber;

    @JsonProperty
    private Date startDate;

    @JsonProperty
    private Date endDate;

    @JsonProperty
    private BigInteger duration;

    @JsonProperty
    private String budget;

    @JsonProperty
    private boolean adhoc;

    @JsonProperty
    private String gaSubsidyWebLink;

    @JsonProperty
    private String legalBasis;

    @JsonProperty
    private Date publishedMeasureDate;

    @JsonProperty
    private String createdBy;

    @JsonProperty
    private String approvedBy;

    @JsonProperty
    private String status;

    public SubsidyMeasureResponse(SubsidyMeasure subsidyMeasure, boolean showAll) {

        this.scNumber = subsidyMeasure.getScNumber();
        this.subsidyMeasureTitle  = subsidyMeasure.getSubsidyMeasureTitle();
        if (showAll) {
            this.adhoc = subsidyMeasure.isAdhoc();
            this.duration = subsidyMeasure.getDuration();
            this.legalBasis = subsidyMeasure.getLegalBasis();
            this.status = subsidyMeasure.getStatus();
            this.gaSubsidyWebLink = subsidyMeasure.getGaSubsidyWebLink();
            this.startDate = subsidyMeasure.getStartDate();
            this.endDate = subsidyMeasure.getEndDate();
            this.budget = subsidyMeasure.getBudget();
            this.publishedMeasureDate = subsidyMeasure.getPublishedMeasureDate();
            this.createdBy = subsidyMeasure.getCreatedBy();
            this.approvedBy = subsidyMeasure.getApprovedBy();
        }

    }

    /*public SubsidyMeasureResponse(SubsidyMeasure subsidyMeasure, boolean showAll) {

        this.scNumber = subsidyMeasure.getScNumber();
        this.subsidyMeasureTitle  = subsidyMeasure.getSubsidyMeasureTitle();
        this.adhoc = subsidyMeasure.isAdhoc();
        this.duration = subsidyMeasure.getDuration();
        this.legalBasis = subsidyMeasure.getLegalBasis();
        this.status = subsidyMeasure.getStatus();
        this.gaSubsidyWebLink = subsidyMeasure.getGaSubsidyWebLink();
        this.startDate = subsidyMeasure.getStartDate();
        this.endDate = subsidyMeasure.getEndDate();
        this.budget = subsidyMeasure.getBudget();
        this.publishedMeasureDate = subsidyMeasure.getPublishedMeasureDate();
        this.createdBy = subsidyMeasure.getCreatedBy();
        this.approvedBy = subsidyMeasure.getApprovedBy();
    }*/


}