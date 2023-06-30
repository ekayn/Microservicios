import React from "react";
import styled from "styled-components";

function NavbarComponent(){
    return(
        <>
            <NavStyle>
                <header class="header">
                <div class="logo">
                    <h1>Milk Stg</h1>
                </div>
                <nav>
                </nav>
                <a class="btn" href="/"><button>Menú principal</button></a>
                <a class="btn-2" href="/listar-proveedores"><button>Proveedores</button></a>
                <a class="btn-2" href="/agregar-proveedor"><button>Agregar Proveedor</button></a>
                <a class="btn-2" href="/listar-acopios"><button>Acopios</button></a>
                <a class="btn-2" href="/listar-grasas-solidos"><button>Grasas y solidos</button></a>
                <a class="btn-2" href="/listar-registros"><button>Registros</button></a>
                <a class="btn-2" href="/listar-pagos"><button>Pagos</button></a>
                <a class="btn-2" href="/agregar-pago"><button>Agregar pago</button></a>
            </header>
            </NavStyle>
        </>
    )
}

export default NavbarComponent;

const NavStyle = styled.nav`
.header{
    background-color: #1b3039;
    display: flex;
    justify-content: flex-end;
    align-items: center;
    height: 85px;
    padding: 5px 10%;
  }
.header .logo{
    margin-right: auto;
    color: white;
    font-family: 'Pacifico',serif;
  }
.header .btn button{
    margin-left: 20px;
    font-weight: 700;
    color: #1b3039;
    padding: 9px 25px;
    background: #eceff1;
    border: none;
    border-radius: 50px;
    cursor: pointer;
    transition: all 0.3s ease 0s;
  }
.header .btn button:hover{
    background-color: #e2f1f8;
    color: #ffbc0e;
    transform: scale(1.1);
  }
.header .btn-2 button {
    margin-left: 20px;
    font-weight: 700;
    color: #1b3039;
    padding: 9px 25px;
    background: #eceff1;
    border: none;
    border-radius: 50px;
    cursor: pointer;
    transition: all 0.3s ease 0s;
  }
.header .btn-2 button:hover{
    background-color: #e2f1f8;
    color: #ffbc0e;
    transform: scale(1.1);
}
`