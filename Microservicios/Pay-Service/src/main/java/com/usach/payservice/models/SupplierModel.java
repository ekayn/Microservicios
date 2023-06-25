package com.usach.payservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupplierModel {
    private String code;
    private String name;
    private String category;
    private String retention;
}
