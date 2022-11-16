package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import com.beis.subsidy.control.publicsearchservice.model.MFAGrouping;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MFAAwardResponse {

    @JsonProperty
    private long mfaAwardNumber;

    @JsonProperty
    private String isSpeiAssistance;

    @JsonProperty
    private boolean hasMfaGrouping;

    @JsonProperty
    private String mfaGroupingNumber;

    @JsonProperty
    private MFAGroupingResponse mfaGroupingResponse;

    @JsonProperty
    private String awardAmount;

    @JsonProperty
    private String awardAmountFormatted;

    @JsonProperty
    private String confirmationDate;

    @JsonProperty
    private String publishedDate;

    @JsonProperty
    private String grantingAuthorityName;

    @JsonProperty
    private String recipientName;

    @JsonProperty
    private String recipientIdType;

    @JsonProperty
    private String recipientIdNumber;

    @JsonProperty
    private String status;

    @JsonProperty
    private String createdBy;

    @JsonProperty
    private String approvedBy;

    @JsonProperty
    private String reason;

    @JsonProperty
    private String lastModifiedTimestamp;

    @JsonProperty
    private String createdTimestamp;

    @JsonProperty
    private String deletedBy;

    @JsonProperty
    private String deletedTimestamp;

    @JsonProperty
    private Boolean canApprove;

    public MFAAwardResponse(MFAAward mfaAward){
        this.mfaAwardNumber = mfaAward.getMfaAwardNumber();
        this.isSpeiAssistance = mfaAward.isSPEI() ? "Yes" : "No";
        this.hasMfaGrouping = mfaAward.isMfaGroupingPresent();
        if (mfaAward.isMfaGroupingPresent()){
            this.mfaGroupingNumber = mfaAward.getMfaGroupingNumber();
            this.mfaGroupingResponse = new MFAGroupingResponse(mfaAward.getMfaGrouping());
        }else{
            this.mfaGroupingNumber = "NA";
        }
        this.awardAmount = mfaAward.getAwardAmount().toString();
        this.awardAmountFormatted = SearchUtils.decimalNumberFormat(mfaAward.getAwardAmount());
        this.confirmationDate = SearchUtils.dateToFullMonthNameInDate(mfaAward.getConfirmationDate());
        this.grantingAuthorityName = mfaAward.getGrantingAuthority().getGrantingAuthorityName();
        this.recipientName = mfaAward.getRecipientName();
        this.recipientIdType = mfaAward.getRecipientIdType();
        this.recipientIdNumber = mfaAward.getRecipientId();
        this.status = mfaAward.getStatus();
        this.createdBy = mfaAward.getCreatedBy();
        this.approvedBy = mfaAward.getApprovedBy();
        this.reason = mfaAward.getReason();
        this.lastModifiedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(mfaAward.getLastModifiedTimestamp());
        this.createdTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(mfaAward.getCreatedTimestamp());
        this.deletedBy = mfaAward.getDeletedBy();
        if(mfaAward.getDeletedTimestamp() != null) {
            this.deletedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(mfaAward.getDeletedTimestamp());
        }
        if ("Awaiting Approval".equals(this.status)) {
            this.publishedDate = "Awaiting Approval";
        } else {
            this.publishedDate = SearchUtils.dateToFullMonthNameInDate(mfaAward.getPublishedDate());
        }
        this.canApprove = false;
    }
}
