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

    @GetMapping("/existe/{code}")
    public ResponseEntity<Boolean> existeProveedor(@PathVariable("code") String code){
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        if (proveedor == null){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }

    @GetMapping("/{code}")
    public ResponseEntity<SupplierEntity> obtenerProveedor(@PathVariable("code") String code){
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        if (proveedor == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(proveedor);
    }

    @GetMapping("/pago-categoria/{code}")
    public ResponseEntity<Double> pagoCategoriaCodigo(@PathVariable("code") String code){
        Double pago = supplierService.pagoCategoria(code);
        return ResponseEntity.ok(pago);
    }

    @GetMapping("/pago-retencion/{code}")
    public ResponseEntity<Double> pagoRetencionCodigo(@PathVariable("code") String code){
        Double pago = supplierService.pagoRetencion(code);
        return ResponseEntity.ok(pago);
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

    @GetMapping("/obtener-nombre/{code}")
    public ResponseEntity<String> obtenerNombre(@PathVariable("code") String code){
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        if (proveedor == null){
            return ResponseEntity.notFound().build();
        }
        String nombre = supplierService.obtenerNombre(proveedor);
        if (nombre == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nombre);
    }

    @GetMapping("/obtener-categoria/{code}")
    public ResponseEntity<String> obtenerCategoria(@PathVariable("code") String code){
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        if (proveedor == null){
            return ResponseEntity.notFound().build();
        }
        String categoria = supplierService.obtenerCategoria(proveedor);
        if (categoria == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/obtener-retencion/{code}")
    public ResponseEntity<String> obtenerRetencion(@PathVariable("code") String code){
        SupplierEntity proveedor = supplierService.obtenerProveedorCodigo(code);
        if (proveedor == null){
            return ResponseEntity.notFound().build();
        }
        String retencion = supplierService.obtenerRetencion(proveedor);
        if (retencion == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(retencion);
    }
}
