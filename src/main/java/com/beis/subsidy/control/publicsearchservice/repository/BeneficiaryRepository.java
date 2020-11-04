package com.beis.subsidy.control.publicsearchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beis.subsidy.control.publicsearchservice.model.Beneficiary;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, Long> {

}
