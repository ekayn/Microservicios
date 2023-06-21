package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.PayEntity;
import com.usach.mingeso.entities.SupplierEntity;
import com.usach.mingeso.services.GreaseAndSolidService;
import com.usach.mingeso.services.PayService;
import com.usach.mingeso.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;

@RequestMapping
@Controller
public class PayController {
    @Autowired
    PayService payService;

    @Autowired
    SupplierService supplierService;

    @Autowired
    GreaseAndSolidService greaseAndSolidService;

    @GetMapping("/listar-pagos-1")
    public String listarPagos1(Model model){
        ArrayList<PayEntity> pagos = payService.obtenerPagos();
        model.addAttribute("pagos", pagos);
        return "payList1";
    }

    @GetMapping("/listar-pagos-2")
    public String listarPagos2(Model model){
        ArrayList<PayEntity> pagos = payService.obtenerPagos();
        model.addAttribute("pagos", pagos);
        return "payList2";
    }

    @GetMapping("/ingresar-pago")
    public String pago(Model model){
        ArrayList<SupplierEntity>proveedores = supplierService.obtenerProveedores();
        model.addAttribute("proveedores", proveedores);
        return "payAdd";
    }
    @PostMapping("/ingresar-pago")
    public String nuevoPago(@RequestParam("code") String code,
                                 RedirectAttributes redirectAttributes){
        if (supplierService.existeProveedorPorCodigo(code)){
            if (greaseAndSolidService.existeGrasaSolidoCodigo(code)){
                payService.pagarPorId(code);
                redirectAttributes.addFlashAttribute("mensaje", "¡Pago generado!");
            } else{
                redirectAttributes.addFlashAttribute("mensaje", "¡El proveedor no posee datos de grasas y solidos totales!");
            }
        } else{
            redirectAttributes.addFlashAttribute("mensaje", "¡Código ingresado no valido!");
        }
        return "redirect:/ingresar-pago";
    }

}
