package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.controller.request.Filter;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class SubsidyMeasureSpecifications {

    public static Specification<SubsidyMeasure> withFilters(Filter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.notEqual(root.get("status"), "Deleted"));

            if (filter != null) {
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

                if (hasText(filter.getPa())) {
                    predicates.add(criteriaBuilder.equal(root.get("grantingAuthority").get("grantingAuthorityName"), filter.getPa().trim()));
                }

                if(hasText(filter.getSchemeStatus())) {
                    predicates.add(criteriaBuilder.equal(root.get("status"),filter.getSchemeStatus().trim()));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}