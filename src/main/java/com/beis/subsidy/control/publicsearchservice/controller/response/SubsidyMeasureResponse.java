package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDate;

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
    private LegalBasisResponse legalBasis;

    @JsonProperty
    private LocalDate publishedMeasureDate;

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
            this.status = subsidyMeasure.getStatus();
            this.gaSubsidyWebLink = subsidyMeasure.getGaSubsidyWebLink();
            this.startDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getStartDate());
            this.endDate = SearchUtils.dateToFullMonthNameInDate(subsidyMeasure.getEndDate());
            this.budget = subsidyMeasure.getBudget();
            this.publishedMeasureDate = subsidyMeasure.getPublishedMeasureDate();
            this.createdBy = subsidyMeasure.getCreatedBy();
            this.approvedBy = subsidyMeasure.getApprovedBy();
        }
        this.legalBasis = new LegalBasisResponse(subsidyMeasure.getLegalBases());

    }
}
