package com.usach.payservice.services;

import com.usach.payservice.entities.*;
import com.usach.payservice.models.CollectionModel;
import com.usach.payservice.models.GreaseAndSolidModel;
import com.usach.payservice.models.RegisterModel;
import com.usach.payservice.models.SupplierModel;
import com.usach.payservice.repositories.*;
import lombok.Generated;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class PayService {
    @Autowired
    PayRepository payRepository;

    @Autowired
    RestTemplate restTemplate;

    public List<PayEntity> obtenerPagos(){
        return payRepository.findAll();
    }

    public Double pagoTotalLeche(Double milkPay, Double greasePay, Double solidPay, Double frecuencyBonification){
        double milk = 1.0;
        double grease = 1.0;
        double solid = 1.0;
        double frecuency = 1.0;
        if (milkPay < 0.0){
            milk = 0.0;
        }
        if (greasePay < 0.0){
            grease = 0.0;
        }
        if (solidPay < 0.0){
            solid = 0.0;
        }
        if (frecuencyBonification < 0.0){
            frecuency = 0.0;
        }
        return milk * milkPay + grease * greasePay + solid * solidPay + frecuency * frecuencyBonification;
    }

    public Double descuentoTotal(Double milkDiscount,Double greaseDiscount, Double solidDiscount){
        double milk = 1.0;
        double grease = 1.0;
        double solid = 1.0;

        if (milkDiscount < 0.0){
            milk = 0.0;
        }
        if (greaseDiscount < 0.0){
            grease = 0.0;
        }
        if (solidDiscount < 0.0){
            solid = 0.0;
        }
        return milk * milkDiscount + grease * greaseDiscount + solid * solidDiscount;
    }

    public Double calcularPago(Double milkTotalPay, Double totalDiscount){
        double pago = 1.0;
        double descuento = 1.0;

        if (milkTotalPay < 0.0){
            pago = 0.0;
        }
        if (totalDiscount < 0.0){
            descuento = 0.0;
        }
        return pago * milkTotalPay - descuento * totalDiscount;
    }

    public Double calcularRetencion(Double retencion, Double monto){
        if (monto > 950000.0) {
            return retencion * monto;
        } else if (monto < 0.0 || retencion < 0.0 || retencion > 1.0) {
            return 0.0;
        } else{
            return 0.0;
        }
    }

    public Double pagoTotal(Double retencion, Double monto){
        if (monto < 0.0 ){
            return 0.0;
        }
        if (retencion < 0.0) {
            return monto;
        }
        return monto - retencion;
    }

    public Boolean existeProveedor(String code){
        return restTemplate.getForObject("http://Supplier-Service/proveedores/existe/" + code, Boolean.class);
    }

    public Boolean existeGrasaSolido(String code){
        return restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/existe/" + code, Boolean.class);
    }

    public PayEntity obtenerPagoCodigo(String code){
        return payRepository.getReferenceById(code);
    }

    @Generated
    public void pagarPorId(String code) {

        SupplierModel proveedor = restTemplate.getForObject("http://Supplier-Service/proveedores/" + code, SupplierModel.class);
        GreaseAndSolidModel grasaSolido = restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/" + code, GreaseAndSolidModel.class);
        List<CollectionModel> acopios = restTemplate.getForObject("http://Collection-Service/acopios/" + code, List.class);
        RegisterModel registro = restTemplate.getForObject("http://Register-Service/registros/" + code, RegisterModel.class);

        PayEntity pago = new PayEntity();

        pago.setCode(code);
        List<CollectionModel> acopiosQuincena = restTemplate.getForObject("http://Collection-Service/acopios/", List.class);
        String quincena = restTemplate.getForObject("http://Collection-Service/acopios/quincena", String.class, acopiosQuincena);
        String categoria = restTemplate.getForObject("http://Supplier-Service/proveedores/obtener-categoria/" + code, String.class);
        String retencion = restTemplate.getForObject("http://Supplier-Service/proveedores/obtener-retencion/" + code, String.class);
        String nombre = restTemplate.getForObject("http://Supplier-Service/proveedores/obtener-nombre/" + code, String.class);

        pago.setDate(quincena);
        pago.setCategory(categoria);
        pago.setName(nombre);

        Double lecheTotal = restTemplate.getForObject("http://Collection-Service/acopios/leche-total", Double.class, acopios);
        Double diasEntregas = restTemplate.getForObject("http://Collection-Service/acopios/dias-entregas", Double.class, acopios);
        Double lechePromedio = restTemplate.getForObject("http://Collection-Service/acopios/leche-promedio", Double.class, lecheTotal);
        Double lecheRegistro = restTemplate.getForObject("http://Register-Service/registros/obtener-leche", Double.class, registro);
        Double lecheVariacion = restTemplate.getForObject("http://Register-Service/registros/variacion-leche", Double.class, lecheRegistro, lecheTotal);
        pago.setMilk(lecheTotal);
        pago.setMilkDays(diasEntregas);
        pago.setMilkAverage(lechePromedio);
        pago.setMilkChanged(lecheVariacion);

        Double grasa = restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/obtener-grasa/" + code, Double.class);
        Double grasaRegistro = restTemplate.getForObject("http://Register-Service/registros/obtener-grasa", Double.class, registro);
        Double grasaVariacion = restTemplate.getForObject("http://Register-Service/registros/variacion-grasa", Double.class, grasaRegistro, grasa);
        pago.setGrease(grasa);
        pago.setGreaseChanged(grasaVariacion);

        Double solido = restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/obtener-solido/" + code, Double.class);
        Double solidoRegistro = restTemplate.getForObject("http://Register-Service/registros/obtener-solido", Double.class, registro);
        Double solidoVariacion = restTemplate.getForObject("http://Register-Service/registros/variacion-solido", Double.class, solidoRegistro, solido);
        pago.setSolid(solido);
        pago.setSolidChanged(solidoVariacion);

        String grasaString = String.valueOf(grasa);
        String solidoString = String.valueOf(solido);
        Double pagoCategoria = restTemplate.getForObject("http://Supplier-Service/proveedores/pago-categoria/" + categoria, Double.class);
        Double pagoGrasa = restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/pago-grasa/" + grasaString, Double.class);
        Double pagoSolido = restTemplate.getForObject("http://GreaseAndSolid-Service/grasas-solidos/pago-solido/" + solidoString, Double.class);

        pago.setMilkPay(pagoCategoria * lecheTotal);
        pago.setGreasePay(pagoGrasa * lecheTotal);
        pago.setSolidPay(pagoSolido * lecheTotal);

        Double bonificacionFrecuencia = restTemplate.getForObject("http://Collection-Service/acopios/bonificacion-frecuencia", Double.class, acopios);
        Double descuentoLeche = restTemplate.getForObject("http://Register-Service/registros/descuento-leche", Double.class, pago.getMilkChanged());
        Double descuentoGrasa = restTemplate.getForObject("http://Register-Service/registros/descuento-grasa", Double.class, pago.getGreaseChanged());
        Double descuentoSolido = restTemplate.getForObject("http://Register-Service/registros/descuento-solido", Double.class, pago.getSolidChanged());

        pago.setFrecuencyBonification(bonificacionFrecuencia * pago.getMilkPay());
        pago.setMilkDiscount(descuentoLeche * pago.getMilkPay());
        pago.setGreaseDiscount(descuentoGrasa * pago.getMilkPay());
        pago.setSolidDiscount(descuentoSolido * pago.getMilkPay());

        pago.setMilkTotalPay(pagoTotalLeche(pago.getMilkPay(), pago.getGreasePay(), pago.getSolidPay(), pago.getFrecuencyBonification()));
        pago.setTotalDiscount(descuentoTotal(pago.getMilkDiscount(), pago.getGreaseDiscount(), pago.getSolidDiscount()));
        pago.setPay(calcularPago(pago.getMilkTotalPay(), pago.getTotalDiscount()));

        Double montoRetencion = restTemplate.getForObject("http://Supplier-Service/proveedores/pago-retencion/" + retencion, Double.class);
        pago.setRetention(calcularRetencion(montoRetencion, pago.getPay()));
        pago.setTotalPay(pagoTotal(pago.getRetention(), pago.getPay()));

        payRepository.save(pago);
    }
}
