package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;
import org.springframework.data.jpa.domain.Specification;

import java.text.MessageFormat;

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
	 * To check contains operations
	 * @param expression - input string
	 * @return - message format with like expression
	 */
	private static String contains(String expression) {
	    return MessageFormat.format("%{0}%", expression);
	}

}
