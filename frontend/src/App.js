import { BrowserRouter, Route, Routes } from "react-router-dom";
import HomeComponent from './components/HomeComponent.jsx';

import CollectionImportComponent from './components/CollectionImportComponent';
import CollectionListComponent from './components/CollectionListComponent';
import GreaseAndSolidImportComponent from './components/GreaseAndSolidImportComponent';
import GreaseAndSolidListComponent from './components/GreaseAndSolidListComponent';
import RegisterListComponent from './components/RegisterListComponent';
import SupplierAddComponent from './components/SupplierAddComponent';
import SupplierListComponent from './components/SupplierListComponent';

/*
import PayAddComponent from './components/PayAddComponent';
import PayList1Component from './components/PayList1Component';
import PayList2Component from './components/PayList2Component';


          <Route path= "/pagar" element={<PayAddComponent />} />
          <Route path= "/listar-pagos-1" element={<PayList1Component />} />
          <Route path= "/listar-pagos-2" element={<PayList2Component />} />
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


                            
          
          
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
