package com.usach.supplierservice.controllers;

import com.usach.supplierservice.entities.SupplierEntity;
import com.usach.supplierservice.services.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/proveedores")
@RestController
public class SupplierController{
    @Autowired
    SupplierService supplierService;

    @GetMapping
    public ResponseEntity<List<SupplierEntity>> listarProveedores(){
        List<SupplierEntity> proveedores = supplierService.obtenerProveedores();
        if (proveedores.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(proveedores);
    }

    @PostMapping
    public ResponseEntity<SupplierEntity> guardarProveedor(@RequestBody SupplierEntity proveedor) {
        String supplierCode = supplierService.obtenerCodigo(proveedor);
        String supplierName = supplierService.obtenerNombre(proveedor);
        String supplierCategory = supplierService.obtenerCategoria(proveedor);
        String supplierRetention = supplierService.obtenerRetencion(proveedor);

        if (supplierService.existeProveedorPorCodigo(supplierCode)){
            return ResponseEntity.notFound().build();
        } else if (supplierService.existeProveedorPorNombre(supplierName)){
            return ResponseEntity.notFound().build();
        }
        supplierService.guardarProveedor(supplierName, supplierCode, supplierCategory, supplierRetention);
        supplierService.ingresarRegistroCodigo(supplierCode);
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/existe-proveedor/{code}")
    public ResponseEntity<Boolean> existeProveedor(@PathVariable("code") String code){
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        if (proveedor == null){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
}
