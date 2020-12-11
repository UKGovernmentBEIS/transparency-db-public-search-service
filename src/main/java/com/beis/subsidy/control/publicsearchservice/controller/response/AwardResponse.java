package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    private String legalGrantingDate;

    public AwardResponse(Award award) {

        this.awardNumber = award.getAwardNumber();
        this.subsidyFullAmountRange = award.getSubsidyFullAmountRange();
        this.subsidyFullAmountExact = SearchUtils.decimalNumberFormat(award.getSubsidyFullAmountExact());
        this.subsidyObjective = award.getSubsidyObjective();
        this.spendingSector = award.getSpendingSector();
        this.subsidyInstrument = award.getSubsidyInstrument();
        this.legalGrantingDate = SearchUtils.dateToFullMonthNameInDate(award.getLegalGrantingDate());
        this.beneficiary = new BeneficiaryResponse(award.getBeneficiary());
        this.subsidyMeasure = new SubsidyMeasureResponse(award.getSubsidyMeasure());

    }

}
