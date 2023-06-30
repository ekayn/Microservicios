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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
        RegisterModel registro = restTemplate.getForObject("http://Register-Service/registros/" + code, RegisterModel.class);

        Double bonificacionFrecuencia = restTemplate.getForObject("http://Collection-Service/acopios/bonificacion-frecuencia/" + code, Double.class);
        Double lecheTotal = restTemplate.getForObject("http://Collection-Service/acopios/leche-total/" + code, Double.class);
        String quincena = restTemplate.getForObject("http://Collection-Service/acopios/quincena", String.class);
        Double lechePromedio = restTemplate.getForObject("http://Collection-Service/acopios/leche-promedio/" + lecheTotal, Double.class);
        Double diasEntregas = restTemplate.getForObject("http://Collection-Service/acopios/dias-entregas/" + code, Double.class);

        PayEntity pago = new PayEntity();

        pago.setCode(code);

        String categoria = proveedor.getCategory();
        String retencion = proveedor.getRetention();
        String nombre = proveedor.getName();

        pago.setDate(quincena);
        pago.setCategory(categoria);
        pago.setName(nombre);

        Double lecheRegistro = registro.getMilk();
        Double lecheVariacion = restTemplate.getForObject("http://Register-Service/registros/variacion-leche", Double.class, lecheRegistro, lecheTotal);
        pago.setMilk(lecheTotal);
        pago.setMilkDays(diasEntregas);
        pago.setMilkAverage(lechePromedio);
        pago.setMilkChanged(lecheVariacion);

        Double grasa = grasaSolido.getGrease();
        Double grasaRegistro = registro.getGrease();
        Double grasaVariacion = restTemplate.getForObject("http://Register-Service/registros/variacion-grasa/" + grasaRegistro + "/" + grasa, Double.class);
        pago.setGrease(grasa);
        pago.setGreaseChanged(grasaVariacion);

        Double solido = grasaSolido.getSolid();
        Double solidoRegistro = registro.getSolid();
        Double solidoVariacion = restTemplate.getForObject("http://Register-Service/registros/variacion-solido/" + solidoRegistro + "/" +  solido, Double.class);
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
