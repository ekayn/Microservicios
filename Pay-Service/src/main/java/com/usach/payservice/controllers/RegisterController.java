package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.RegisterEntity;
import com.usach.mingeso.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@RequestMapping
@Controller
public class RegisterController {
    @Autowired
    RegisterService registerService;

    @GetMapping("/listar-registros")
    public String listarRegistros(Model model){
        ArrayList<RegisterEntity> registros = registerService.obtenerRegistros();
        model.addAttribute("registros", registros);
        return "registerList";
    }
}
