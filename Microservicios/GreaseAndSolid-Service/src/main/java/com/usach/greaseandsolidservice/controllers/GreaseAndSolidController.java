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
            return ResponseEntity.notFound().build();
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

    @GetMapping("/obtener-grasa/{code}")
    public ResponseEntity<Double> obtenerGrasa(@PathVariable("code") String code) {
        Double grasa = greaseAndSolidService.obtenerGrasa(code);
        return ResponseEntity.ok(grasa);
    }

    @GetMapping("/obtener-solido/{code}")
    public ResponseEntity<Double> obtenerSolido(@PathVariable("code") String code) {
        Double solido = greaseAndSolidService.obtenerSolido(code);
        return ResponseEntity.ok(solido);
    }

    @GetMapping("/pago-grasa/{grasa}")
    public ResponseEntity<Double> calculoPagoGrasa(@PathVariable("grasa") String grasa) {
        Double grasaPago = greaseAndSolidService.pagoGrasa(Double.parseDouble(grasa));
        return ResponseEntity.ok(grasaPago);
    }

    @GetMapping("/pago-solido/{solido}")
    public ResponseEntity<Double> calculoPagoSolido(@PathVariable("solido") String solido) {
        Double solidoPago = greaseAndSolidService.pagoSolido(Double.parseDouble(solido));
        return ResponseEntity.ok(solidoPago);
    }

    @PostMapping("/subir-data")
    public void guardarGrasasSolidos(@RequestParam("file") MultipartFile file, RedirectAttributes ms) throws FileNotFoundException, ParseException {
        greaseAndSolidService.actualizarRegistros();
        greaseAndSolidService.guardarCsv(file);
        greaseAndSolidService.cargarCsv(file.getOriginalFilename());
    }

    @PostMapping("/guardar/{codigo}/{grasa}/{solido}")
    public void guardarGrasasSolidos(@PathVariable("codigo") String codigo, @PathVariable("grasa") String grasa, @PathVariable("solido") String solido) {
                greaseAndSolidService.guardarGrasaYSolido(codigo, Double.parseDouble(grasa), Double.parseDouble(solido));
    }

    @PostMapping("/eliminar")
    public void eliminarGrasaSolido(@RequestBody GreaseAndSolidEntity grasaSolido) {
        greaseAndSolidService.eliminarGrasaSolido(grasaSolido);
    }

}
