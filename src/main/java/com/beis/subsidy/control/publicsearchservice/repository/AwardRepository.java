package com.beis.subsidy.control.publicsearchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.beis.subsidy.control.publicsearchservice.model.Award;

/**
 * Interface for Award repository to get award details from database 
 *
 */
public interface AwardRepository extends JpaRepository<Award, Long>, JpaSpecificationExecutor<Award> {


    Award findByAwardNumber(Long awardNumber);
}
