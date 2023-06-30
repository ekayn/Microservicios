package com.usach.registerservice.services;

import com.usach.registerservice.entities.RegisterEntity;
import com.usach.registerservice.repositories.RegisterRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public class RegisterService {
    @Autowired
    RegisterRepository registerRepository;

    private final Logger logg = LoggerFactory.getLogger(RegisterService.class);

    public List<RegisterEntity> obtenerRegistros(){
        return registerRepository.findAll();
    }

    public RegisterEntity obtenerRegistroCodigo(String code){
        return registerRepository.getReferenceById(code);
    }

    public void restablecerLeche(){
        List<RegisterEntity> registros = registerRepository.findAll();
        for (RegisterEntity registro : registros){
            registro.setMilk(0.0);
            registerRepository.save(registro);
        }
    }

    public void guardarLeche(String code, Double valor){
        RegisterEntity registro = obtenerRegistroCodigo(code);
        registro.setMilk(registro.getMilk() + valor);
        registerRepository.save(registro);
    }

    public void restablecerGrasasSolidos(){
        List<RegisterEntity> registros = registerRepository.findAll();
        for (RegisterEntity registro : registros){
            registro.setGrease(0.0);
            registro.setSolid(0.0);
            registerRepository.save(registro);
        }
    }

    public void guardarGrasaSolido(String code, Double valorGrasa, Double valorSolido){
        RegisterEntity registro = obtenerRegistroCodigo(code);
        registro.setGrease(valorGrasa);
        registro.setSolid(valorSolido);
        registerRepository.save(registro);
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
}
