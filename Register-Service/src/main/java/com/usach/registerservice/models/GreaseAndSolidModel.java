package com.usach.registerservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GreaseAndSolidModel {
    private String code;
    private Double grease;
    private Double solid;
}
