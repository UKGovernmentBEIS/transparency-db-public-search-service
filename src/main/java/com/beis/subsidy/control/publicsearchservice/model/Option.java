package com.beis.subsidy.control.publicsearchservice.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.Date;

/**
 * 
 * OPTION Entity Class
 *
 */
@Builder
@Entity(name = "OPTION")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Option {
	
	@Id
	@Column(name="OPTION_ID")
	private Long optionId;

	@Column(name = "LEGAL_BASIS_TEXT")
	private String legalBasisText;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "STATUS")
	private String status;
	
	@CreationTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")  
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIMESTAMP")
	private Date createdTimestamp;
	
	@UpdateTimestamp
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")  
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_TIMESTAMP")
	private Date lastModifiedTimestamp;
}
