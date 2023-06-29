package com.usach.greaseandsolidservice.controllers;

import com.usach.greaseandsolidservice.entities.GreaseAndSolidEntity;
import com.usach.greaseandsolidservice.services.GreaseAndSolidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.expression.ParseException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.FileNotFoundException;
import java.util.List;


@RestController
@RequestMapping("/grasas-solidos")
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

    @GetMapping("/existe/{code}")
    public ResponseEntity<Boolean> existeGrasaSolido(@PathVariable("code") String code){
        Boolean grasaSolido = greaseAndSolidService.existeGrasaSolidoCodigo(code);
        if (!grasaSolido){
            return ResponseEntity.ok(grasaSolido);
        }
        return ResponseEntity.ok(grasaSolido);
    }

    @GetMapping("/{code}")
    public ResponseEntity<GreaseAndSolidEntity> obtenerGrasaSolido(@PathVariable("code") String code){
        GreaseAndSolidEntity grasaSolido = greaseAndSolidService.obtenerGrasasSolidosCodigo(code);
        if (grasaSolido == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(grasaSolido);
    }

    @GetMapping("/pago-grasa/{grasa}")
    public ResponseEntity<Double> calculoPagoGrasa(@PathVariable("grasa") Double grasa) {
        Double grasaPago = greaseAndSolidService.pagoGrasa(grasa);
        return ResponseEntity.ok(grasaPago);
    }

    @GetMapping("/pago-solido/{solido}")
    public ResponseEntity<Double> calculoPagoSolido(@PathVariable("solido") Double solido) {
        Double solidoPago = greaseAndSolidService.pagoSolido(solido);
        return ResponseEntity.ok(solidoPago);
    }

    @PostMapping("/subir-data")
    public void guardarGrasasSolidos(@RequestParam("file") MultipartFile file) {
        greaseAndSolidService.restablecerRegistros();
        greaseAndSolidService.guardarCsv(file);
        greaseAndSolidService.cargarCsv(file.getOriginalFilename());
    }

    @PostMapping("/guardar/{codigo}/{grasa}/{solido}")
    public void guardarGrasasSolidos(@PathVariable("codigo") String codigo, @PathVariable("grasa") Double grasa, @PathVariable("solido") Double solido) {
                greaseAndSolidService.guardarGrasaYSolido(codigo, grasa, solido);
    }

}
