package com.usach.payservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterModel {
    private String code;
    private Double milk;
    private Double grease;
    private Double solid;
}
