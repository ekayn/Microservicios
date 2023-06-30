import React from "react";
import styled from "styled-components";
import NavbarComponent from "./NavbarComponent";
import imagenes from "./assets/imagenes.js"

export default function Home(){
    
    return (
        <div className="home">
            <HomeStyle>
                <NavbarComponent />
                <div class="box-area">
                    <div class="single-box-img">
                        <img className="d-block w-6000" src={imagenes.vaca} height="600" alt="banner 1"/>
                    </div>
                </div>
                <div class="box-area">
                    <div class="single-box">
                        <a href="/subir-archivo-acopios">
                            <div class="img-area">
                            </div>
                        </a>
                        <div class="img-text">
                            <span class="header-text"><strong>Subir data acopios</strong></span>
                        </div>
                    </div>
                    <div class="single-box">
                        <a href="/subir-archivo-grasas-solidos">
                            <div class= "img-area">
                            </div>
                        </a>
                        <div class="img-text">
                            <span class="header-text"><strong>Subir data grasas-solidos</strong></span>
                        </div>
                    </div>
                </div>
            </HomeStyle>
        </div>
    );
}

const HomeStyle = styled.nav`

.box-area{
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    align-items: center;
}

.single-box{
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 400px;
    height: auto;
    border-radius: 4px;
    background-color: #fff;
    text-align: center;
    margin: 20px;
    padding: 20px;
    transition: .3s
}

.single-box-img{
    position: relative;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    width: 60px;
    height: auto;
    border-radius: 4px;
    background-color: #fff;
    text-align: center;
    margin: 20px;
    padding: 20px;
    transition: .3s
}

.img-area{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 80px;
    height: 80px;
    border: 6px solid #ddd;
    border-radius: 50%;
    padding: 20px;
    -webkit-background-size: cover;
    background-size: cover;
    background-position: center center;
}

.single-box:nth-child(1) .img-area{
    background-image: url(https://www.llaverosypinspersonalizados.com/wp-content/uploads/2015/06/entrega-a-tiempo.png)
}

.header-text{
    font-size: 23px;
    font-weight: 500;
    line-height: 48px;
}
.img-text p{
    font-size: 15px;
    font-weight: 400;
    line-height: 30px;
}
.single-box:hover{
    background: #3ADF00;
    color: #fff;}

.single-box:nth-child(2) .img-area{
        background-image: url(https://www.niter.com.uy/wp-content/uploads/2017/09/leche-en-polvo.jpeg)
}
.login-box{
    cursor: pointer;
}
`