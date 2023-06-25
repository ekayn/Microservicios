package com.usach.supplierservice.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
@Table(name = "suppliers")
public class SupplierEntity {
    @Id
    @Column(nullable=false, length = 5)
    private String code;
    private String name;
    private String category;
    private String retention;
}
