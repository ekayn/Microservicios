package com.usach.supplierservice.services;

import com.usach.supplierservice.entities.SupplierEntity;
import com.usach.supplierservice.repositories.SupplierRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
public class SupplierService {
    @Autowired
    SupplierRepository supplierRepository;

    @Autowired
    RestTemplate restTemplate;

    public void ingresarRegistroCodigo(SupplierEntity proveedor){
        restTemplate.postForObject("http://Register-Service/registros/guardar/" + proveedor.getCode(), null, Void.class);
    }

    public ArrayList<SupplierEntity> obtenerProveedores(){
        return (ArrayList<SupplierEntity>) supplierRepository.findAll();
    }

    public boolean existeProveedorPorCodigo(String code){
        return supplierRepository.existsById(code);
    }

    public boolean existeProveedorPorNombre(String name){
        return !supplierRepository.findByName(name).isEmpty();
    }

    public SupplierEntity obtenerProveedorCodigo(String code){
        return supplierRepository.findByCode(code);
    }

    public void guardarProveedor(SupplierEntity proveedor){
        supplierRepository.save(proveedor);
    }

    public double pagoCategoria(String category){
        return switch (category) {
            case "A" -> 700.0;
            case "B" -> 550.0;
            case "C" -> 400.0;
            case "D" -> 250.0;
            default -> 0.0;
        };
    }

    public double pagoRetencion(String retention) {
        if (retention.equals("Si")) {
            return 0.13;
        } else if (retention.equals("No")) {
            return 0.0;
        } else {
            return 0.0;
        }
    }


    public String obtenerCategoria(String code){
        SupplierEntity proveedor = obtenerProveedorCodigo(code);
        return proveedor.getCategory();
    }

    public String obtenerRetencion(String code){
        SupplierEntity proveedor = obtenerProveedorCodigo(code);
        return proveedor.getRetention();
    }

    public String obtenerNombre(String code){
        SupplierEntity proveedor = obtenerProveedorCodigo(code);
        return proveedor.getName();
    }
}
