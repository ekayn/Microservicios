import React, { useState } from 'react';
import NavbarComponent from "./NavbarComponent";
import styled from "styled-components";
import PayAddService from '../services/PayAddService';
import Form from 'react-bootstrap/Form';
import Button from 'react-bootstrap/Button';

import swal from 'sweetalert';

export default function PayAddComponent(props) {
	const initialState = {
		code: ""
	};

	const [input, setInput] = useState(initialState);

	const changeCodeHandler = event => {
		setInput({ ...input, code: event.target.value });
		console.log(input.code);
	};

	const handleSubmit = async (event) => {
		event.preventDefault();

		try {
			const respuesta = await swal({
				title: "¿Seguro de desea generar el pago?",
				text: "Una vez generado, no podrá ser modificado.",
				icon: "warning",
				buttons: ["Cancelar", "Enviar"],
				dangerMode: true
			});

			if (respuesta) {
				const res = await PayAddService.ingresarPago(input.code);

				swal("Pago generado correctamente!", { icon: "success", timer: "3000" });
				console.log("Respuesta exitosa:", res);
			} else {
				swal({ text: "Pago no generado.", icon: "error" });
			}

		} catch (error) {
			swal("Error al generar el pago.", { icon: "error" });
			console.error("Error:", error);
		}
	};

	return (

		<Styles>
			<div className="home">
				<NavbarComponent />
				<div className="mainclass">
					<div className="form1">
						<h1 className="text-center"><b>Generar pago</b></h1>
						<div className="formcontainer">
							<hr></hr>
							<div className="container">
								<Form onSubmit={handleSubmit}>
									<Form.Group className="mb-3" controlId="code">
										<Form.Label>Código del proveedor</Form.Label>
										<Form.Control
											type="code"
											name="pago"
											placeholder="Codigo del proveedor de 5 digitos"
											value={input.code}
											onChange={changeCodeHandler}
										/>
									</Form.Group>
									<Button className="boton" type="submit">Generar pago</Button>
								</Form>
							</div>
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

input[type=codigo_proveedor] {
	width: 100%;
	padding: 16px 8px;
	margin: 8px 0;
	display: inline-block;
	border: 1px solid #ccc;
	box-sizing: border-box;
}

.container{
	font-size: 20px;
	font-weight: 700;
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

.boton2 {
	background-color: #E72000;
	color: white;
	padding: 14px 0;
	margin: 10px 0;
	border: none;
	cursor: grabbing;
	width: 100%;
}

.boton2:hover {
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