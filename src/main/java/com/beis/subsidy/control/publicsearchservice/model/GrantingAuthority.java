package com.beis.subsidy.control.publicsearchservice.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Entity(name = "GRANTING_AUTHORITY_READ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GrantingAuthority {
	
	@Id
	@Column(name="GA_ID")
	private Long grantingAuthorityId;
	
	//TODO - Add entity relationships with SM
	
	@Column(name = "GA_NAME")
	private String grantingAuthorityName;
	
	@Column(name = "LEGAL_BASIS")
	private String legalBasis;
			
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
