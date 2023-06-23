package com.usach.registerservice.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionModel {
    private String code;
    private String date;
    private String shift;
    private Double milk;
}
