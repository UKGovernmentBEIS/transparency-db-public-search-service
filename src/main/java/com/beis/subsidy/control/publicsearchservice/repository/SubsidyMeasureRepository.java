package com.beis.subsidy.control.publicsearchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;

/**
 * 
 * Interface for Subsidy Measure repository to get subsidy measure details from database 
 *
 */
public interface SubsidyMeasureRepository extends JpaRepository<SubsidyMeasure, String> {

    SubsidyMeasure findByScNumber(String scNumber);
}
