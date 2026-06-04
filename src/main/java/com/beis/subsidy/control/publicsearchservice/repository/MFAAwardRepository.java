package com.beis.subsidy.control.publicsearchservice.repository;

import com.beis.subsidy.control.publicsearchservice.model.MFAAward;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface MFAAwardRepository extends JpaRepository<MFAAward, Long>, JpaSpecificationExecutor<MFAAward> {
    MFAAward findByMfaAwardNumber(long mfaAwardNumber);

    List<MFAAward> findByMfaGroupingNumber(String mfaGroupingNumber);

    Page<MFAAward> findByStatus(String status, Pageable pageable);

    Optional<MFAAward> findByMfaAwardNumberAndStatus(Long mfaAwardNumber, String published);
}
