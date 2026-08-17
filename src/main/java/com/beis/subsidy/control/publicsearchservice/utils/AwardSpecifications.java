package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.controller.request.Filter;
import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class AwardSpecifications {

    public static Specification<Award> withFilters(Filter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Force a left join for schemes, without this, standalone awards will be omitted from the results.
            Join<Award, SubsidyMeasure> subsidyMeasureJoin =
                    root.join("subsidyMeasure", JoinType.LEFT);

            predicates.add(criteriaBuilder.equal(root.get("status"), "Published"));

            if (filter != null) {
                if (hasText(filter.getKeyword())) {
                    String keyword = "%" + filter.getKeyword().toLowerCase().trim() + "%";

                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("beneficiary").get("beneficiaryName")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("grantingAuthority").get("grantingAuthorityName")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(subsidyMeasureJoin.get("scNumber")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(subsidyMeasureJoin.get("subsidyMeasureTitle")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("subsidyAwardDescription")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("legalBasis")), keyword)
                    ));
                }
                if (filter.getAwardType() != null
                        && !filter.getAwardType().isEmpty()) {

                    String awardType = filter.getAwardType().toLowerCase();

                    boolean standaloneSelected = awardType.contains("standalone award");
                    boolean schemeSelected = awardType.contains("award under a scheme");

                    if (standaloneSelected && schemeSelected) {
                        // Don't add a predicate.
                        // Both yes and no are valid, so return both.
                    } else if (standaloneSelected) {
                        predicates.add(
                                criteriaBuilder.equal(
                                        criteriaBuilder.lower(root.get("standaloneAward")),
                                        "yes"
                                )
                        );
                    } else if (schemeSelected) {
                        predicates.add(
                                criteriaBuilder.equal(
                                        criteriaBuilder.lower(root.get("standaloneAward")),
                                        "no"
                                )
                        );
                    }
                }
                if (hasText(filter.getPa())) {
                    predicates.add(criteriaBuilder.equal(root.get("grantingAuthority").get("grantingAuthorityName"), filter.getPa().trim()));
                }

                if (hasNumber(filter.getAwardFullAmountFrom()) && hasNumber(filter.getAwardFullAmountTo())){
                    predicates.add(criteriaBuilder.between(root.get("subsidyFullAmountExact"),filter.getAwardFullAmountFrom(),filter.getAwardFullAmountTo()));
                }

                if(filter.getConfirmationDateFrom() != null && filter.getConfirmationDateTo() != null){
                    predicates.add(criteriaBuilder.between(root.get("legalGrantingDate"),filter.getConfirmationDateFrom(),filter.getConfirmationDateTo()));
                }

                if (filter.getGeoLocations() != null && !filter.getGeoLocations().isEmpty()) {
                    List<Predicate> regionPredicates = new ArrayList<>();

                    for (String geoLocation : filter.getGeoLocations()) {
                        String value = geoLocation.trim().toLowerCase();

                        regionPredicates.add(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("spendingRegion")),
                                        "%\"" + value + "\"%"
                                )
                        );
                    }

                    predicates.add(
                            criteriaBuilder.or(regionPredicates.toArray(new Predicate[0]))
                    );
                }

                if (filter.getSectors() != null && !filter.getSectors().isEmpty()) {
                    Predicate[] sectorPredicates = filter.getSectors().stream()
                            .map(String::trim)
                            .filter(value -> !value.isEmpty())
                            .map(value ->
                                    criteriaBuilder.like(
                                            criteriaBuilder.lower(root.get("spendingSector")),
                                            "%" + value.toLowerCase() + "%"
                                    )
                            )
                            .toArray(Predicate[]::new);

                    if (sectorPredicates.length > 0) {
                        predicates.add(criteriaBuilder.or(sectorPredicates));
                    }
                }

                if(filter.getSubsidyForms() != null && !filter.getSubsidyForms().isEmpty()){
                    List<Predicate> formPredicates = new ArrayList<>();

                    for (String form : filter.getSubsidyForms()) {
                        String value = form.trim().toLowerCase();
                        if(value.equalsIgnoreCase("other") && filter.getSubsidyFormOther() != null){
                            value = value.concat(" - " + filter.getSubsidyFormOther().toLowerCase());
                        }

                        formPredicates.add(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("subsidyInstrument")),
                                        "%" + value + "%"
                                )
                        );
                    }

                    predicates.add(
                            criteriaBuilder.or(formPredicates.toArray(new Predicate[0]))
                    );
                }
                if(filter.getSubsidyPurposes() != null && !filter.getSubsidyPurposes().isEmpty()){
                    List<Predicate> purposePredicates = new ArrayList<>();

                    for (String purpose : filter.getSubsidyPurposes()) {
                        String value = purpose.trim().toLowerCase();
                        if(value.equalsIgnoreCase("other") && filter.getSubsidyPurposeOther() != null){
                            value = value.concat(" - " + filter.getSubsidyPurposeOther().toLowerCase());
                        }

                        purposePredicates.add(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("subsidyObjective")),
                                        "%\"" + value + "\"%"
                                )
                        );
                    }

                    predicates.add(
                            criteriaBuilder.or(purposePredicates.toArray(new Predicate[0]))
                    );
                }
                if(filter.getSubsidyInterest() != null){
                    String value = filter.getSubsidyInterest().trim().toLowerCase();
                    predicates.add(criteriaBuilder.like(
                                    criteriaBuilder.lower(root.get("subsidyAwardInterest")),
                                    "%" + value + "%"
                            )
                    );
                }


            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }

    private static boolean hasNumber(Integer value) { return value != null && !value.toString().trim().isEmpty(); }
}