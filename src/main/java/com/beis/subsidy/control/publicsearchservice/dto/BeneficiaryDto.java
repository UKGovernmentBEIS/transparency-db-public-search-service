package com.beis.subsidy.control.publicsearchservice.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class BeneficiaryDto {
    private String beneficiaryName;

    private String nationalId;

    private String nationalIdType;
}
