package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.GreaseAndSolidEntity;
import com.usach.mingeso.services.GreaseAndSolidService;
import com.usach.mingeso.services.RegisterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;


@RequestMapping
@Controller
public class GreaseAndSolidController {
    @Autowired
    GreaseAndSolidService greaseAndSolidService;

    @Autowired
    RegisterService registerService;

    @GetMapping("/listar-grasas-solidos")
    public String listarGrasasSolidos(Model model){
        ArrayList<GreaseAndSolidEntity> grasasYSolidos = greaseAndSolidService.obtenerGrasasYSolidos();
        model.addAttribute("grasasYSolidos", grasasYSolidos);
        return "greaseAndSolidList";
    }

    @GetMapping("/subir-grasas-solidos")
    public String subirGrasasSolidos() { return "greaseAndSolidImport";}

    @PostMapping("/subir-grasas-solidos")
    public String guardarGrasasSolidos(@RequestParam("file") MultipartFile file) {
        registerService.actualizarGrasaSolido();
        greaseAndSolidService.guardarCsv(file);
        greaseAndSolidService.cargarCsv(file.getOriginalFilename());
        return "redirect:/subir-grasas-solidos";
    }
}
