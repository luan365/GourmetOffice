
import './App.css'
import {Route,Routes} from "react-router-dom";
import IndexPage from './pages/IndexPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import DetalhesCozinha from './pages/DetalhesCozinha.jsx';
import RegisterCozinha from './pages/RegisterCozinha';
import RegisterEmpresa from './pages/RegisterEmpresa';
import RegisterPageChoose from './pages/RegisterPageChoose';
import {UserContextProvider} from "./userContext"
import Layout  from './Layout';
import axios from "axios";
import AccontEmpresaPage from './pages/EmpresaAccontPage';
import AccontCozinhaPage from './pages/CozinhaAccontPage';


axios.defaults.baseURL = 'http://localhost:4000';// especificando rota padrao
axios.defaults.withCredentials = true;
function App() {
  

  return (
    <UserContextProvider>
      <Routes>
        <Route path="/" element={<Layout/>}>
          <Route index element={<IndexPage/>}/>
          <Route path="/login" element={<LoginPage />}/>
          <Route path="/detalhesCozinha/:cnpj" element={<DetalhesCozinha />}/>
          <Route path='/registerCozinha'element={<RegisterCozinha/>}/>
          <Route path='/registerEmpresa'element={<RegisterEmpresa/>}/>
          <Route path='/registerFirst'element={<RegisterPageChoose/>}/>
          <Route path='/Accont/Empresa'element={<AccontEmpresaPage/>}/>
          <Route path='/Accont/Cozinha'element={<AccontCozinhaPage/>}/>
        </Route>
      </Routes>
    </UserContextProvider>
  )
}

export default App


/**
 *  Por padrão, o axios assume que as requisições são feitas para o mesmo domínio e porta do front-end. 
 * Caso o servidor esteja 
 * rou seja o servidor esta em uma porta diferente(4000), por isso ele nao achava a ropta \profile:
 */