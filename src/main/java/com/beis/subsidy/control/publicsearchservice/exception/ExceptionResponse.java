package com.beis.subsidy.control.publicsearchservice.exception;

import java.util.Date;

import lombok.*;

/**
 * 
 * Exception response object - used to define meaningful exception details
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ExceptionResponse {
	
	private Date timestamp;
	private String message;
	private String details;

}
