package com.beis.subsidy.control.publicsearchservice.controller;

import com.beis.subsidy.control.publicsearchservice.dto.AwardDto;
import com.beis.subsidy.control.publicsearchservice.dto.BeneficiaryDto;
import com.beis.subsidy.control.publicsearchservice.dto.SubsidyMeasureDto;
import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import com.beis.subsidy.control.publicsearchservice.repository.AwardRepository;
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

    private static SubsidyMeasureDto getSubsidyMeasureDto(Award award) {
        SubsidyMeasure subsidyMeasure = award.getSubsidyMeasure();
        SubsidyMeasureDto subsidyMeasureDto = null;

        if (subsidyMeasure != null){
            subsidyMeasureDto = new SubsidyMeasureDto(
                    subsidyMeasure.getScNumber(),
                    subsidyMeasure.getSubsidyMeasureTitle(),
                    subsidyMeasure.getStartDate(),
                    subsidyMeasure.getEndDate(),
                    subsidyMeasure.getDuration(),
                    subsidyMeasure.getBudget(),
                    subsidyMeasure.getGaSubsidyWebLink(),
                    subsidyMeasure.getGaSubsidyWebLinkDescription(),
                    subsidyMeasure.getPublishedMeasureDate(),
                    subsidyMeasure.getStatus(),
                    subsidyMeasure.getCreatedTimestamp(),
                    subsidyMeasure.getLastModifiedTimestamp(),
                    subsidyMeasure.getSubsidySchemeDescription(),
                    subsidyMeasure.getSpecificPolicyObjective(),
                    subsidyMeasure.getConfirmationDate(),
                    subsidyMeasure.getSpendingSectors(),
                    subsidyMeasure.getMaximumAmountUnderScheme(),
                    subsidyMeasure.getPurpose(),
                    subsidyMeasure.getSubsidySchemeInterest(),
                    subsidyMeasure.getLegalBases().getLegalBasisText()
            );
        }
        return subsidyMeasureDto;
    }
}
