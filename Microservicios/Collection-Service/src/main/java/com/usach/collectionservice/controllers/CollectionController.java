package com.usach.collectionservice.controllers;

import com.usach.collectionservice.entities.CollectionEntity;
import com.usach.collectionservice.services.CollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/acopios")
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @GetMapping
    public ResponseEntity<List<CollectionEntity>> obtenerTodosAcopios() {
        List<CollectionEntity> acopios = collectionService.obtenerAcopios();
        if (acopios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios);
    }

    @GetMapping("/{code}")
    public ResponseEntity<List<CollectionEntity>> obtenerAcopiosCodigo(@PathVariable("code") String code) {
        List<CollectionEntity> acopios = collectionService.obtenerAcopiosCodigo(code);
        if (acopios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios);
    }

    @GetMapping("/bonificacion-frecuencia/{code}")
    public ResponseEntity<Double> bonificacionFrecuencias(@PathVariable("code") String code) {
        Double bonificacion = collectionService.bonificacionFrecuencia(code);
        return ResponseEntity.ok(bonificacion);
    }

    @GetMapping("/leche-total/{code}")
    public ResponseEntity<Double> lecheTotal(@PathVariable("code") String code) {
        Double leche = collectionService.lecheTotal(code);
        return ResponseEntity.ok(leche);
    }

    @GetMapping("/quincena")
    public ResponseEntity<String> quincena() {
        String fecha = collectionService.obtenerQuincena();
        return ResponseEntity.ok(fecha);
    }

    @GetMapping("/leche-promedio/{total}")
    public ResponseEntity<Double> lechePromedio(@PathVariable("total") Double total) {
        Double lechePromedio = collectionService.lechePromedio(total);
        return ResponseEntity.ok(lechePromedio);
    }

    @GetMapping("/dias-entregas/{code}")
    public ResponseEntity<Double> diasEntregas(@PathVariable("code") String code) {
        Double totalDias = collectionService.diasEntregaTotal(code);
        return ResponseEntity.ok(totalDias);
    }

    @PostMapping("/subir-data")
    public void guardarData(@RequestParam("file") MultipartFile file) {
        collectionService.restablecerRegistrosLeche();
        collectionService.guardarCsv(file);
        collectionService.cargarCsv(file.getOriginalFilename());
    }
}
