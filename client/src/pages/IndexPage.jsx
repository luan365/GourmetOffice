import { useState, useEffect } from "react";
import axios from "axios";

export default function IndexPage() {
  // Estado para armazenar as cozinhas
  const [cozinhas, setCozinhas] = useState([]);

  // Função para buscar as cozinhas
  async function getEmpresa() {
    try {
      // Chama a API para obter as cozinhas
      const response = await axios.get('http://localhost:8080/getAllCozinhas');
      // Atualiza o estado com os dados recebidos
      setCozinhas(response.data);
    } catch (error) {
      console.error("Erro ao buscar cozinhas:", error);
    }
  }

  // UseEffect para buscar as cozinhas quando o componente for montado
  useEffect(() => {
    getEmpresa();
  }, []); // A lista vazia [] significa que a chamada será feita uma vez após o primeiro render

  return (
    <div className="bg-gray-100 min-h-screen flex flex-col items-center py-10">
      <div className="bg-purple-100 p-6 rounded-lg shadow-lg w-full max-w-md">
        <div className="flex flex-col items-center mb-6">
          <h2 className="text-2xl font-bold text-gray-800">Lista de Cozinhas</h2>
        </div>

        <div className="space-y-4 text-left">
          {/* Verifica se há cozinhas e exibe elas */}
          {cozinhas.length > 0 ? (
            cozinhas.map((cozinha, index) => (
              <div key={index} className="bg-white p-4 rounded-lg shadow-md">
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-600">Nome:</span>
                  <span>{cozinha.nome}</span>
                </div>
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-600">CNPJ:</span>
                  <span>{cozinha.cnpj}</span>
                </div>
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-600">Email:</span>
                  <span>{cozinha.email}</span>
                </div>
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-600">Telefone:</span>
                  <span>{cozinha.telefone}</span>
                </div>
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-600">Endereço:</span>
                  <span>{cozinha.endereco}</span>
                </div>
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-600">Descrição:</span>
                  <span>{cozinha.descricao}</span>
                </div>
                <div className="flex justify-between">
                  <span className="font-semibold text-gray-600">Tipo:</span>
                  <span>{cozinha.tipo}</span>
                </div>
              </div>
            ))
          ) : (
            <p>Carregando cozinhas...</p>
          )}
        </div>
      </div>
    </div>
  );
}

    
