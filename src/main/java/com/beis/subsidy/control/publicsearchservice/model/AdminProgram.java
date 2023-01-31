package com.beis.subsidy.control.publicsearchservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 
 * Admin Program entity class
 *
 */
@Entity(name = "ADMIN_PROGRAM")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AdminProgram {

	@Id
	@Column(name="AP_NUMBER")
	private String apNumber;

	@ManyToOne(fetch=FetchType.EAGER , cascade = CascadeType.ALL)
	@JoinColumn(name = "gaId", nullable = false)
	private GrantingAuthority grantingAuthority;

	@Column(name = "ADMIN_PROGRAM_NAME")
	private String adminProgramName;

	@ManyToOne(fetch=FetchType.EAGER)
	@JoinColumn(name = "scNumber", nullable = false)
	private SubsidyMeasure subsidyMeasure;

	@Column(name = "BUDGET")
	private BigDecimal budget;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "CREATED_BY")
	private String createdBy;

	@CreationTimestamp
	@Column(name = "CREATED_TIMESTAMP")
	private LocalDateTime createdTimestamp;

	@UpdateTimestamp
	@Column(name = "LAST_MODIFIED_TIMESTAMP")
	private LocalDateTime lastModifiedTimestamp;

	@Column(name = "DELETED_BY")
	private String deletedBy;

	@Column(name = "DELETED_TIMESTAMP", columnDefinition = "TIMESTAMP")
	private LocalDateTime deletedTimestamp;
}
