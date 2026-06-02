package com.beis.subsidy.control.publicsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor

public class AwardDto {

    private BeneficiaryDto beneficiary;

    private SubsidyMeasureDto subsidyScheme;

    private Long awardNumber;
}