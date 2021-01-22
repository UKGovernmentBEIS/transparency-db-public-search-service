package com.beis.subsidy.control.publicsearchservice.utils;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
/**
 * 
 * Search Utility class 
 */
@Slf4j
public class SearchUtils {

	/**
	 * To check if input string is null or empty
	 *
	 * @param inputString - input string
	 * @return boolean - true or false
	 */
	public static boolean checkNullOrEmptyString(String inputString) {
		return inputString == null || inputString.trim().isEmpty();
	}

	/**
	 * To convert string date in format YYYY-MM-DD to LocalDate (without timezone)
	 *
	 * @param inputStringDate - input string date
	 * @return
	 */
	public static LocalDate stringToDate(String inputStringDate) {
		return LocalDate.parse(inputStringDate);
	}

	/**
	 * To convert string date in format YYYY-MM-DD to DD FullMONTHNAME YYYY
	 *
	 * @param inputStringDate - input string date
	 * @return
	 */
	public static String dateToFullMonthNameInDate(LocalDate inputStringDate) {
		log.info("input Date ::{}", inputStringDate);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		return dateFormat.format(inputStringDate);
	}

	/**
	 * To convert BigDecimal to string by adding , for thousands.
	 *
	 * @param subsidyFullAmountExact
	 * @return
	 */
	public static String decimalNumberFormat(BigDecimal subsidyFullAmountExact) {
		DecimalFormat numberFormat = new DecimalFormat("###,###.##");
		return numberFormat.format(subsidyFullAmountExact.longValue());
	}

	/**
	 * To convert Amount Range to by adding pound and , for thousands.
	 *
	 * @param amountRange
	 * @return formatted string
	 */
	public static String formatedFullAmountRange(String amountRange) {
		String finalAmtRange = "NA";
		if (StringUtils.isNotBlank(amountRange) &&
				!(amountRange.equalsIgnoreCase("NA") || amountRange.contains("N/A")
						|| amountRange.contains("n/a"))
		&& !amountRange.endsWith("or more")) {

			StringBuilder format = new StringBuilder();
			String[] tokens = amountRange.split("-");
			if (tokens.length == 2) {
				finalAmtRange = format.append(convertDecimalValue(tokens[0]))
						.append(" - ")
						.append("£")
						.append(decimalNumberFormat(new BigDecimal(tokens[1].trim()))).toString();
			} else 	{
					finalAmtRange = new BigDecimal(amountRange).longValue() > 0 ? format.append("£")
							.append(decimalNumberFormat(new BigDecimal(amountRange))).toString() : "0";
			}

		} else if(StringUtils.isNotBlank(amountRange) && amountRange.endsWith("or more")) {
			String removedLessThanVal = amountRange.substring(0, amountRange.length()-7).trim();
			finalAmtRange = "£"  + decimalNumberFormat(new BigDecimal(removedLessThanVal)) + " or more";
		}
		return finalAmtRange;
	}

	public static String convertDecimalValue(String token) {
		String formatNumber = "";
		if (!token.contains("NA/na")) {
			String removedLessThanVal = token.contains(">") ? token.substring(1, token.length()).trim() : token.trim();
			formatNumber = decimalNumberFormat(new BigDecimal(removedLessThanVal));
			if (token.contains(">")) {
				formatNumber = ">£" + formatNumber;
			} else {
				formatNumber = "£" + formatNumber;
			}
		}
		return formatNumber;
	}
}
