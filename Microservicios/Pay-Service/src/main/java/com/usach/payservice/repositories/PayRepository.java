package com.usach.payservice.repositories;

import com.usach.payservice.entities.PayEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PayRepository extends JpaRepository<PayEntity, String> {
    @Query("select e from PayEntity e where e.code = :code")
    PayEntity findByCode(@Param("code")String code);
}
