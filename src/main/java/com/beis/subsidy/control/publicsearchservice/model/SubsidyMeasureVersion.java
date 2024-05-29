package com.beis.subsidy.control.publicsearchservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * 
 * Subsidy Measure Version entity class
 *
 */
@Entity(name = "SUBSIDY_MEASURE_VERSION")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class SubsidyMeasureVersion {

	@Id
	@Column(name="VERSION")
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
	private UUID version;

	@Column(name="SC_NUMBER")
	private String scNumber;

	@ManyToOne(fetch=FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "ga_id", nullable = false, insertable = false, updatable = false)
	private GrantingAuthority grantingAuthority;

	@Column(name = "GA_ID")
	private Long gaId;

	@Column(name = "LEGAL_BASIS_TEXT")
	private String legalBasisText;

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

	@Column(name = "GA_SUBSIDY_WEBLINK_DESCRIPTION")
	private String gaSubsidyWebLinkDescription;

	@Column(name = "CREATED_TIMESTAMP")
	private LocalDateTime createdTimestamp;

	@Column(name = "LAST_MODIFIED_TIMESTAMP")
	private LocalDateTime lastModifiedTimestamp;

	@Column(name = "DELETED_BY")
	private String deletedBy;

	@Column(name = "DELETED_TIMESTAMP", columnDefinition = "TIMESTAMP")
	private LocalDateTime deletedTimestamp;

	@Column(name = "HAS_NO_END_DATE")
	private boolean hasNoEndDate;

	@Column(name = "SUBSIDY_SCHEME_DESCRIPTION")
	private String subsidySchemeDescription;

	@Column(name = "CONFIRMATION_DATE")
	private LocalDate confirmationDate;

	@Column(name = "SPENDING_SECTORS")
	private String spendingSectors;

	@Column(name = "MAXIMUM_AMOUNT_UNDER_SCHEME")
	private String maximumAmountUnderScheme;

	@Column(name = "SUBSIDY_SCHEME_INTEREST")
	private String subsidySchemeInterest;
}
