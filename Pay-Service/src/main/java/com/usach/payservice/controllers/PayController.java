package com.usach.payservice.controllers;

import com.usach.payservice.entities.PayEntity;
import com.usach.payservice.services.PayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@RequestMapping("/pagos")
@RestController
public class PayController {
    @Autowired
    PayService payService;

    @GetMapping
    public ResponseEntity<List<PayEntity>> obtenerPagos(){
        List<PayEntity> pagos = payService.obtenerPagos();
        if (pagos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(pagos);
    }

    @PostMapping("/{code}")
    public ResponseEntity<PayEntity> crearPago(@PathVariable("code") String code) {
        if (payService.existeProveedor(code)){
            if (payService.existeGrasaSolido(code)){
                payService.pagarPorId(code);
                return ResponseEntity.ok(payService.obtenerPagoCodigo(code));
            } else{
                return ResponseEntity.notFound().build();
            }
        }
        return ResponseEntity.notFound().build();
    }
}
