package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

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
		if (!(inputStringDate == null)) {
			log.info("input Date ::{}", inputStringDate);
			DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATE_FORMAT);
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
			return dateFormat.format(inputStringDate);
		} else {
			return null;
		}
	}

	public static String timestampToFullMonthNameInDate(Date inputDateObj) {
		log.info("input Date ::{}", inputDateObj);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
		return dateFormat.format(inputDateObj);
	}

	/**
	 * To convert Local DateTime to DD FullMONTHNAME YYYY
	 *
	 * @param inputDateTime - input string date
	 * @return
	 */
	public static String dateTimeToFullMonthNameInDate(LocalDateTime inputDateTime) {
		log.info("input Date ::{}", inputDateTime);
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy H:mm:ss");
		return dateFormat.format(inputDateTime);
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
		amountRange = (StringUtils.isNotBlank(amountRange)) ? amountRange.toUpperCase() : amountRange;
		if (StringUtils.isNotBlank(amountRange) &&
				!(amountRange.equalsIgnoreCase("NA") || amountRange.contains("N/A"))
		&& !amountRange.endsWith("OR MORE")) {

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

		} else if(StringUtils.isNotBlank(amountRange) && amountRange.endsWith("OR MORE")) {
			String removedLessThanVal = amountRange.substring(0, amountRange.length()-7).trim();
			finalAmtRange = "£"  + decimalNumberFormat(new BigDecimal(removedLessThanVal)) + " OR MORE";
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

	/**
	 * To remove role names from a list of GAs.
	 *
	 * @param gaList
	 * @return reduced gaList
	 */
	public static List<GrantingAuthority> removeRolesFromGaList(List<GrantingAuthority> gaList){
		List<String> roleNames = new ArrayList<String>(){{
			add("BEIS Administrator");
			add("Granting Authority Encoder");
			add("Granting Authority Administrator");
			add("Granting Authority Approver");
		}};

		roleNames.forEach(roleName -> {
			gaList.removeIf(ga -> Objects.equals(ga.getGrantingAuthorityName(), roleName));
		});

		return gaList;
	}

	final static String DATE_FORMAT = "yyyy-MM-dd";

	public static boolean isDateValid(String date)
	{
		try {
			DateFormat df = new SimpleDateFormat(DATE_FORMAT);
			df.setLenient(false);
			df.parse(date);
			return true;
		} catch (ParseException e) {
			return false;
		}
	}

	public static boolean isNumeric(String strNum) {
		if (strNum == null) {
			return false;
		}
		try {
			double d = Double.parseDouble(strNum);
		} catch (NumberFormatException nfe) {
			return false;
		}
		return true;
	}
}