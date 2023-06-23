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
    public ResponseEntity<RegisterEntity> guardarRegistro(@RequestBody RegisterEntity registro){
        registerService.guardarRegistro(registerService.obtenerCodigo(registro));
        return ResponseEntity.ok(registro);
    }

    @PostMapping("/actualizar-registros-grasas-solidos")
    public ResponseEntity<String> actualizarRegistros(){
        registerService.actualizarRegistrosGrasasSolidos();
        return ResponseEntity.ok("Registros de grasas y solidos actualizados con éxito");
    }

    @PostMapping("/actualizar-registros-leche")
    public ResponseEntity<String> actualizarLeche(){
        registerService.actualizarRegistrosLeche();
        return ResponseEntity.ok("Registros de leche actualizados con éxito");
    }
}
