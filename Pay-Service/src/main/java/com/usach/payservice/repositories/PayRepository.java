package com.usach.mingeso.repositories;

import com.usach.mingeso.entities.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<PayEntity, String> {
    
}
