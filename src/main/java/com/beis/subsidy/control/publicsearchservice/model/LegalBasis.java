package com.beis.subsidy.control.publicsearchservice.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;

import java.util.Date;
/**
 * 
 * LegalBasis Entity Class
 *
 */
@Builder
@Entity(name = "LEGAL_BASIS")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LegalBasis {
	
	@Id
	@Column(name="LEGAL_BASIS_ID")
	private Long legalBasisId;

	@OneToOne
	@JoinColumn(name = "scNumber", nullable = false)
	private SubsidyMeasure subsidyMeasure;

	@Column(name="LEGAL_BASIS_TEXT")
	private String legalBasisText;

	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "STATUS")
	private String status;
	
	@CreationTimestamp
	@Column(name = "CREATED_TIMESTAMP")
	private Date createdTimestamp;
	
	@UpdateTimestamp
	@Column(name = "LAST_MODIFIED_TIMESTAMP")
	private Date lastModifiedTimestamp;
}
