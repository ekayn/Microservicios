package com.usach.supplierservice.controllers;

import com.usach.supplierservice.entities.SupplierEntity;
import com.usach.supplierservice.services.SupplierService;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/pago-categoria/{ctg}")
    public ResponseEntity<Double> pagoCategoriaCodigo(@PathVariable("ctg") String ctg){
        Double pago = supplierService.pagoCategoria(ctg);
        return ResponseEntity.ok(pago);
    }

    @GetMapping("/pago-retencion/{rtc}")
    public ResponseEntity<Double> pagoRetencionCodigo(@PathVariable("rtc") String rtc){
        Double pago = supplierService.pagoRetencion(rtc);
        return ResponseEntity.ok(pago);
    }

    @PostMapping("/{supplierCode}/{supplierName}/{supplierCategory}/{supplierRetention}")
    public ResponseEntity<String> guardarProveedor(@PathVariable("supplierCode") String supplierCode,
                                                           @PathVariable("supplierName") String supplierName,
                                                           @PathVariable("supplierCategory") String supplierCategory,
                                                           @PathVariable("supplierRetention") String supplierRetention) {
        if (supplierService.existeProveedorPorCodigo(supplierCode)){
            return ResponseEntity.ok("Codigo del roveedor ya existente");
        } else if (supplierService.existeProveedorPorNombre(supplierName)){
            return ResponseEntity.ok("Nombre del roveedor ya existente");
        }
        supplierService.guardarProveedor(supplierName, supplierCode, supplierCategory, supplierRetention);
        supplierService.ingresarRegistroCodigo(supplierCode);
        return ResponseEntity.ok("Proveedor guardado con éxito");
    }
}
