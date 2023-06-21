package com.usach.mingeso.controllers;

import com.usach.mingeso.entities.CollectionEntity;
import com.usach.mingeso.services.CollectionService;
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
public class CollectionController {
    @Autowired
    CollectionService collectionService;

    @Autowired
    RegisterService registerService;

    @GetMapping("/listar-acopios")
    public String listarAcopios(Model model){
        ArrayList<CollectionEntity> acopios = collectionService.obtenerAcopios();
        model.addAttribute("acopios", acopios);
        return "collectionList";
    }

    @GetMapping("/subir-acopios")
    public String subirAcopios(){
        return "collectionImport";
    }

    @PostMapping("/subir-acopios")
    public String guardarAcopios(@RequestParam("file") MultipartFile file) {
        registerService.actualizarLeche();
        collectionService.guardarCsv(file);
        collectionService.cargarCsv(file.getOriginalFilename());
        return "redirect:/subir-acopios";
    }
}
