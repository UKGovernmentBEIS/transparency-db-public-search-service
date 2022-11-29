package com.beis.subsidy.control.publicsearchservice.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;

@Builder
@Entity(name = "MFA_GROUPING")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MFAGrouping {

    @Id
    @Column(name="MFA_GROUPING_NUMBER")
    private String mfaGroupingNumber;

    @ManyToOne(fetch= FetchType.EAGER)
    @JoinColumn(name = "GA_ID", nullable = false, insertable = false, updatable = false)
    @ToString.Exclude
    @JsonIgnore
    private GrantingAuthority grantingAuthority;

    @Column(name = "GA_ID")
    private Long gaId;

    @Column(name = "MFA_GROUPING_NAME")
    private String mfaGroupingName;

    @Column(name = "CREATED_BY")
    private String createdBy;

    @Column(name = "STATUS")
    private String status;

    @CreationTimestamp
    @Column(name = "CREATED_TIMESTAMP")
    private LocalDateTime createdTimestamp;

    @UpdateTimestamp
    @Column(name = "LAST_MODIFIED_TIMESTAMP")
    private LocalDateTime lastModifiedTimestamp;

    @Column(name = "DELETED_BY")
    private String deletedBy;

    @Column(name = "DELETED_TIMESTAMP")
    private LocalDateTime deletedTimestamp;
}
