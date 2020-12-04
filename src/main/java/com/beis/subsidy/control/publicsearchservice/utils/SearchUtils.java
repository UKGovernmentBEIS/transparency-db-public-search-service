package com.beis.subsidy.control.publicsearchservice.utils;

import java.time.LocalDate;

/**
 * 
 * Search Utility class 
 */
public class SearchUtils {

	/**
	 * To check if input string is null or empty
	 * 
	 * @param inputString - input string
	 * @return boolean - true or false
	 */
	public static boolean checkNullorEmptyString(String inputString) {
		return inputString == null || inputString.trim().isEmpty();
	}
	
	/**
	 * To convert string date in format YYYY-MM-DD to LocalDate (without timezone)	
	 * @param inputStringDate - input string date
	 * @return
	 */
	public static LocalDate stringToDate(String inputStringDate) {
		return LocalDate.parse(inputStringDate);
	}
	
}
