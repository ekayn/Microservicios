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


    @GetMapping("/obtener-codigo")
    public ResponseEntity<String> obtenerCodigoAcopio(@RequestParam("acopio") CollectionEntity acopio) {
        String codigo = collectionService.obtenerCodigo(acopio);
        if (codigo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(codigo);
    }

    @GetMapping("/obtener-leche")
    public ResponseEntity<Double> obtenerLecheAcopio(@RequestParam("acopio") CollectionEntity acopio) {
        Double leche = collectionService.obtenerLeche(acopio);
        if (leche == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leche);
    }

    @GetMapping("/{code}")
    public ResponseEntity<List<CollectionEntity>> obtenerAcopiosCodigo(@PathVariable("code") String code) {
        List<CollectionEntity> acopios = collectionService.obtenerAcopiosCodigo(code);
        if (acopios.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(acopios);
    }

    @GetMapping("/bonificacion-frecuencia")
    public ResponseEntity<Double> bonificacionFrecuencias(@RequestParam("acopios") List<CollectionEntity> acopios) {
        if (acopios.isEmpty()){
            ResponseEntity.ok(0);
        }
        Double bonificacion = collectionService.bonificacionFrecuencia(acopios);
        return ResponseEntity.ok(bonificacion);
    }

    @GetMapping("/leche-total")
    public ResponseEntity<Double> lecheTotal(@RequestParam("acopios") List<CollectionEntity> acopios) {
        if (acopios.isEmpty()){
            ResponseEntity.ok(0);
        }
        Double leche = collectionService.lecheTotal(acopios);
        return ResponseEntity.ok(leche);
    }

    @GetMapping("/quincena")
    public ResponseEntity<String> quincena(@RequestParam("acopios") List<CollectionEntity> acopios) {
        String fecha = collectionService.obtenerQuincena(acopios);
        return ResponseEntity.ok(fecha);
    }

    @GetMapping("/leche-promedio")
    public ResponseEntity<Double> lechePromedio(@RequestParam("lecheTotal") Double lecheTotal) {
        Double lechePromedio = collectionService.lechePromedio(lecheTotal);
        return ResponseEntity.ok(lechePromedio);
    }

    @GetMapping("/dias-entregas")
    public ResponseEntity<Double> diasEntregas(@RequestParam("acopios") List<CollectionEntity> acopios) {
        if (acopios.isEmpty()){
            ResponseEntity.ok(0);
        }
        Double totalDias = collectionService.diasEntregaTotal(acopios);
        return ResponseEntity.ok(totalDias);
    }

    @PostMapping("/subir-data")
    public void guardarAcopios(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException {
        collectionService.actualizarRegistrosLeche();
        collectionService.guardarCsv(file);
        collectionService.cargarCsv(file.getOriginalFilename());
    }

    @PostMapping
    public ResponseEntity<CollectionEntity> guardarAcopio(@RequestBody CollectionEntity acopio) {
        collectionService.guardarAcopio(collectionService.obtenerFecha(acopio),
                collectionService.obtenerTurno(acopio),
                collectionService.obtenerCodigo(acopio),
                collectionService.obtenerLeche(acopio));
        return ResponseEntity.ok(acopio);
    }
}
