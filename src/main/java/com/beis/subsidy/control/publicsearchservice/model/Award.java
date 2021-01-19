package com.beis.subsidy.control.publicsearchservice.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

/**
 * 
 * Award Entity class 
 */
@Builder
@Entity(name = "AWARD")
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
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "gaId", nullable = false)
	@ToString.Exclude
	private GrantingAuthority grantingAuthority;
	
	@ManyToOne(fetch=FetchType.EAGER)
    @JoinColumn(name = "scNumber", nullable = false)
	@ToString.Exclude
	private SubsidyMeasure subsidyMeasure;
	
	@Column(name = "SUBSIDY_ELEMENT_FULL_AMOUNT_RANGE")
	private String subsidyFullAmountRange;
	
	@Column(name = "SUBSIDY_ELEMENT_FULL_AMOUNT_EXACT")
	private BigDecimal subsidyFullAmountExact;
	
	@Column(name = "SUBSIDY_OBJECTIVE")
	private String subsidyObjective;
	
	@Column(name = "GOOD_SERVICES_FILTER")
	private String goodsServicesFilter;
	
	@Column(name = "LEGAL_GRANTING_DATE")
	private LocalDate legalGrantingDate;
	
	@Column(name = "PUBLISHED_AWARD_DATE")
	private LocalDate publishedAwardDate;
	
	@Column(name = "SPENDING_REGION")
	private String spendingRegion;
	
	@Column(name = "SUBSIDY_INSTRUMENT")
	private String subsidyInstrument;
	
	@Column(name = "SPENDING_SECTOR")
	private String spendingSector;
	
	@Column(name = "CREATED_BY")
	private String createdBy;
	
	@Column(name = "APPROVED_BY")
	private String approvedBy;
	
	@Column(name = "STATUS")
	private String status;

	@Column(name = "REASON")
	private String reason;
	
	@CreationTimestamp
	@Column(name = "CREATED_TIMESTAMP")
	private LocalDate createdTimestamp;
	
	@UpdateTimestamp
	@Column(name = "LAST_MODIFIED_TIMESTAMP")
	private LocalDate lastModifiedTimestamp;
}
