package com.usach.greaseandsolidservice.repositories;

import com.usach.greaseandsolidservice.entities.GreaseAndSolidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreaseAndSolidRepository extends JpaRepository<GreaseAndSolidEntity, String> {

}
