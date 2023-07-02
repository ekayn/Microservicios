package com.usach.supplierservice.repositories;

import com.usach.supplierservice.entities.SupplierEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface SupplierRepository extends JpaRepository<SupplierEntity, String> {
    ArrayList<SupplierEntity> findByName(String name);
    @Query("select e from SupplierEntity e where e.code = :code")
    SupplierEntity findByCode(@Param("code")String code);
}
