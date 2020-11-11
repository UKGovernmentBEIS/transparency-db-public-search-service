package com.beis.subsidy.control.publicsearchservice.model;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Builder
@Entity(name = "BENEFICIARY_READ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {

		@Id
		@Column(name="BENEFICIARY_ID")
		private Long beneficiaryId;
		
		@OneToMany(mappedBy="beneficiary")
		@ToString.Exclude
		@JsonIgnore
		private List<Award> awards;
		
		@Column(name = "BENEFICIARY_NAME")
		private String beneficiaryName;
		
		@Column(name = "BENEFICIARY_TYPE")
		private String beneficiaryType;
				
		@Column(name = "NATIONAL_ID")
		private String nationalId;
		
		@Column(name = "NATIONAL_ID_TYPE")
		private String nationalIdType;
		
		@Column(name = "SIC_CODE")
		private String sicCode;
		
		@Column(name = "SIZE_OF_ORG")
		private String orgSize;
		
		@Column(name = "POST_CODE")
		private String postCode;
		
		@Column(name = "REGION")
		private String region;
		
		@Column(name = "COUNTRY")
		private String country;
		
		@Column(name = "CREATED_BY")
		private String createdBy;
		
		@Column(name = "APPROVED_BY")
		private String approvedBy;
		
		@Column(name = "STATUS")
		private String status;
		
		@CreationTimestamp
		@Temporal(TemporalType.TIMESTAMP)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")  
		@Column(name = "CREATED_TIMESTAMP")
		private Date createdTimestamp;
		
		@UpdateTimestamp
		@Temporal(TemporalType.TIMESTAMP)
		@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")  
		@Column(name = "LAST_MODIFIED_TIMESTAMP")
		private Date lastModifiedTimestamp;
		
		
}
