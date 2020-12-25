package com.beis.subsidy.control.publicsearchservice.utils;

import com.beis.subsidy.control.publicsearchservice.model.Award;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
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
	 * @param inputStringDate - input string date
	 * @return
	 */
	public static LocalDate stringToDate(String inputStringDate) {
		return LocalDate.parse(inputStringDate);
	}

	/**
	 * To convert string date in format YYYY-MM-DD to DD FullMONTHNAME YYYY
	 * @param inputStringDate - input string date
	 * @return
	 */
	public static  String dateToFullMonthNameInDate(LocalDate inputStringDate) {
		log.info("input Date ::{}", inputStringDate);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy");
		return dateFormat.format(inputStringDate);
	}

    /**
     * To convert BigDecimal to string by adding , for thousands.
     * @param subsidyFullAmountExact
     * @return
     */
    public static String decimalNumberFormat(BigDecimal subsidyFullAmountExact) {
        DecimalFormat numberFormat = new DecimalFormat("###,###.##");
        return  numberFormat.format(subsidyFullAmountExact.longValue());
	}

	/**
	 * To convert Amount Range to by adding pound and , for thousands.
	 * @param amountRange
	 * @return formatted string
	 */
	public static String formatedFullAmountRange(String amountRange) {
		StringBuilder format = new StringBuilder();
		String [] tokens = amountRange.split("-");
		String finalAmtRange = null;
		if (tokens.length == 2) {
			finalAmtRange = format.append(convertDecimalValue(tokens[0]))
					.append(" - ")
					.append("£")
					.append(decimalNumberFormat(new BigDecimal(tokens[1].trim()))).toString();
		} else {
			finalAmtRange = format.append("£").append(decimalNumberFormat(new BigDecimal(amountRange))).toString();
		}
		return  finalAmtRange;
	}

	public static String convertDecimalValue(String token){
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
	 * Preparing the public search service
	 * @param awards
	 * @return
	 * @throws IOException
	 */
	public static XSSFWorkbook prepareAwardDetailsSheet(List<Award> awards) throws IOException  {
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet spreadsheet = workbook.createSheet("Public User Details");
		XSSFRow row = spreadsheet.createRow(0);
		XSSFCell cell;
		cell = row.createCell(0);
		createHeaderForPublicSearchExcel(cell,row);
		int i = 1;
		for (Award award : awards) {
			row = spreadsheet.createRow(i);
			cell = row.createCell(0);
			cell.setCellValue(award.getSubsidyMeasure().getScNumber());
			cell = row.createCell(1);
			cell.setCellValue(award.getSubsidyMeasure().getSubsidyMeasureTitle());
			cell = row.createCell(2);
			cell.setCellValue(award.getAwardNumber());
			cell = row.createCell(3);

			if (StringUtils.isNotBlank(award.getSubsidyObjective())
				&& !award.getSubsidyObjective().contains("Other"))
			{
				cell.setCellValue(award.getSubsidyObjective());
			} else if(StringUtils.isNotBlank(award.getSubsidyObjective()) &&
					award.getSubsidyObjective().contains("Other")){
				cell = row.createCell(4);
				cell.setCellValue(award.getSubsidyObjective());
			}
			cell = row.createCell(5);
			if (StringUtils.isNotBlank(award.getSpendingSector())
					&& !award.getSpendingSector().contains("Other"))
			{
				cell.setCellValue(award.getSpendingSector());
			} else if(StringUtils.isNotBlank(award.getSpendingSector()) &&
					award.getSpendingSector().contains("Other")){
				cell = row.createCell(6);
				cell.setCellValue(award.getSubsidyInstrument());
			}
			cell = row.createCell(7);
			if (StringUtils.isNotBlank(award.getSubsidyFullAmountRange()) &&
				!award.getSubsidyFullAmountRange().contains("NA")) {
				cell.setCellValue(SearchUtils.formatedFullAmountRange(award.getSubsidyFullAmountRange()));
			} else {
				cell.setCellValue(award.getSubsidyFullAmountRange());
			}
			cell = row.createCell(8);
			if (award.getSubsidyFullAmountExact().doubleValue() > 0.0){
				cell.setCellValue(SearchUtils.formatedFullAmountRange(award.getSubsidyFullAmountExact().toString()));
			}
			cell = row.createCell(9);
			cell.setCellValue(award.getBeneficiary().getNationalIdType());
			cell = row.createCell(10);
			cell.setCellValue(award.getBeneficiary().getNationalId());
			cell = row.createCell(11);
			cell.setCellValue(award.getBeneficiary().getBeneficiaryName());
			cell = row.createCell(12);
			cell.setCellValue(award.getBeneficiary().getOrgSize());
			cell = row.createCell(13);
			cell.setCellValue(award.getGrantingAuthority().getGrantingAuthorityName());
			cell = row.createCell(14);
			cell.setCellValue(award.getLegalGrantingDate().toString());
			cell = row.createCell(15);
			cell.setCellValue(award.getGoodsServicesFilter());
			cell = row.createCell(16);
			cell.setCellValue(award.getSpendingRegion());
			cell = row.createCell(17);
			cell.setCellValue(award.getSpendingSector());
			cell = row.createCell(18);
			cell.setCellValue(award.getPublishedAwardDate().toString());
			i++;
		}
		//Write the workbook in file system
		FileOutputStream out = new FileOutputStream(new File("publicsearchawards.xlsx"));
		workbook.write(out);
		out.close();
		log.info("publicsearchawards.xlsx written successfully");
		return workbook;
	}

	/**
	 * Creating the header's of the excel file
	 * @param cell
	 * @param row
	 */
	public  static void createHeaderForPublicSearchExcel(XSSFCell cell, XSSFRow row) {
		cell.setCellValue("Subsidy Control (SC) Number");
		cell = row.createCell(1);
		cell.setCellValue("Subsidy Measure Title");
		cell = row.createCell(2);
		cell.setCellValue("Subsidy Award Number");
		cell = row.createCell(3);
		cell.setCellValue("Subsidy Objective");
		cell = row.createCell(4);
		cell.setCellValue("Subsidy Objective - Other");
		cell = row.createCell(5);
		cell.setCellValue("Subsidy Instrument");
		cell = row.createCell(6);
		cell.setCellValue("Subsidy Instrument - Other");
		cell = row.createCell(7);
		cell.setCellValue("Subsidy Element Full Amount Range");
		cell = row.createCell(8);
		cell.setCellValue("Subsidy Element Full Amount");
		cell = row.createCell(9);
		cell.setCellValue("National ID Type");
		cell = row.createCell(10);
		cell.setCellValue("National ID");
		cell = row.createCell(11);
		cell.setCellValue("Beneficiary Name");
		cell = row.createCell(12);
		cell.setCellValue("Size of Organisation");
		cell = row.createCell(13);
		cell.setCellValue("Granting Authority Name");
		cell = row.createCell(14);
		cell.setCellValue("Legal Granting Date");
		cell = row.createCell(15);
		cell.setCellValue("Goods/Services Filter");
		cell = row.createCell(16);
		cell.setCellValue("Spending Region");
		cell = row.createCell(17);
		cell.setCellValue("Spending Sector");
		cell = row.createCell(18);
		cell.setCellValue("Published Date");
	}

}