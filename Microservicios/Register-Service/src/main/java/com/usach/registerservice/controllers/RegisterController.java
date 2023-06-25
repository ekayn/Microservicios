package com.usach.registerservice.controllers;

import com.usach.registerservice.entities.RegisterEntity;
import com.usach.registerservice.services.RegisterService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
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

    @PostMapping
    public ResponseEntity<RegisterEntity> guardarRegistro(@RequestParam RegisterEntity registro) {
        registerService.guardarRegistro(registerService.obtenerCodigo(registro));
        return ResponseEntity.ok(registro);
    }

    @PostMapping("/actualizar-grasas-solidos")
    public ResponseEntity<String> actualizarRegistros(){
        registerService.actualizarRegistrosGrasasSolidos();
        return ResponseEntity.ok("Registros de grasas y solidos actualizados con éxito");
    }

    @PostMapping("/actualizar-leche")
    public ResponseEntity<String> actualizarLeche(){
        registerService.actualizarRegistrosLeche();
        return ResponseEntity.ok("Registros de leche actualizados con éxito");
    }

    @GetMapping("/{code}")
    public ResponseEntity<RegisterEntity> existeRegistro(@PathVariable("code") String code) {
        RegisterEntity registro = registerService.obtenerRegistroCodigo(code);
        if (registro == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registro);
    }

    @GetMapping("/obtener-leche")
    public ResponseEntity<Double> obtenerLeche(@RequestParam("registro") RegisterEntity registro) {
        Double leche = registerService.obtenerLeche(registro);
        if (leche == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leche);
    }

    @GetMapping("/obtener-grasa")
    public ResponseEntity<Double> obtenerGrasa(@RequestParam("registro") RegisterEntity registro) {
        Double grasa = registerService.obtenerGrasa(registro);
        if (grasa == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(grasa);
    }

    @GetMapping("/obtener-Solido")
    public ResponseEntity<Double> obtenerSolido(@RequestParam("registro") RegisterEntity registro) {
        Double solido = registerService.obtenerSolido(registro);
        if (solido == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(solido);
    }

    @GetMapping("/variacion-leche")
    public ResponseEntity<Double> variacionLeche(@RequestParam("valorAntes") Double valorAntes, @RequestParam("valorAhora") Double valorAhora) {
        Double variacion = registerService.variacionLeche(valorAntes, valorAhora);
        if (variacion == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(variacion);
    }

    @GetMapping("/variacion-grasa")
    public ResponseEntity<Double> variacionGrasa(@RequestParam("valorAntes") Double valorAntes, @RequestParam("valorAhora") Double valorAhora) {
        Double variacion = registerService.variacionGrasa(valorAntes, valorAhora);
        if (variacion == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(variacion);
    }

    @GetMapping("/variacion-solido")
    public ResponseEntity<Double> variacionSolido(@RequestParam("valorAntes") Double valorAntes, @RequestParam("valorAhora") Double valorAhora) {
        Double variacion = registerService.variacionSolido(valorAntes, valorAhora);
        if (variacion == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(variacion);
    }

    @GetMapping("/descuento-leche")
    public ResponseEntity<Double> descuentoLeche(@RequestParam("valor") Double valor) {
        Double descuento = registerService.descuentoLeche(valor);
        if (descuento == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(descuento);
    }

    @GetMapping("/descuento-grasa")
    public ResponseEntity<Double> descuentoGrasa(@RequestParam("valor") Double valor) {
        Double descuento = registerService.descuentoGrasa(valor);
        if (descuento == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(descuento);
    }
    @GetMapping("/descuento-solido")
    public ResponseEntity<Double> descuentoSolido(@RequestParam("valor") Double valor) {
        Double descuento = registerService.descuentoSolido(valor);
        if (descuento == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(descuento);
    }
}
