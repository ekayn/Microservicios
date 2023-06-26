import axios from "axios";

const API_URL = "http://localhost:8080/acopios/subir-data";

class FileUploadCollectionService{
    
    CargarArchivo(file){
        return axios.post(API_URL, file);
    }
}

export default new FileUploadCollectionService()