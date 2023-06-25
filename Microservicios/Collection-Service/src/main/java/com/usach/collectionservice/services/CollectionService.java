package com.usach.collectionservice.services;

import com.usach.collectionservice.entities.CollectionEntity;
import com.usach.collectionservice.repositories.CollectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import lombok.Generated;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
public class CollectionService {
    @Autowired
    CollectionRepository collectionRepository;

    @Autowired
    RestTemplate restTemplate;

    public void actualizarRegistrosLeche(){
        restTemplate.postForObject("http://Register-Service/actualizar-registros-leche", null, Void.class);
    }

    public List<CollectionEntity> obtenerAcopios(){
        return collectionRepository.findAll();
    }

    public List<CollectionEntity> obtenerAcopiosCodigo(String code){
        return collectionRepository.findByCode(code);
    }

    public void guardarAcopio(String collectionDate, String collectionShift, String collectionSupplier, Double collectionMilk){
        CollectionEntity acopio = new CollectionEntity();
        acopio.setDate(collectionDate);
        acopio.setCode(collectionSupplier);
        if (collectionShift.equals("M") || collectionShift.equals("T")){
            acopio.setShift(collectionShift);
        } else{
            acopio.setShift("M");
        }
        if (collectionMilk < 0.0){
            acopio.setMilk(0.0);
        } else{
            acopio.setMilk(collectionMilk);
        }
        collectionRepository.save(acopio);
    }

    private final Logger logg = LoggerFactory.getLogger(CollectionService.class);

    @Generated
    public void guardarCsv(MultipartFile file){
        String filename = file.getOriginalFilename();
        if(filename != null){
            if(!file.isEmpty()){
                try{
                    byte [] bytes = file.getBytes();
                    Path path  = Paths.get(filename);
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
        collectionRepository.deleteAll();
        try{
            bf = new BufferedReader(new FileReader(direccion));
            String bfRead;
            int count = 1;
            while((bfRead = bf.readLine()) != null){
                if (count == 1){
                    count = 0;
                }
                else{
                    guardarAcopio(bfRead.split(";")[0], bfRead.split(";")[1], bfRead.split(";")[2], Double.parseDouble(bfRead.split(";")[3]));
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

    public double bonificacionFrecuencia(List<CollectionEntity> acopios){
        double totalManana = 0.0;
        double totalTarde = 0.0;
        for (CollectionEntity acopio : acopios) {
            if (acopio.getShift().equals("M")) {
                totalManana = totalManana + 1;
            } else if (acopio.getShift().equals("T")) {
                totalTarde = totalTarde + 1;
            }
        }  
        if (totalManana > 10 && totalTarde > 10) {
            return 0.20;
        } else if (totalManana > 10) {
            return 0.12;
        } else if (totalTarde > 10) {
            return 0.08;
        } else {
            return 0.0;
        }
    }

    public double lecheTotal(List<CollectionEntity> acopios){
        double totalLeche = 0.0;
        for (CollectionEntity acopio : acopios) {
            totalLeche = totalLeche + acopio.getMilk();
        }  
        return totalLeche;
    }

    public String obtenerQuincena(List<CollectionEntity> acopiosDB){
        if (!acopiosDB.isEmpty()){
            if (Integer.parseInt(acopiosDB.get(0).getDate().split("/")[2]) <= 15) {
                return acopiosDB.get(0).getDate().split("/")[0] + "/" + acopiosDB.get(0).getDate().split("/")[1] + "/" + "01";
            } else {
                return acopiosDB.get(0).getDate().split("/")[0] + "/" + acopiosDB.get(0).getDate().split("/")[1] + "/" + "02";
            }
        } else{
            return "####/##/##";
        }
    }

    public double lechePromedio(double lecheTotal){
        return (double) Math.abs(Math.round((lecheTotal / 15.0) * 100d)) / 100d;
    }

    public double diasEntregaTotal(List<CollectionEntity> acopios){
        return acopios.size();
    }

    public String obtenerCodigo(CollectionEntity acopio){
        return acopio.getCode();
    }

    public double obtenerLeche(CollectionEntity acopio){
        return acopio.getMilk();
    }

    public String obtenerFecha(CollectionEntity acopio){
        return acopio.getDate();
    }

    public String obtenerTurno(CollectionEntity acopio){
        return acopio.getShift();
    }

    public void eliminarAcopio(CollectionEntity acopio) {
        try{
            collectionRepository.deleteById(acopio.getId());
        }catch(Exception ignored){
        }
    }
}
