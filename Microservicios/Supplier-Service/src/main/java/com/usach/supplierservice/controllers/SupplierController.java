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

    @GetMapping("/categoria/{code}")
    public ResponseEntity<String> obtenerCategoria(@PathVariable("code") String code){
        String categoria = supplierService.obtenerCategoria(code);
        return ResponseEntity.ok(categoria);
    }

    @GetMapping("/retencion/{code}")
    public ResponseEntity<String> obtenerRetencion(@PathVariable("code") String code){
        String retencion = supplierService.obtenerRetencion(code);
        return ResponseEntity.ok(retencion);
    }

    @GetMapping("/nombre/{code}")
    public ResponseEntity<String> obtenerNombre(@PathVariable("code") String code){
        String nombre = supplierService.obtenerNombre(code);
        return ResponseEntity.ok(nombre);
    }

    @PostMapping
    public ResponseEntity<SupplierEntity> guardarProveedor(@RequestBody SupplierEntity proveedor) {
        supplierService.guardarProveedor(proveedor);
        supplierService.ingresarRegistroCodigo(proveedor);
        return ResponseEntity.ok(proveedor);
    }
}
