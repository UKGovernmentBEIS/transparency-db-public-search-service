package com.beis.subsidy.control.publicsearchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;

/**
 * 
 * Interface for Beneficiary repository to get beneficiary details from database 
 *
 */
public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

}
