package com.riwi.vacants.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.riwi.vacants.entity.Vacant;

@Repository
public interface VacantRepository extends JpaRepository<Vacant, Long>{
    
}
