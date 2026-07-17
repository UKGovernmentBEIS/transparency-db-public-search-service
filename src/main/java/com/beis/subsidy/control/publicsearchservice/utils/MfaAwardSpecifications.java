package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.controller.request.Filter;
import com.beis.subsidy.control.publicsearchservice.model.Award;
import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class MfaAwardSpecifications {

    public static Specification<MFAAward> withFilters(Filter filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            // Force a left join for mfa groupings, without this, awards without groupings will be omitted from the results.
            Join<Award, SubsidyMeasure> mfaGroupingJoin =
                    root.join("mfaGrouping", JoinType.LEFT);

            predicates.add(criteriaBuilder.equal(root.get("status"), "Published"));

            if (filter != null) {
                if (hasText(filter.getKeyword())) {
                    int keywordNumber;
                    try {
                        keywordNumber = Integer.parseInt(filter.getKeyword());
                    }
                    catch (NumberFormatException e) {
                        keywordNumber = 0;
                    }
                    String keyword = "%" + filter.getKeyword().toLowerCase().trim() + "%";

                    predicates.add(criteriaBuilder.or(
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("recipientName")), keyword),
                            criteriaBuilder.equal((root.get("mfaAwardNumber")), keywordNumber),
                            criteriaBuilder.like(criteriaBuilder.lower(mfaGroupingJoin.get("mfaGroupingNumber")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(mfaGroupingJoin.get("mfaGroupingName")), keyword),
                            criteriaBuilder.like(criteriaBuilder.lower(root.get("grantingAuthority").get("grantingAuthorityName")), keyword)
                    ));
                }

                if(hasText(filter.getMfaAssistance())){
                    predicates.add(criteriaBuilder.equal(root.get("isSPEI"), filter.getIsSpei()));
                }

                if (hasNumber(filter.getAwardFullAmountFrom()) && hasNumber(filter.getAwardFullAmountTo())){
                    predicates.add(criteriaBuilder.between(root.get("awardAmount"),filter.getAwardFullAmountFrom(),filter.getAwardFullAmountTo()));
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