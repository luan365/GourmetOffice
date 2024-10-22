
import './App.css'
import {Route,Routes} from "react-router-dom";
import IndexPage from './pages/IndexPage.jsx';
import LoginPage from './pages/LoginPage.jsx';


function App() {
  

  return (
    <Routes>
      <Route index element={<IndexPage/>}/>
      <Route path="/login" element={<LoginPage />}/>
    </Routes>

  )
}

export default App
