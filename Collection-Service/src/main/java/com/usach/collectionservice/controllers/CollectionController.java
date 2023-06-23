package com.usach.collectionservice.controllers;

import com.usach.collectionservice.entities.CollectionEntity;
import com.usach.collectionservice.services.CollectionService;
import org.apache.commons.lang.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.spel.ast.NullLiteral;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/acopios")
@RestController
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

    @PostMapping
    public ResponseEntity<CollectionEntity> guardarAcopio(@RequestBody CollectionEntity acopio) {
        collectionService.guardarAcopio(collectionService.obtenerFecha(acopio),
                collectionService.obtenerTurno(acopio),
                collectionService.obtenerCodigo(acopio),
                collectionService.obtenerLeche(acopio));
        return ResponseEntity.ok(acopio);
    }

    @GetMapping("/obtener-codigo-acopio/{acopio}")
    public ResponseEntity<String> obtenerCodigoAcopio(@PathVariable("acopio") CollectionEntity acopio) {
        String codigo = collectionService.obtenerCodigo(acopio);
        if (codigo == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(codigo);
    }

    @GetMapping("/obtener-leche-acopio/{acopio}")
    public ResponseEntity<Double> obtenerLecheAcopio(@PathVariable("acopio") CollectionEntity acopio) {
        Double leche = collectionService.obtenerLeche(acopio);
        if (leche == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(leche);
    }

}
