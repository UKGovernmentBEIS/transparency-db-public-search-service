package com.beis.subsidy.control.publicsearchservice.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
 * Granting Authority Entity Class 
 *
 */
@Builder
@Entity(name = "GRANTING_AUTHORITY")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantingAuthority {
	
	@Id
	@Column(name="GA_ID")
	private Long gaId;
	
	@OneToMany(mappedBy="grantingAuthority")
	@ToString.Exclude
	@JsonIgnore
	private List<Award> awards;

	@OneToMany(mappedBy="grantingAuthority")
	@ToString.Exclude
	@JsonIgnore
	private List<SubsidyMeasure> subsidyMeasure;
	
	@Column(name = "GA_NAME")
	private String grantingAuthorityName;

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
