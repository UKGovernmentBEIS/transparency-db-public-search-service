package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;

public final class MFAAwardSpecificationUtils {
    public static Specification<MFAAward> mfaGroupingNameLike(String mfaGroupingName) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("mfaGrouping").get("mfaGroupingName")),
                builder.lower(builder.literal("%" + mfaGroupingName.trim() + "%")));
    }

    public static Specification<MFAAward> mfaRecipientLike(String recipientName) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("recipientName")),
                builder.lower(builder.literal("%" + recipientName.trim() + "%")));
    }

    public static Specification<MFAAward> mfaAwardNumberEquals(String mfaAwardNumber) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("mfaAwardNumber")),
                builder.lower(builder.literal(mfaAwardNumber.trim())));
    }

    public static Specification<MFAAward> mfaGroupingByStatus(String status) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("status")),
                builder.lower(builder.literal(status.trim())));
    }

    public static Specification<MFAAward> grantingAuthorityNameEqual(String searchName) {
        return (root, query, builder) -> builder.equal(builder.lower(root.get("grantingAuthority").get("grantingAuthorityName")),
                builder.lower(builder.literal(searchName.trim())));
    }

    public static Specification<MFAAward> mfaGroupingNumberEquals(String mfaGroupingNumber) {
        return (root, query, builder) -> builder.like(builder.lower(root.get("mfaGrouping").get("mfaGroupingNumber")),
                builder.lower(builder.literal("%" + mfaGroupingNumber.trim() + "%")));
    }

    public static Specification<MFAAward> mfaConfirmationDateBetween(LocalDate dateFrom, LocalDate dateTo) {
        return (root, query, builder) -> builder.between(root.get("confirmationDate"), dateFrom, dateTo);
    }

    public static Specification<MFAAward> mfaAwardStatusLike(String status) {
        return (root, query, builder) -> builder.like(root.get("status"), status);
    }

    public static Specification<MFAAward> awardAmountBetween(BigDecimal amountFrom, BigDecimal amountTo) {
        if(amountFrom != null && amountTo != null){
            return (root, query, builder) -> builder.and(
                    builder.lessThanOrEqualTo(root.get("awardAmount").as(BigDecimal.class), amountTo),
                    builder.greaterThanOrEqualTo(root.get("awardAmount").as(BigDecimal.class), amountFrom)
            );
        }else if(amountFrom == null){
            return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("awardAmount").as(BigDecimal.class), amountTo);
        }else if(amountTo == null){
            return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("awardAmount").as(BigDecimal.class), amountFrom);
        }
        return null;
    }

    public static Specification<MFAAward> isSpei(Boolean isSpei) {
        if(isSpei){
            return (root, query, builder) -> builder.isTrue(root.get("isSPEI"));
        } else {
            return (root, query, builder) -> builder.isFalse(root.get("isSPEI"));
        }
    }
}
