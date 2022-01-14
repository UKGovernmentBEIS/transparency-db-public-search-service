package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.time.LocalDate;

/**
 * 
 * To define specification on Award for Public Search Award functionality
 */
public final class SubsidyMeasureSpecificationUtils {


	/**
	 * To define specification for subsidy measure title
	 * 
	 * @param scNumber - Add subsidy measure number
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> scNumber(String scNumber) {
	    return (root, query, builder) -> builder.like(root.get("scNumber"), contains(scNumber));
	}

	/**
	 * To define specification for subsidy measure title
	 *
	 * @param subsidyMeasureTitle - Add subsidy measure title
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> subsidyMeasureTitle(String subsidyMeasureTitle) {
		return (root, query, builder) -> builder.like(root.get("subsidyMeasureTitle"), contains(subsidyMeasureTitle));
	}

	/**
	 * To define specification for subsidy measure granting authority name
	 *
	 * @param subsidyMeasureGaName - Add subsidy measure granting authority name
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> subsidyMeasureGaName(String subsidyMeasureGaName) {
		return (root, query, builder) -> builder.like(root.get("grantingAuthority").get("grantingAuthorityName"), contains(subsidyMeasureGaName));
	}

	/**
	 * To define specification for start date
	 *
	 * @param startOrEnd - String to define start or end date
	 * @param dateFrom - Subsidy start Date from
	 * @param dateTo - Subsidy start Date to
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> subsidyStartEndDate(String startOrEnd, LocalDate dateFrom, LocalDate dateTo) {
		return (root, query, builder) -> builder.between(root.get(startOrEnd), dateFrom, dateTo);
	}

	/**
	 * To define specification for status
	 *
	 * @param status - Subsidy status [Active/Inactive]
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> subsidyStatus(String status) {
		return (root, query, builder) -> builder.equal(root.get("status"), status);
	}

	/**
	 * To define specification for adhoc
	 *
	 * @param adhoc - Subsidy adhoc [yes/no]
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> subsidyAdhoc(String adhoc) {
		switch(adhoc){
			case "yes":
				return (root, query, builder) -> builder.isTrue(root.get("adhoc"));
			case "no":
				return (root, query, builder) -> builder.isFalse(root.get("adhoc"));
		}
		return null;
	}

	/**
	 * To define specification for budget
	 *
	 * @param budgetFrom - Subsidy budget from value
	 * @param budgetTo - Subsidy budget to value
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> subsidyBudget(BigDecimal budgetFrom, BigDecimal budgetTo) {
		if(budgetFrom != null && budgetTo != null){
			return (root, query, builder) -> builder.and(
					builder.lessThanOrEqualTo(root.get("budget").as(BigDecimal.class), budgetTo),
					builder.greaterThanOrEqualTo(root.get("budget").as(BigDecimal.class), budgetFrom)
			);
		}else if(budgetFrom == null){
			return (root, query, builder) -> builder.lessThanOrEqualTo(root.get("budget").as(BigDecimal.class), budgetTo);
		}else if(budgetTo == null){
			return (root, query, builder) -> builder.greaterThanOrEqualTo(root.get("budget").as(BigDecimal.class), budgetFrom);
		}
		return null;
	}

	/**
	 * To check contains operations
	 * @param expression - input string
	 * @return - message format with like expression
	 */
	private static String contains(String expression) {
	    return MessageFormat.format("%{0}%", expression);
	}

}
