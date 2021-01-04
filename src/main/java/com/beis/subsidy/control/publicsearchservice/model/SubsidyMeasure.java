package com.beis.subsidy.control.publicsearchservice.model;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 
 * Subsidy Measure entity class
 *
 */
@Builder
@Entity(name = "SUBSIDY_MEASURE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubsidyMeasure {

	@Id
	@Column(name="SC_NUMBER")
	private String scNumber;
	
	@OneToMany(mappedBy="subsidyMeasure")
	@ToString.Exclude
	@JsonIgnore
	private List<Award> awards;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "gaId", nullable = false)
	@JsonIgnore
	private GrantingAuthority grantingAuthority;

	@OneToOne(mappedBy="subsidyMeasure")
	private LegalBasis legalBases;
	
	@Column(name = "SUBSIDY_MEASURE_TITLE")
	private String subsidyMeasureTitle;
	
	@Column(name = "START_DATE")
	private LocalDate startDate;
	
	@Column(name = "END_DATE")
	private LocalDate endDate;
	
	@Column(name = "DURATION")
	private BigInteger duration;
	
	@Column(name = "BUDGET")
	private String budget;
	
	@Column(name = "ADHOC")
	private boolean adhoc;
			
	@Column(name = "GA_SUBSIDY_WEBLINK")
	private String gaSubsidyWebLink;

	@Column(name = "PUBLISHED_MEASURE_DATE")
	private LocalDate publishedMeasureDate;

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
