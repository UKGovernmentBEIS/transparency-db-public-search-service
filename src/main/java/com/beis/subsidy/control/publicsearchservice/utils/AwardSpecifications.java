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

                if (hasText(filter.getPa())) {
                    predicates.add(criteriaBuilder.equal(root.get("grantingAuthority").get("grantingAuthorityName"), filter.getPa().trim()));
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
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }

    private static boolean hasText(String value) {
        return value != null && !value.trim().isEmpty();
    }
}