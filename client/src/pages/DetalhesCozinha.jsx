
import axios from "axios";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";


export default function EmpresaAccontPage(){
  const { cnpj } = useParams();
  const [cozinha, setCozinha] = useState([]);
  async function getCozinhaByCNPJ() {
    try {
      // Chama a API para obter as cozinhas
      const response = await axios.post(`http://localhost:8080/getCozinhaByCNPJ`,{cnpj:cnpj});
      // Atualiza o estado com os dados recebidos
      setCozinha(response.data);
    } catch (error) {
      console.error("Erro ao buscar cozinhas:", error);
    }
  }
   





  useEffect(() => {
    getCozinhaByCNPJ();
    console.log(cozinha)
  }, []);

return (
    <div className="bg-gray-100 min-h-screen flex flex-col items-center py-10">
      <div className="bg-purple-100 p-6 rounded-lg shadow-lg w-full max-w-md">
        <div className="flex flex-col items-center mb-6">
          <div className="mb-4">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-16 h-16 text-gray-500">
              <path strokeLinecap="round" strokeLinejoin="round" d="M17.982 18.725A7.488 7.488 0 0 0 12 15.75a7.488 7.488 0 0 0-5.982 2.975m11.963 0a9 9 0 1 0-11.963 0m11.963 0A8.966 8.966 0 0 1 12 21a8.966 8.966 0 0 1-5.982-2.275M15 9.75a3 3 0 1 1-6 0 3 3 0 0 1 6 0Z" />
            </svg>
          </div>
          <h2 className="text-2xl font-bold text-gray-800">{cozinha.nome} </h2>
        </div>

        <div className="space-y-4 text-left">
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">CNPJ:</span>
            <span>{cozinha.cnpj}</span>
          </div>
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">Email:</span>
            <span>{cozinha.email}</span>
          </div>
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">Senha:</span>
            <span>{cozinha.senha}</span>
          </div>
          <div className="flex justify-between">
            <span className="font-semibold text-gray-600">Telefone:</span>
            <span>{cozinha.telefone}</span>
          </div>
          <div className="flex justify-between" >
            <span className="font-semibold text-gray-600">Endereço:</span>
            <span>{cozinha.endereco}</span>
          </div>
          <div className="flex justify-between" >
            <span className="font-semibold text-gray-600">Descrição:</span>
            <span>{cozinha.descricao}</span>
          </div>
          <div className="flex justify-between" >
            <span className="font-semibold text-gray-600">Estados atuantes:</span>
            <span>{cozinha.estados.join(", ")}</span>
          </div>
        </div>
      </div>
    </div>
  );
}