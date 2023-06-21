package com.usach.mingeso.repositories;

import com.usach.mingeso.entities.GreaseAndSolidEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GreaseAndSolidRepository extends JpaRepository<GreaseAndSolidEntity, String> {

}
