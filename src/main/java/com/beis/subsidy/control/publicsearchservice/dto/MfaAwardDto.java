package com.beis.subsidy.control.publicsearchservice.dto;

import lombok.Getter;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class MfaAwardDto {
    private Long mfaAwardNumber;

    private String publicAuthority;

    private MfaGroupingDto mfaGrouping;

    private BigDecimal awardAmount;

    private LocalDate confirmationDate;

    private LocalDate publishedDate;

    private String recipientName;

    private String recipientIdType;

    private String recipientId;

    private String status;

    private LocalDateTime createdTimestamp;

    private LocalDateTime lastModifiedTimestamp;
}
