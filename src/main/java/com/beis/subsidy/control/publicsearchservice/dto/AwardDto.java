package com.beis.subsidy.control.publicsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Map;

@Getter
@AllArgsConstructor

public class AwardDto {

    private BeneficiaryDto beneficiary;

    private SubsidyMeasureDto subsidyScheme;

    private Long awardNumber;

    private Map<String, LinkDto> _links;
}