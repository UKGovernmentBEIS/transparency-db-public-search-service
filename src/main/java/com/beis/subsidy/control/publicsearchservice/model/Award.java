package com.beis.subsidy.control.publicsearchservice.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity(name = "AWARD_READ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Award {

	@Id
	@Column(name="AWARD_NUMBER")
	private Long awardNumber;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "beneficiaryId", nullable = false)
	@ToString.Exclude
	private Beneficiary beneficiary;
	
	//TODO - Add entity relationships with GA
	
	//TODO - Add entity relationships with SM
	
	@Column(name = "SC_NUMBER")
	private String subdidyControlNumber;
	
	@Column(name = "GA_ID")
	private Long gratingAuthorityId;
	
	@Column(name = "SUBSIDY_ELEMENT_FULL_AMOUNT_RANGE")
	private String subsidyFullAmountRange;
	
	@Column(name = "SUBSIDY_ELEMENT_FULL_AMOUNT_EXACT")
	private BigDecimal subsidyFullAmountExact;
	
	@Column(name = "SUBSIDY_OBJECTIVE")
	private String susidyObjective;
	
	@Column(name = "GOOD_SERVICES_FILTER")
	private String goodsServicesFilter;
	
	@Column(name = "LEGAL_GRANTING_DATE")
	private Date legalGrantingDate;
	
	@Column(name = "PUBLISHED_AWARD_DATE")
	private Date publishedAwardDate;
	
	@Column(name = "SPENDING_REGION")
	private String spendingRegion;
	
	@Column(name = "SUBSIDY_INSTRUMENT")
	private String subsidyInstrument;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "STATUS")
	private String status;
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIMESTAMP")
	private Date createdTimestamp;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LAST_MODIFIED_TIMESTAMP")
	private Date lastModifiedTimestamp;

		
}
