package com.beis.subsidy.control.publicsearchservice.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.beis.subsidy.control.publicsearchservice.model.SubsidyMeasure;

import java.util.Optional;

/**
 * Interface for Award repository to get award details from database
 *
 */
public interface SubsidyMeasureRepository extends JpaRepository<SubsidyMeasure, Long>, JpaSpecificationExecutor<SubsidyMeasure> {

    SubsidyMeasure findByScNumber(String scNumber);

    Page<SubsidyMeasure> findByStatus(String status, Pageable pageable);

    Optional<SubsidyMeasure> findByScNumberAndStatus(String scNumber, String status);
}
