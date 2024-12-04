import { useState, useEffect } from "react";
import axios from "axios";
import { FaMapMarkerAlt, FaPhoneAlt, FaEnvelope, FaClipboardList } from 'react-icons/fa'; // Para ícones

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
    <div className="bg-gradient-to-r from-purple-50 to-purple-100 min-h-screen py-16">
      <div className="container mx-auto px-6">
        <h1 className="text-5xl font-extrabold text-center text-purple-700 mb-10">Explore Nossas Cozinhas</h1>

        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-10">
          {/* Verifica se há cozinhas e exibe elas */}
          {cozinhas.length > 0 ? (
            cozinhas.map((cozinha, index) => (
              <div key={index} className="bg-white p-6 rounded-3xl shadow-lg transform hover:scale-105 hover:shadow-2xl transition-all duration-300">
                <div className="flex justify-center mb-4">
                  <div className="w-16 h-16 bg-purple-300 text-white flex items-center justify-center rounded-full">
                    <FaClipboardList size={24} />
                  </div>
                </div>
                <h3 className="text-3xl font-semibold text-purple-600 text-center">{cozinha.nome}</h3>
                <p className="text-xl text-gray-600 text-center mb-4">{cozinha.tipo}</p>

                <div className="space-y-4">
                  <div className="flex items-center justify-between">
                    <FaMapMarkerAlt className="text-purple-500" />
                    <span className="text-gray-700">{cozinha.endereco}</span>
                  </div>
                  <div className="flex items-center justify-between">
                    <FaPhoneAlt className="text-purple-500" />
                    <span className="text-gray-700">{cozinha.telefone}</span>
                  </div>
                  <div className="flex items-center justify-between">
                    <FaEnvelope className="text-purple-500" />
                    <span className="text-gray-700">{cozinha.email}</span>
                  </div>
                  <div className="flex items-center justify-between">
                    <span className="text-gray-700 font-semibold">CNPJ:</span>
                    <span className="text-gray-700">{cozinha.cnpj}</span>
                  </div>
                  <div className="flex items-center justify-between">
                    <span className="text-gray-700 font-semibold">Descrição:</span>
                    <span className="text-gray-700">{cozinha.descricao}</span>
                  </div>
                </div>

                <div className="flex justify-center mt-6">
                  <button className="bg-gradient-to-r from-purple-500 to-purple-700 text-white py-3 px-8 rounded-full text-lg hover:bg-purple-600 transform transition-all duration-200">
                    Saiba Mais
                  </button>
                </div>
              </div>
            ))
          ) : (
            <div className="col-span-3 text-center text-gray-500">Carregando cozinhas...</div>
          )}
        </div>
      </div>
    </div>
  );
}
