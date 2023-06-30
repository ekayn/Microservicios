import axios from "axios";

const API_URL = "http://localhost:8080/grasas-solidos/subir-data";

class FileUploadGreaseAndSolidService{
    
    CargarArchivo(file){
        return axios.post(API_URL, file);
    }
}

export default new FileUploadGreaseAndSolidService()