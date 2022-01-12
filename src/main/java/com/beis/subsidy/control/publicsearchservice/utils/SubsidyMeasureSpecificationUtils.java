package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.springframework.data.jpa.domain.Specification;

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
	 * @param startDateFrom - Subsidy start Date from
	 * @param startDateTo - Subsidy start Date to
	 * @return Specification<SubsidyMeasure> - Specification for Subsidy Measure
	 */
	public static Specification<SubsidyMeasure> subsidyStartDate(LocalDate startDateFrom, LocalDate startDateTo) {
		return (root, query, builder) -> builder.between(root.get("startDate"), startDateFrom, startDateTo);
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
