package com.usach.mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "greaseAndSolids")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreaseAndSolidEntity {
    @Id
    @Column(nullable=false, length = 5)
    private String code;
    private Double grease;
    private Double solid;
}
