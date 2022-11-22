package com.beis.subsidy.control.publicsearchservice.controller.response;

import com.beis.subsidy.control.publicsearchservice.model.MFAGrouping;
import com.beis.subsidy.control.publicsearchservice.utils.SearchUtils;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MFAGroupingResponse {
    @JsonProperty
    private String mfaGroupingName;

    @JsonProperty
    private String mfaGroupingNumber;

    @JsonProperty
    private String createdBy;

    @JsonProperty
    private String status;

    @JsonProperty
    private String grantingAuthorityName;

    @JsonProperty
    private String lastModifiedTimestamp;

    @JsonProperty
    private String createdTimestamp;

    @JsonProperty
    private String deletedTimestamp;

    @JsonProperty
    private String deletedBy;

    @JsonProperty
    private boolean canEdit;

    @JsonProperty
    private boolean canDelete;

    public MFAGroupingResponse(MFAGrouping mfaGrouping){
        this.mfaGroupingNumber = mfaGrouping.getMfaGroupingNumber();
        this.mfaGroupingName = mfaGrouping.getMfaGroupingName();
        this.grantingAuthorityName = mfaGrouping.getGrantingAuthority().getGrantingAuthorityName();
        this.createdBy = mfaGrouping.getCreatedBy();
        this.status = mfaGrouping.getStatus();
        this.lastModifiedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(mfaGrouping.getLastModifiedTimestamp());
        this.createdTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(mfaGrouping.getCreatedTimestamp());
        if(mfaGrouping.getDeletedBy() != null) {
            this.deletedBy = mfaGrouping.getDeletedBy();
        }
        if(mfaGrouping.getDeletedTimestamp() != null) {
            this.deletedTimestamp = SearchUtils.dateTimeToFullMonthNameInDate(mfaGrouping.getDeletedTimestamp());
        }
        this.canEdit = false;
        this.canDelete = false;
    }
}
