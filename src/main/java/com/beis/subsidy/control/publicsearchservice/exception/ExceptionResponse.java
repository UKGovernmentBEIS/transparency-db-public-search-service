package com.beis.subsidy.control.publicsearchservice.exception;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 * Exception response object - used to define meaniningful exception details
 *
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExceptionResponse {
	
	private Date timestamp;
	private String message;
	private String details;

}
