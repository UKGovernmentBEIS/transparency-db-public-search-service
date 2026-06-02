package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.dto.AwardDto;
import com.beis.subsidy.control.publicsearchservice.dto.AwardSummaryDto;
import com.beis.subsidy.control.publicsearchservice.dto.BeneficiaryDto;
import com.beis.subsidy.control.publicsearchservice.dto.SubsidyMeasureDto;
import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
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
    public SubsidyMeasureDto award(HttpServletRequest request, @PathVariable String scNumber) {
        return subsidyMeasureRepository.findByScNumberAndStatus(scNumber, "Active")
                .map(this::toDto)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Scheme " + scNumber + " not found"));
    }

    private SubsidyMeasureDto toDto(SubsidyMeasure scheme){
        List<Award> awards = scheme.getAwards();
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
        SubsidyMeasureDto subsidyMeasureDto = getSubsidyMeasureDto(award);
        return new AwardDto(
                new BeneficiaryDto(
                        beneficiary.getBeneficiaryName(),
                        beneficiary.getNationalId(),
                        beneficiary.getNationalIdType()
                ),
                subsidyMeasureDto,
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

    private SubsidyMeasureDto getSubsidyMeasureDto(Award award) {
        SubsidyMeasure subsidyMeasure = award.getSubsidyMeasure();

        if (subsidyMeasure == null){
            return null;
        }

        return toDto(subsidyMeasure);
    }
}
