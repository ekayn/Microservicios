package com.usach.payservice.repositories;

import com.usach.payservice.entities.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<PayEntity, String> {
    
}
