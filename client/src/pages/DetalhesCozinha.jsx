import axios from "axios";
import { useParams } from "react-router-dom";
import { useState, useEffect } from "react";

export default function DetalhesCozinha() {
  const { cnpj } = useParams();
  const [cozinha, setCozinha] = useState([]);

  async function getCozinhaByCNPJ() {
    try {
      // Chama a API para obter a cozinha
      const response = await axios.post(`http://localhost:8080/getCozinhaByCNPJ`, { cnpj: cnpj });
      // Atualiza o estado com os dados recebidos
      setCozinha(response.data);
    } catch (error) {
      console.error("Erro ao buscar cozinha:", error);
    }
  }

  useEffect(() => {
    getCozinhaByCNPJ();
  }, []);

  return (
    <div className="bg-gray-100 min-h-screen py-10">
      {/* Card do perfil */}
      <div className="bg-white shadow-lg rounded-xl max-w-4xl mx-auto p-8">
        {/* Cabeçalho */}
        <div className="relative mb-6">
          {/* Foto de capa */}
          <div className="w-full h-48 bg-gradient-to-r from-purple-600 to-purple-300 rounded-xl"></div>
          {/* Ícone de utensílios de cozinha (faca e garfo cruzados) */}
          <div className="absolute top-32 left-8">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-32 h-32 text-white bg-gray-300 p-4 rounded-full shadow-md">
            <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-32 h-32 text-purple-600 bg-gray-400 p-4 rounded-full shadow-md">
              <path strokeLinecap="round" strokeLinejoin="round" d="M13.5 21v-7.5a.75.75 0 0 1 .75-.75h3a.75.75 0 0 1 .75.75V21m-4.5 0H2.36m11.14 0H18m0 0h3.64m-1.39 0V9.349M3.75 21V9.349m0 0a3.001 3.001 0 0 0 3.75-.615A2.993 2.993 0 0 0 9.75 9.75c.896 0 1.7-.393 2.25-1.016a2.993 2.993 0 0 0 2.25 1.016c.896 0 1.7-.393 2.25-1.015a3.001 3.001 0 0 0 3.75.614m-16.5 0a3.004 3.004 0 0 1-.621-4.72l1.189-1.19A1.5 1.5 0 0 1 5.378 3h13.243a1.5 1.5 0 0 1 1.06.44l1.19 1.189a3 3 0 0 1-.621 4.72M6.75 18h3.75a.75.75 0 0 0 .75-.75V13.5a.75.75 0 0 0-.75-.75H6.75a.75.75 0 0 0-.75.75v3.75c0 .414.336.75.75.75Z" />
            </svg>
            </svg>
          </div>
        </div>

        {/* Nome e descrição */}
        <div className="text-center">
          <h2 className="text-3xl font-semibold text-purple-700">{cozinha.nome}</h2>
          <p className="text-lg text-gray-600 mt-2">{cozinha.descricao || "Sem descrição disponível"}</p>
        </div>

        {/* Informações principais */}
        <div className="mt-6 space-y-4">
          <div className="flex justify-between items-center text-lg text-gray-700">
            <span className="font-semibold">CNPJ:</span>
            <span>{cozinha.cnpj}</span>
          </div>
          <div className="flex justify-between items-center text-lg text-gray-700">
            <span className="font-semibold">Email:</span>
            <span>{cozinha.email}</span>
          </div>
          <div className="flex justify-between items-center text-lg text-gray-700">
            <span className="font-semibold">Telefone:</span>
            <span>{cozinha.telefone}</span>
          </div>
          <div className="flex justify-between items-center text-lg text-gray-700">
            <span className="font-semibold">Endereço:</span>
            <span>{cozinha.endereco}</span>
          </div>
          <div className="flex justify-between items-center text-lg text-gray-700">
            <span className="font-semibold">Estados Atuantes:</span>
            <span>{Array.isArray(cozinha.estados) ? cozinha.estados.join(", ") : "Nenhum estado disponível"}</span>
          </div>
        </div>

        {/* Botão de contato */}
        <div className="mt-8 flex justify-center">
          <button className="bg-purple-600 text-white py-3 px-8 rounded-full shadow-lg hover:bg-purple-700 transition duration-300 transform hover:scale-105">
            Entrar em contato
          </button>
        </div>
      </div>

      {/* Seções adicionais (se desejado) */}
      <div className="max-w-4xl mx-auto mt-12">
        {/* Outras informações ou interações podem ser colocadas aqui, como posts, avaliações, etc. */}
        <div className="bg-white shadow-md rounded-xl p-6 mb-6">
          <h3 className="text-2xl font-semibold text-purple-700">Avaliações</h3>
          {/* Lista de avaliações ou posts */}
          <div className="mt-4">
            <p className="text-lg text-gray-600">Ainda não há avaliações.</p>
          </div>
        </div>

        {/* Outros detalhes */}
        <div className="bg-white shadow-md rounded-xl p-6">
          <h3 className="text-2xl font-semibold text-purple-700">Mais Sobre a Cozinha</h3>
          <div className="mt-4 text-lg text-gray-700">
            <p>{cozinha.descricao || "Sem descrição adicional."}</p>
          </div>
        </div>
      </div>
    </div>
  );
}
