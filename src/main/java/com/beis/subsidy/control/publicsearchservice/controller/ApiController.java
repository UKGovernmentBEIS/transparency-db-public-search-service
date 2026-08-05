package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.dto.*;
import com.beis.subsidy.control.publicsearchservice.model.*;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
import com.beis.subsidy.control.publicsearchservice.repository.MFAAwardRepository;
import com.beis.subsidy.control.publicsearchservice.repository.SubsidyMeasureRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This is rest controller for Public Search service APIs
 *
 */
@RequestMapping(
        path = "/api"
)

@Slf4j
@RestController
public class ApiController {
    @Autowired
    private AwardRepository awardRepository;

    @Autowired
    private SubsidyMeasureRepository subsidyMeasureRepository;

    @Autowired
    private MFAAwardRepository mfaAwardRepository;


    @GetMapping("/awards")
    public Page<AwardDto> awards(HttpServletRequest request, Pageable pageable) {
        return awardRepository.findByStatus("Published", pageable)
                .map(this::toDto);
    }

    @GetMapping("/awards/{awardNumber}")
    public AwardDto award(HttpServletRequest request, @PathVariable Long awardNumber) {
        return awardRepository.findByAwardNumberAndStatus(awardNumber, "Published")
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Award " + awardNumber + " not found"));
    }

    @GetMapping("/schemes")
    public Page<SubsidyMeasureDto> schemes(HttpServletRequest request, Pageable pageable) {
        return subsidyMeasureRepository.findByStatus("Active", pageable)
                .map(this::toDto);
    }

    @GetMapping("/schemes/{scNumber}")
    public SubsidyMeasureDto scheme(HttpServletRequest request, @PathVariable String scNumber) {
        return subsidyMeasureRepository.findByScNumberAndStatus(scNumber, "Active")
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Scheme " + scNumber + " not found"));
    }

    @GetMapping("/mfaAwards")
    public Page<MfaAwardDto> mfaAwards(HttpServletRequest request, Pageable pageable) {
        return mfaAwardRepository.findByStatus("Published", pageable)
                .map(this::toDto);
    }

    @GetMapping("/mfaAwards/{mfaAwardNumber}")
    public MfaAwardDto mfaAward(HttpServletRequest request, @PathVariable Long mfaAwardNumber) {
        return mfaAwardRepository.findByMfaAwardNumberAndStatus(mfaAwardNumber, "Published")
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "MFA Award " + mfaAwardNumber + " not found"));
    }

    private MfaAwardDto toDto(MFAAward mfaAward){
        MfaGroupingDto mfaGroupingDto = getMfaGroupingDto(mfaAward);

        return new MfaAwardDto(
                mfaAward.getMfaAwardNumber(),
                mfaAward.getGrantingAuthority().getGrantingAuthorityName(),
                mfaGroupingDto,
                mfaAward.getAwardAmount(),
                mfaAward.getConfirmationDate(),
                mfaAward.getPublishedDate(),
                mfaAward.getRecipientName(),
                mfaAward.getRecipientIdType(),
                mfaAward.getRecipientId(),
                mfaAward.getStatus(),
                mfaAward.getCreatedTimestamp(),
                mfaAward.getLastModifiedTimestamp()
        );
    }

    private MfaGroupingDto getMfaGroupingDto(MFAAward mfaAward) {
        MFAGrouping mfaGrouping = mfaAward.getMfaGrouping();
        if (mfaGrouping == null) {
            return null;
        }

        return new MfaGroupingDto(
                mfaGrouping.getMfaGroupingNumber(),
                mfaGrouping.getMfaGroupingName(),
                mfaGrouping.getStatus(),
                mfaGrouping.getCreatedTimestamp(),
                mfaGrouping.getLastModifiedTimestamp()
        );
    }

    private SubsidyMeasureDto toDto(SubsidyMeasure scheme){
        List<AwardSummaryDto> awardDtos = Collections.emptyList();

        if (scheme.getAwards() != null) {
            awardDtos = scheme.getAwards().stream()
                    .filter(award -> "Published".equals(award.getStatus()))
                    .map(this::toDtoSummary)
                    .collect(Collectors.toList());
        }

        return new SubsidyMeasureDto(
                scheme.getScNumber(),
                scheme.getSubsidyMeasureTitle(),
                scheme.getStartDate(),
                scheme.getEndDate(),
                scheme.getDuration(),
                scheme.getBudget(),
                scheme.getGaSubsidyWebLink(),
                scheme.getGaSubsidyWebLinkDescription(),
                scheme.getPublishedMeasureDate(),
                scheme.getStatus(),
                scheme.getCreatedTimestamp(),
                scheme.getLastModifiedTimestamp(),
                scheme.getSubsidySchemeDescription(),
                scheme.getSpecificPolicyObjective(),
                scheme.getConfirmationDate(),
                scheme.getSpendingSectors(),
                scheme.getMaximumAmountUnderScheme(),
                scheme.getPurpose(),
                scheme.getSubsidySchemeInterest(),
                scheme.getLegalBases().getLegalBasisText(),
                awardDtos
        );
    }

    private AwardSummaryDto toDtoSummary(Award award){
        return new AwardSummaryDto(
                award.getAwardNumber()
        );
    }

    private AwardDto toDto(Award award) {
        Beneficiary beneficiary = award.getBeneficiary();
        // Include summary of schemes to prevent bringing in potentially thousands of duplicate award data.
        SubsidyMeasureSummaryDto subsidyMeasureSummaryDto = getSubsidyMeasureSummaryDto(award);
        return new AwardDto(
                new BeneficiaryDto(
                        beneficiary.getBeneficiaryName(),
                        beneficiary.getNationalId(),
                        beneficiary.getNationalIdType()
                ),
                subsidyMeasureSummaryDto,
                award.getAwardNumber(),
                award.getStandaloneAwardTitle(),
                award.getGrantingAuthority().getGrantingAuthorityName(),
                award.getSubsidyFullAmountRange(),
                award.getSubsidyFullAmountExact(),
                award.getSubsidyObjective(),
                award.getGoodsServicesFilter(),
                award.getLegalGrantingDate(),
                award.getPublishedAwardDate(),
                award.getSpendingRegion(),
                award.getSubsidyInstrument(),
                award.getSpendingSector(),
                award.getStatus(),
                award.getCreatedTimestamp(),
                award.getLastModifiedTimestamp(),
                award.getStandaloneAward(),
                award.getSubsidyAwardDescription(),
                award.getSpecificPolicyObjective(),
                award.getSubsidyAwardInterest(),
                award.getAuthorityURL(),
                award.getAuthorityURLDescription(),
                award.getSpei(),
                award.getLegalBasis()
        );
    }

    private SubsidyMeasureSummaryDto getSubsidyMeasureSummaryDto(Award award) {
        SubsidyMeasure scheme = award.getSubsidyMeasure();

        if (scheme == null){
            return null;
        }

        return toDtoSummary(scheme);
    }

    private SubsidyMeasureSummaryDto toDtoSummary(SubsidyMeasure scheme){
        return new SubsidyMeasureSummaryDto(
                scheme.getScNumber(),
                scheme.getSubsidyMeasureTitle()
        );
    }
}
