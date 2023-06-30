import axios from "axios";

class SupplierAddServiceService {
    
    ingresarProveedor(proveedor){
        return axios.post(`http://localhost:8080/proveedores`, proveedor);
    }
}

export default new SupplierAddServiceService();