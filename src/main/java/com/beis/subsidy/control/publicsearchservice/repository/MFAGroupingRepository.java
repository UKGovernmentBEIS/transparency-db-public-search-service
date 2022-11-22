package com.beis.subsidy.control.publicsearchservice.repository;


import com.beis.subsidy.control.publicsearchservice.model.MFAGrouping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface MFAGroupingRepository extends JpaRepository<MFAGrouping, String>, JpaSpecificationExecutor<MFAGrouping> {
    MFAGrouping findByMfaGroupingNumber(String mfaGroupingNumber);

    List<MFAGrouping> findByMfaGroupingName(String mfaGroupingName);
}
