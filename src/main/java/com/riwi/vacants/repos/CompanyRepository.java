package com.riwi.vacants.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.vacants.entity.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, String>{
    
}
