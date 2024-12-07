import {useContext,useState} from "react";
import {UserContext} from "../userContext.jsx";
import { Navigate } from "react-router-dom";
import axios from "axios";

export default function EmpresaAccontPage(){
const {ready,user,setUser} = useContext(UserContext);
const {redirect,setRedirect} = useState(null);
const [cnpj, setCnpj] = useState(""); 


async function logout(){
    await axios.put('http://localhost:4000/logout');
    alert("saindo...")
    setUser(null);
    setRedirect('/')
    console.log('redirect :', redirect)
}
async function deletar() {
  
      // Passando o CNPJ no corpo da requisição como um objeto JSON
      const response = await axios.delete('http://localhost:8080/deleteCozinha', {
        data: {
          cnpj: user.cnpj // Passando o CNPJ no corpo da requisição
        }
      });
      if (response.status === 200) {
        alert("Conta excluída com sucesso");
        setUser(null);
        setRedirect('/');
      } else {
        alert("Erro ao excluir conta. Tente novamente.");
      }
     
  
}


if(!ready){
    console.log(ready,'ready aqui')
    return 'carregando...';
}

if(ready && !user){ //verifica se há um usuario logado e verifica se 
    console.log('ready:',ready,'user',user)
    return <Navigate to={'/login'}/>
}

if(redirect){
    return <Navigate to={redirect}/>
  }
return (
    <div className="bg-gray-100 min-h-screen flex flex-col items-center py-10">
      <div className="bg-purple-100 p-6 rounded-lg shadow-lg w-full max-w-md">
        <div className="flex flex-col items-center mb-6">
          <div className="mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-16 h-16 text-gray-500">
              <path strokeLinecap="round" strokeLinejoin="round" d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
            </svg>
          </div>
          <h2 className="text-2xl font-bold text-gray-800">{user.nome} JOIJOJOJOJ</h2>
        </div>

        <div className="space-y-4 text-left">
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">CNPJ:</span>
            <span>{user.cnpj}</span>
          </div>
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">Email:</span>
            <span>{user.email}</span>
          </div>
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">Senha:</span>
            <span>{user.senha}</span>
          </div>
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">Telefone:</span>
            <span>{user.telefone}</span>
          </div>
          <div className="flex justify-between" >
            <span className="font-semibold text-gray-600">Endereço:</span>
            <span>{user.endereco}</span>
          </div>
          <div>
            <button onClick={logout} className="text-white">Sair</button>
            <button onClick={deletar} className="text-white bg-red-500 mt-3">Excluir conta</button>
          </div>
        </div>
      </div>
    </div>
  );
}