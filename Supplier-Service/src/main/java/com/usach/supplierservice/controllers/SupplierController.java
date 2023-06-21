package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.SupplierEntity;
import com.usach.mingeso.services.RegisterService;
import com.usach.mingeso.services.SupplierService;
import org.springframework.ui.Model;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RequestMapping
@Controller
public class SupplierController {
    @Autowired
    SupplierService supplierService;

    @Autowired
    RegisterService registerService;

    @GetMapping("/listar-proveedores")
    public String listarProveedores(Model model){
        ArrayList<SupplierEntity>proveedores = supplierService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "supplierList";
    }

    @GetMapping("/ingresar-proveedor")
    public String proveedor(){
        return "supplierAdd";
    }
    @PostMapping("/ingresar-proveedor")
    public String nuevoProveedor(@RequestParam("supplierName") String supplierName,
                                 @RequestParam("supplierCode") String supplierCode,
                                 @RequestParam("supplierCategory") String supplierCategory,
                                 @RequestParam("supplierRetention") String supplierRetention,
                                 RedirectAttributes redirectAttributes) throws InterruptedException {
        if (supplierService.existeProveedorPorCodigo(supplierCode)){
            redirectAttributes.addFlashAttribute("mensaje", "¡Código de proveedor ya existente!");
        }else{
            if (supplierService.existeProveedorPorNombre(supplierName)){
                redirectAttributes.addFlashAttribute("mensaje", "¡Nombre de proveedor ya existente!");
            } else {
                supplierService.guardarProveedor(supplierName, supplierCode, supplierCategory, supplierRetention);
                registerService.guardarRegistro(supplierCode);
                redirectAttributes.addFlashAttribute("mensaje", "¡Proveedor guardado con éxito!");
            }
        }
        return "redirect:/ingresar-proveedor";
    }
}
