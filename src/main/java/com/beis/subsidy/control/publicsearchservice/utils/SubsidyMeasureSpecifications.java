package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.controller.request.Filter;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class SubsidyMeasureSpecifications {

    public static Specification<SubsidyMeasure> withFilters(Filter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.notEqual(root.get("status"), "Deleted"));

            if (filter != null) {
                // Keyword or SC
                if (hasText(filter.getKeyword())) {
                    String keyword = "%" + filter.getKeyword().toLowerCase().trim() + "%";

                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("scNumber")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("subsidyMeasureTitle")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("gaSubsidyWebLinkDescription")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("legalBases").get("legalBasisText")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("subsidySchemeDescription")), keyword)
                    ));
                }

                // Public Authority
                if (hasText(filter.getPa())) {
                    predicates.add(criteriaBuilder.equal(root.get("grantingAuthority").get("grantingAuthorityName"), filter.getPa().trim()));
                }

                // Scheme Status
                if(hasText(filter.getSchemeStatus())) {
                    predicates.add(criteriaBuilder.equal(root.get("status"),filter.getSchemeStatus().trim()));
                }

                // Start Date
                if(filter.getSchemeStartFromDate() != null && filter.getSchemeStartToDate() != null){
                    predicates.add(criteriaBuilder.between(root.get("startDate"), filter.getSchemeStartFromDate(), filter.getSchemeStartToDate()));
                }

                // Budget
                if(filter.getSchemeBudgetFrom() != null && filter.getSchemeBudgetTo() != null) {
                    predicates.add(criteriaBuilder.between(root.get("budget").as(BigDecimal.class), filter.getSchemeBudgetFrom(), filter.getSchemeBudgetTo()));
                }

                // Sector
                if(filter.getSectors() != null && !filter.getSectors().isEmpty()){
                    List<Predicate> sectorPredicates = new ArrayList<>();

                    for (String sector : filter.getSectors()) {
                        String value = sector.trim().toLowerCase();

                        sectorPredicates.add(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("spendingSectors")),
                                        "%" + value + "%"
                                )
                        );
                    }

                    predicates.add(
                            criteriaBuilder.or(sectorPredicates.toArray(new Predicate[0]))
                    );
                }

                // Purpose
                if(filter.getSubsidyPurposes() != null && !filter.getSubsidyPurposes().isEmpty()){
                    List<Predicate> purposePredicates = new ArrayList<>();

                    for (String purpose : filter.getSubsidyPurposes()) {
                        String value = purpose.trim().toLowerCase();
                        if(value.equalsIgnoreCase("other") && filter.getSubsidyPurposeOther() != null){
                            value = value.concat(" - " + filter.getSubsidyPurposeOther().toLowerCase());
                        }

                        purposePredicates.add(
                                criteriaBuilder.like(
                                        criteriaBuilder.lower(root.get("purpose")),
                                        "%" + value + "%"
                                )
                        );
                    }

                    predicates.add(
                            criteriaBuilder.or(purposePredicates.toArray(new Predicate[0]))
                    );
                }

                // Interest
                if(filter.getSubsidyInterest() != null){
                    String value = filter.getSubsidyInterest().trim().toLowerCase();
                    predicates.add(criteriaBuilder.like(
                            criteriaBuilder.lower(root.get("subsidySchemeInterest")),
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
}