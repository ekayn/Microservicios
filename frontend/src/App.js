import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent.jsx';

import CollectionImportComponent from './components/CollectionImportComponent';
import CollectionListComponent from './components/CollectionListComponent';
import GreaseAndSolidImportComponent from './components/GreaseAndSolidImportComponent';
import GreaseAndSolidListComponent from './components/GreaseAndSolidListComponent';
import RegisterListComponent from './components/RegisterListComponent';
import SupplierAddComponent from './components/SupplierAddComponent';
import SupplierListComponent from './components/SupplierListComponent';
import PayListComponent from './components/PayListComponent';

/*
import PayAddComponent from './components/PayAddComponent';


          <Route path= "/pagar" element={<PayAddComponent />} />
*/

function App() {
  return (
    <div>
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<HomeComponent />} />
          <Route path= "/subir-archivo-acopios" element={<CollectionImportComponent />} />
          <Route path= "/listar-acopios" element={<CollectionListComponent />} />         
          <Route path= "/subir-archivo-grasas-solidos" element={<GreaseAndSolidImportComponent />} />                   
          <Route path= "/listar-grasas-solidos" element={<GreaseAndSolidListComponent />} />
          <Route path= "/listar-registros" element={<RegisterListComponent />} />
          <Route path= "/agregar-proveedor" element={<SupplierAddComponent />} />
          <Route path= "/listar-proveedores" element={<SupplierListComponent />} />
          <Route path= "/listar-pagos" element={<PayList1Component />} />


                            
          
          
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
