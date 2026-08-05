package com.beis.subsidy.control.publicsearchservice.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MfaGroupingDto {
    private String mfaGroupingNumber;

    private String mfaGroupingName;

    private String status;

    private LocalDateTime createdTimestamp;

    private LocalDateTime lastModifiedTimestamp;
}
