package com.usach.collectionservice.repositories;

import com.usach.collectionservice.entities.CollectionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public interface CollectionRepository extends JpaRepository<CollectionEntity, Long> {  
    ArrayList<CollectionEntity> findByCode(String code);
}
