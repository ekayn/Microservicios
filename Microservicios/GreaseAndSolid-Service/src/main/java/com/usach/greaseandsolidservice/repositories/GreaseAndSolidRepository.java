package com.usach.greaseandsolidservice.repositories;

import com.usach.greaseandsolidservice.entities.GreaseAndSolidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface GreaseAndSolidRepository extends JpaRepository<GreaseAndSolidEntity, String> {
    @Query("select e from GreaseAndSolidEntity e where e.code = :code")
    GreaseAndSolidEntity findByCode(@Param("code")String code);
}
