package com.beis.subsidy.control.publicsearchservice.repository;

import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MFAAwardRepository extends JpaRepository<MFAAward, Long>, JpaSpecificationExecutor<MFAAward> {
    MFAAward findByMfaAwardNumber(long mfaAwardNumber);

    List<MFAAward> findByMfaGroupingNumber(String mfaGroupingNumber);
}
