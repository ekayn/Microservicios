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
    public ResponseEntity<List<RegisterEntity>> listarRegistros(Model model){
        List<RegisterEntity> registros = registerService.obtenerRegistros();
        if (registros.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(registros);
    }

    @PostMapping
    public ResponseEntity<RegisterEntity> guardarRegistro(@RequestBody RegisterEntity registro) {
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

    public double obtenerLeche(RegisterEntity registro){
        return registro.getMilk();
    }

    public double obtenerGrasa(RegisterEntity registro){
        return registro.getGrease();
    }

    public double obtenerSolido(RegisterEntity registro){
        return registro.getSolid();
    }
}
