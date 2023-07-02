import axios from "axios";

class PayAddService {
    
    ingresarPago(code){
        return axios.post("http://localhost:8080/pagos/" + code);
    }
}

export default new PayAddService();