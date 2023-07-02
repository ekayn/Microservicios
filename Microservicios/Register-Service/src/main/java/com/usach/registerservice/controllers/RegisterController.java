package com.usach.registerservice.controllers;

import com.usach.registerservice.entities.RegisterEntity;
import com.usach.registerservice.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/registros")
@RestController
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @GetMapping
    public ResponseEntity<List<RegisterEntity>> listarRegistros(){
        List<RegisterEntity> registros = registerService.obtenerRegistros();
        if (registros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registros);
    }

    @PostMapping("/guardar/{code}")
    public void guardarRegistro(@PathVariable("code") String code) {
        registerService.guardarRegistro(code);
    }

    @PostMapping("/restablecer-grasas-solidos")
    public void restablecerGrasasSolidos(){
        registerService.restablecerGrasasSolidos();
    }

    @PostMapping("/guardar-grasa-solido/{code}/{valorGrasa}/{valorSolido}")
    public void guardarGrasasSolidos(@PathVariable("code") String code, @PathVariable("valorGrasa") Double valorGrasa, @PathVariable("valorSolido") Double valorSolido){
        registerService.guardarGrasaSolido(code, valorGrasa, valorSolido);
    }

    @PostMapping("/restablecer-leche")
    public void restablecerLeche(){
        registerService.restablecerLeche();
    }

    @PostMapping("/guardar-leche/{code}/{valor}")
    public void guardarLeche(@PathVariable("code") String code, @PathVariable("valor") Double valor){
        registerService.guardarLeche(code, valor);
    }

    @GetMapping("/{code}")
    public ResponseEntity<RegisterEntity> obtenerRegistro(@PathVariable("code") String code) {
        RegisterEntity registro = registerService.obtenerRegistroCodigo(code);
        if (registro == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    @GetMapping("/variacion-leche/{valorAntes}/{valorAhora}")
    public ResponseEntity<Double> variacionLeche(@PathVariable("valorAntes") Double valorAntes, @PathVariable("valorAhora") Double valorAhora) {
        Double variacion = registerService.variacionLeche(valorAntes, valorAhora);
        return ResponseEntity.ok(variacion);
    }

    @GetMapping("/variacion-grasa/{valorAntes}/{valorAhora}")
    public ResponseEntity<Double> variacionGrasa(@PathVariable("valorAntes") Double valorAntes, @PathVariable("valorAhora") Double valorAhora) {
        Double variacion = registerService.variacionGrasa(valorAntes, valorAhora);
        return ResponseEntity.ok(variacion);
    }

    @GetMapping("/variacion-solido/{valorAntes}/{valorAhora}")
    public ResponseEntity<Double> variacionSolido(@PathVariable("valorAntes") Double valorAntes, @PathVariable("valorAhora") Double valorAhora) {
        Double variacion = registerService.variacionSolido(valorAntes, valorAhora);
        return ResponseEntity.ok(variacion);
    }

    @GetMapping("/descuento-leche/{valor}")
    public ResponseEntity<Double> descuentoLeche(@PathVariable("valor") Double valor) {
        Double descuento = registerService.descuentoLeche(valor);
        return ResponseEntity.ok(descuento);
    }

    @GetMapping("/descuento-grasa/{valor}")
    public ResponseEntity<Double> descuentoGrasa(@PathVariable("valor") Double valor) {
        Double descuento = registerService.descuentoGrasa(valor);
        return ResponseEntity.ok(descuento);
    }

    @GetMapping("/descuento-solido/{valor}")
    public ResponseEntity<Double> descuentoSolido(@PathVariable("valor") Double valor) {
        Double descuento = registerService.descuentoSolido(valor);
        return ResponseEntity.ok(descuento);
    }

    @GetMapping("/grasa/{code}")
    public ResponseEntity<Double> obtenerGrasa(@PathVariable("code") String code){
        Double grasa = registerService.obtenerGrasa(code);
        return ResponseEntity.ok(grasa);
    }

    @GetMapping("/solido/{code}")
    public ResponseEntity<Double> obtenerSolido(@PathVariable("code") String code){
        Double solido = registerService.obtenerSolido(code);
        return ResponseEntity.ok(solido);
    }

    @GetMapping("/leche/{code}")
    public ResponseEntity<Double> obtenerLeche(@PathVariable("code") String code){
        Double leche = registerService.obtenerLeche(code);
        return ResponseEntity.ok(leche);
    }
}
