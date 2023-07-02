package com.usach.registerservice.repositories;

import com.usach.registerservice.entities.RegisterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RegisterRepository extends JpaRepository<RegisterEntity, String> {
    @Query("select e from RegisterEntity e where e.code = :code")
    RegisterEntity findByCode(@Param("code")String code);
}
