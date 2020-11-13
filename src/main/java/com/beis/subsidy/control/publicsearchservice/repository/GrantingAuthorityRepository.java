package com.beis.subsidy.control.publicsearchservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;

/**
 * 
 *  * Interface for Granting Authority repository to get granting authority details from database 
 */
public interface GrantingAuthorityRepository extends JpaRepository<GrantingAuthority, Long> {
	
	/**
	 * To get Granting authority details based on granting authority name 
	 * @param Name - Granting authority name
	 * @return GrantingAuthority - Object of GrantingAuthority
	 */
	GrantingAuthority findByGrantingAuthorityName(String Name);
	

}
