package com.usach.registerservice.services;

import com.usach.registerservice.entities.RegisterEntity;
import com.usach.registerservice.models.CollectionModel;
import com.usach.registerservice.models.GreaseAndSolidModel;
import com.usach.registerservice.repositories.RegisterRepository;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class RegisterService {
    @Autowired
    RegisterRepository registerRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<RegisterEntity> obtenerRegistros(){
        return registerRepository.findAll();
    }

    public RegisterEntity obtenerRegistroCodigo(String code){
        return registerRepository.getReferenceById(code);
    }

    @Generated
    public void actualizarRegistrosLeche(){
        for (RegisterEntity registro : registerRepository.findAll()){
            registro.setMilk(0.0);
        }
        List<CollectionModel> acopios = restTemplate.getForObject("http://Collection-Service/acopios", List.class);

        for (CollectionModel acopio : acopios){
            String code = restTemplate.getForObject("http://Collection-Service/acopios/" + acopio, String.class);
            Double leche = restTemplate.getForObject("http://Collection-Service/acopios/obtener-leche" + acopio, Double.class);
            RegisterEntity registro = registerRepository.getReferenceById(code);
            registro.setMilk(leche + registro.getMilk());
        }
    }

    @Generated
    public void actualizarRegistrosGrasasSolidos(){
        for (RegisterEntity registro : registerRepository.findAll()){
            registro.setGrease(restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/obtener-grasa" + restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/" + registro.getCode(), GreaseAndSolidModel.class), Double.class));
            registro.setSolid(restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/obtener-solido" + restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/" + registro.getCode(), GreaseAndSolidModel.class), Double.class));
        }
    }

    public void guardarRegistro(String registerCode){
        RegisterEntity registro = new RegisterEntity();
        registro.setCode(registerCode);
        registro.setMilk(0.0);
        registro.setGrease(0.0);
        registro.setSolid(0.0);
        registerRepository.save(registro);
    }

    public double variacionLeche(Double lecheAntes, Double lecheAhora){
        if (lecheAntes <= 0.0) {
            return lecheAhora * 100.0;
        } else {
            double variacion = (double) Math.round((100 * (Math.abs(1 - lecheAhora / lecheAntes))) * 100d) / 100d;
            if (variacion < 100.0) {
                return variacion * (-1);
            } else{
                return variacion;
            }
        }
    }

    public double variacionGrasa(Double grasaAntes, Double grasaAhora){
        if (grasaAntes <= 0.0) {
            return grasaAhora * 100.0;
        } else {
            double variacion = (double) Math.round((100 * (Math.abs(1 - grasaAhora / grasaAntes))) * 100d) / 100d;
            if (variacion < 100.0) {
                return variacion * (-1);
            } else{
                return variacion;
            }
        }        
    }

    public double variacionSolido(Double solidoAntes, Double solidoAhora){
        if (solidoAntes <= 0.0) {
            return solidoAhora * 100.0;
        } else {
            double variacion = (double) Math.round((100 * (Math.abs(1 - solidoAhora / solidoAntes))) * 100d) / 100d;
            if (variacion < 100.0) {
                return variacion * (-1);
            } else{
                return variacion;
            }
        }        
    }

    public double descuentoLeche(Double variacion){
        if (variacion > 0.0){
            return 0.0;
        } else if (variacion <= -46.0) {
            return 0.30;
        } else if (variacion <= -26.0){
            return 0.15;
        } else if (variacion <= -9.0){
            return 0.07;
        } else{
            return 0.0;
        }
    }

    public double descuentoGrasa(Double variacion){
        if (variacion > 0.0){
            return 0.0;
        } else if (variacion <= -41.0) {
            return 0.30;
        } else if (variacion <= -26.0){
            return 0.20;
        } else if (variacion <= -16.0){
            return 0.12;
        } else{
            return 0.0;
        }
    }

    public double descuentoSolido(Double variacion){
        if (variacion > 0.0){
            return 0.0;
        } else if (variacion <= -36.0) {
            return 0.45;
        } else if (variacion <= -13.0){
            return 0.27;
        } else if (variacion <= -7.0){
            return 0.18;
        } else{
            return 0.0;
        }
    }

    public double obtenerLeche(RegisterEntity registro){
        return registro.getMilk();
    }

    public String obtenerCodigo(RegisterEntity registro){
        return registro.getCode();
    }

    public double obtenerGrasa(RegisterEntity registro){
        return registro.getGrease();
    }

    public double obtenerSolido(RegisterEntity registro){
        return registro.getSolid();
    }

    public void eliminarRegistro(String id) {
        try{
            registerRepository.deleteById(id);
        }catch(Exception ignore){
        }
    }
}
