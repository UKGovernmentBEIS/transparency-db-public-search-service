package com.beis.subsidy.control.publicsearchservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.beis.subsidy.control.publicsearchservice.model.GrantingAuthority;

public interface GrantingAuthorityRepository extends JpaRepository<GrantingAuthority, Long> {
	
	GrantingAuthority findByGrantingAuthorityName(String Name);
	List<GrantingAuthority> findAllByGrantingAuthorityName(String grantingAuthorityName);

}
