package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.controller.request.Filter;
import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class MfaAwardSpecifications {

    public static Specification<MFAAward> withFilters(Filter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            predicates.add(criteriaBuilder.equal(root.get("status"), "Published"));

            if (filter != null) {
                if (hasText(filter.getKeyword())) {
                    String keyword = "%" + filter.getKeyword().toLowerCase().trim() + "%";

                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("recipientName")), keyword)
                    ));
                }
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}