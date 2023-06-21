package com.usach.mingeso.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@Table(name = "registers")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RegisterEntity {
    @Id
    @Column(nullable=false, length = 5)
    private String code;
    private Double milk;
    private Double grease;
    private Double solid;
}
