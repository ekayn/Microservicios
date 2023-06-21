package com.usach.mingeso.services;

import com.usach.mingeso.entities.GreaseAndSolidEntity;
import com.usach.mingeso.repositories.GreaseAndSolidRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.Generated;
import java.util.ArrayList;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GreaseAndSolidService {
    @Autowired
    GreaseAndSolidRepository greaseAndSolidRepository;

    public ArrayList<GreaseAndSolidEntity> obtenerGrasasYSolidos(){
        return (ArrayList<GreaseAndSolidEntity>) greaseAndSolidRepository.findAll();
    }

    public boolean existeGrasaSolidoCodigo(String code){
        return greaseAndSolidRepository.existsById(code);
    }

    public void guardarGrasaYSolido(String greaseAndSolidCode, Double greaseAndSolidGrease, Double greaseAndSolidSolid){
        GreaseAndSolidEntity grasaYSolido = new GreaseAndSolidEntity();
        grasaYSolido.setCode(greaseAndSolidCode);
        if (greaseAndSolidGrease < 0.0 && greaseAndSolidSolid < 0.0){
            grasaYSolido.setGrease(0.0);
            grasaYSolido.setSolid(0.0);
        } else if (greaseAndSolidGrease < 0.0){
            grasaYSolido.setGrease(0.0);
            grasaYSolido.setSolid(greaseAndSolidSolid);
        } else if (greaseAndSolidSolid < 0.0){
            grasaYSolido.setGrease(greaseAndSolidGrease);
            grasaYSolido.setSolid(0.0);
        } else{
            grasaYSolido.setGrease(greaseAndSolidGrease);
            grasaYSolido.setSolid(greaseAndSolidSolid);
        }
        greaseAndSolidRepository.save(grasaYSolido);
    }

    public GreaseAndSolidEntity obtenerGrasasSolidosCodigo(String code){
        return greaseAndSolidRepository.getReferenceById(code);
    }

    private final Logger logg = LoggerFactory.getLogger(CollectionService.class);
    @Generated
    public void guardarCsv(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(file.getOriginalFilename());
                    Files.write(path, bytes);
                    logg.info("Archivo guardado");
                }
                catch (IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }
    @Generated
    public void cargarCsv(String direccion){
        BufferedReader bf = null;
        //greaseAndSolidRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    if (greaseAndSolidRepository.existsById(bfRead.split(";")[0])){
                        greaseAndSolidRepository.getReferenceById(bfRead.split(";")[0]).setGrease(Double.parseDouble(bfRead.split(";")[1]));
                        greaseAndSolidRepository.getReferenceById(bfRead.split(";")[0]).setSolid(Double.parseDouble(bfRead.split(";")[2]));
                    } else {
                        guardarGrasaYSolido(bfRead.split(";")[0], Double.parseDouble(bfRead.split(";")[1]), Double.parseDouble(bfRead.split(";")[2]));
                    }
                }
            }
            System.out.println("ARCHIVO CARGADO EXITOSAMENTE");
        }catch(Exception e){
            System.err.println("ERROR AL CARGAR EL ARCHIVO");
        }finally{
            if(bf != null){
                try{
                    bf.close();
                }catch(IOException e){
                    logg.error("ERROR", e);
                }
            }
        }
    }

    public double pagoGrasa(Double grasa){
        if (grasa < 0.0){
            return 0.0;
        } else if (grasa <= 20.0) {
            return 30.0;
        } else if (grasa <= 45.0) {
            return 80.0;
        } else {
            return 120.0;
        }
    }

    public double pagoSolido(Double solido){
        if (solido < 0.0){
            return 0.0;
        } else if (solido <= 7.0) {
            return -130.0;
        } else if (solido <= 18.0) {
            return -90.0;
        } else if (solido <= 35.0) {
            return 95.0;
        } else {
            return 150.0;
        }
    }

    public double obtenerGrasa(GreaseAndSolidEntity grasaSolido){
        return grasaSolido.getGrease();
    }

    public double obtenerSolido(GreaseAndSolidEntity grasaSolido){
        return grasaSolido.getSolid();
    }

    public void eliminarGrasaSolido(GreaseAndSolidEntity grasaSolido) {
        try{
            greaseAndSolidRepository.deleteById(grasaSolido.getCode());
        }catch(Exception ignored){
        }
    }
}
