import React, { useState  } from "react";
import NavbarComponent from "./NavbarComponent";
import styled from "styled-components";
import SupplierAddService from "../services/SupplierAddServiceService";
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';
import swal from 'sweetalert';

export default function SupplierAddComponent(props){

    const initialState = {
        code: "",
        name: "",
        category: "",
        retention: ""
    };

    const [input, setInput] = useState(initialState);
    
    const changeCodeHandler = event => {
        setInput({ ...input, code: event.target.value });
        console.log(input.code);
    };
    const changeNameHandler = event => {
        setInput({ ...input, name: event.target.value });
        console.log(input.name);
    };
    const changeCategoryHandler = event => {
        setInput({ ...input, category: event.target.value });
        console.log(input.category);
    };
    const changeRetentionHandler = event => {
        setInput({ ...input, retention: event.target.value });
        console.log(input.retention);
    };
    
    const ingresar = e => {
        e.preventDefault();
        swal({
            title: "¿Está seguro de que desea registrar al proveedor?",
            text: "Una vez registrado, no podrá ser modificado. Se le asignará un % de grasas y solidos por defecto.",
            icon: "warning",
            buttons: ["Cancelar", "Enviar"],
            dangerMode: true
        }).then(respuesta=>{
            if(respuesta){
                swal("Proveedor registrado correctamente!", {icon: "success", timer: "3000"});
                let proveedor = { code: input.code, name: input.name, category: input.category, retention: input.retention};
                console.log(input.code)
                console.log(input.name)
                console.log(input.category)
                console.log(input.retention)
                console.log("proveedor => " + JSON.stringify(proveedor));
                SupplierAddService.ingresarProveedor(proveedor).then(
                    (res) => {
                    }
                  );
                }
            else{
                swal({text: "Proveedor no registrado.", icon: "error"});
            }
        });
    };

    return(
            
            <Styles>
            <div className="home">
                <NavbarComponent />
                    <div className="mainclass">
                        <div className="form1">
                            <h1 className="text-center"><b>Proveedores</b></h1>
                            <div className="formcontainer">
                                <hr></hr>
                                <div className="container">
                                    <Form>
                                        <Form.Group className="mb-3" controlId="code" value = {input.code} onChange={changeCodeHandler}>
                                            <Form.Label>Código del proveedor</Form.Label>
                                            <Form.Control type="code" placeholder="Ingrese solo enteros, tiene que tener 5 dígitos. Ej: 12305." />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="name" value = {input.name} onChange={changeNameHandler}>
                                            <Form.Label>Nombre del proveedor</Form.Label>
                                            <Form.Control type="name" placeholder="Ingrese nombre del proveedor. Ej: EmpresaGenerica" />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="category" value = {input.category} onChange={changeCategoryHandler}>
                                            <Form.Label>Categoria del proveedor</Form.Label>
                                            <Form.Control type="category" placeholder="Ingrese: A, B, C o D" />
                                        </Form.Group>

                                        <Form.Group className="mb-3" controlId="retention" value = {input.retention} onChange={changeRetentionHandler}>
                                            <Form.Label>Retención del proveedor</Form.Label>
                                            <Form.Control type="retention" placeholder="Ingrese si el proveedor posee rentención. Si o No" />
                                        </Form.Group>
                                    </Form>
                                </div>
                                <Button className="boton" onClick={ingresar}>Registrar proveedor</Button>
                            </div>
                        </div>
                    </div>
                
            </div>
            </Styles>
        )
    }


const Styles = styled.div`

.text-center {
    text-align: center;
    justify-content: center;
    padding-top: 8px;
    font-family: "Arial Black", Gadget, sans-serif;
    font-size: 30px;
    letter-spacing: 0px;
    word-spacing: 2px;
    color: #000000;
    font-weight: 700;
    text-decoration: none solid rgb(68, 68, 68);
    font-style: normal;
    font-variant: normal;
    text-transform: uppercase;
}

.home{
    background-color: #006992;
    margin: 0;
    padding: 0;
}

.mainclass{
    margin-top: 20px;
    display: flex;
    justify-content: center;
    font-family: Roboto, Arial, sans-serif;
    font-size: 15px;
}

.form1{
    border: 9px solid #CED0CE;
    background-color: #DADDD8;
    width: 50%;
    padding: 36px;
}

input[type=rut], input[type=fecha] {
    width: 100%;
    padding: 16px 8px;
    margin: 8px 0;
    display: inline-block;
    border: 1px solid #ccc;
    box-sizing: border-box;
}

Button {
    background-color: #42bfbb;
    color: white;
    padding: 14px 0;
    margin: 10px 0;
    border: none;
    cursor: grabbing;
    width: 100%;
}

Button:hover {
    opacity: 0.8;
}

.formcontainer {
    text-align: left;
    margin: 24px 100px 9px;
}

.container {
    padding: 24px 0;
    text-align:left;
}

span.psw {
    float: right;
    padding-top: 0;
    padding-right: 15px;
}
`