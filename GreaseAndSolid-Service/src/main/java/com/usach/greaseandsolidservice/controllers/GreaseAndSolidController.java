package com.usach.greaseandsolidservice.controllers;

import com.usach.greaseandsolidservice.entities.GreaseAndSolidEntity;
import com.usach.greaseandsolidservice.services.GreaseAndSolidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/grasas-solidos")
@RestController
public class GreaseAndSolidController {
    @Autowired
    GreaseAndSolidService greaseAndSolidService;

    @GetMapping
    public ResponseEntity<List<GreaseAndSolidEntity>> obtenerGrasasSolidos(){
        List<GreaseAndSolidEntity> grasasSolidos = greaseAndSolidService.obtenerGrasasYSolidos();
        if (grasasSolidos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(grasasSolidos);
    }

    @PostMapping
    public ResponseEntity<GreaseAndSolidEntity> guardarGrasasSolidos(@RequestBody GreaseAndSolidEntity grasaSolido) {
        greaseAndSolidService.guardarGrasaYSolido(greaseAndSolidService.obtenerCodigo(grasaSolido),
                greaseAndSolidService.obtenerGrasa(grasaSolido),
                greaseAndSolidService.obtenerSolido(grasaSolido));
        return ResponseEntity.ok(grasaSolido);
    }

    @GetMapping("/existe-grasa-solido/{code}")
    public ResponseEntity<Boolean> existeGrasaSolido(@PathVariable("code") String code){
        GreaseAndSolidEntity grasaSolido = greaseAndSolidService.obtenerGrasasSolidosCodigo(code);
        if (grasaSolido == null){
            return ResponseEntity.ok(false);
        }
        return ResponseEntity.ok(true);
    }
    /*
    @PostMapping("/guardar")
    public String guardarGrasasSolidos(@RequestParam("file") MultipartFile file) {
        greaseAndSolidService.actualizarRegistros();
        greaseAndSolidService.guardarCsv(file);
        greaseAndSolidService.cargarCsv(file.getOriginalFilename());

        return "redirect:/subir";
    }
    */

}
