
import './App.css'
import {Route,Routes} from "react-router-dom";
import IndexPage from './pages/IndexPage.jsx';
import LoginPage from './pages/LoginPage.jsx';
import RegisterPage from './pages/RegisterPage';
import Layout  from './Layout';
import axios from "axios";

axios.defaults.baseURL 

function App() {
  

  return (
    <Routes>
      <Route path="/" element={<Layout/>}>
        <Route index element={<IndexPage/>}/>
        <Route path="/login" element={<LoginPage />}/>
        <Route path='/register'element={<RegisterPage/>}/>
      </Route>
    </Routes>

  )
}

export default App
