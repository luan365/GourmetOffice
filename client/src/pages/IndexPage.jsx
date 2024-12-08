import { useState, useEffect } from "react";
import axios from "axios";
import { FaMapMarkerAlt, FaPhoneAlt, FaEnvelope, FaClipboardList } from 'react-icons/fa'; // Para ícones
import { useNavigate } from "react-router-dom";
import { use } from "react";

export default function IndexPage() {
  // Estado para armazenar as cozinhas
  const [cozinhas, setCozinhas] = useState([]);
  const [cozinhasPesquisadas, setCozinhasPesquisadas] = useState([]);
  

  const [pesquisaEstado, setPesquisaEstado] = useState("");
  const [pesquisaNota, setPesquisaNota] = useState("");
  const navigate = useNavigate();

  // Função para buscar as cozinhas
  async function getCozinha() {
    try {
      // Chama a API para obter as cozinhas
      const response = await axios.get('http://localhost:8080/getAllCozinhas');
      // Atualiza o estado com os dados recebidos
      setCozinhas(response.data);
      setCozinhasPesquisadas(response.data);
    } catch (error) {
      console.error("Erro ao buscar cozinhas:", error);
    }
  }

  function concatTelefone(cozinha){
    let ret = ""
    for(let i=0;i<cozinha.telefone.length;i++){
      if(i>1){
        ret += cozinha.telefone[i]
      }
    }
    

    return "+55" + " " + cozinha.telefone[0] + cozinha.telefone[1] + " " +ret 
  }

  function limpar(){

  }

   async function pesquisar(){
    var placeholder = [];
    var tipoPesquisa;

    if(pesquisaEstado.length!=0){
      tipoPesquisa = 1;
    }
    if(pesquisaNota.length!=0){
      tipoPesquisa = 2;
    }
    if(pesquisaEstado.length!=0 && pesquisaNota.length!=0){
      tipoPesquisa = 3;
    }

    switch (tipoPesquisa){
      case 1:
        placeholder = [];
        cozinhas.forEach(cozinha=>{
          for(var i = 0; i<cozinha.estados.length; i++){       
            if(cozinha.estados[i].toUpperCase() == pesquisaEstado.toUpperCase()){
              placeholder.push(cozinha)
            }          
          }
        })
        setCozinhasPesquisadas(placeholder);
        console.log(cozinhasPesquisadas)
        break;
        
      case 2:
        placeholder = [];
        cozinhas.forEach(cozinha=>{
          const notaCozinha = cozinha.notas.reduce((acc, nota) => acc + nota, 0) /cozinha.notas.length
          console.log(pesquisaNota);
          console.log(notaCozinha)
          if(cozinha.notas.length >0){

            if(notaCozinha>=pesquisaNota){
              placeholder.push(cozinha)
            }

          }
          
        })
        setCozinhasPesquisadas(placeholder);
        break;

      case 3:
        placeholder = [];

        cozinhas.forEach(cozinha=>{
          const notaCozinha = cozinha.notas.reduce((acc, nota) => acc + nota, 0) /cozinha.notas.length
          for(var i = 0; i<cozinha.estados.length; i++){       
            if(cozinha.estados[i].toUpperCase() == pesquisaEstado.toUpperCase()){
              if(cozinha.notas.length >0){

                if(notaCozinha>=pesquisaNota){
                  placeholder.push(cozinha)
                }
    
              }
             
            }          
          }
        })

        setCozinhasPesquisadas(placeholder);
        break;

      default:
        setCozinhasPesquisadas(cozinhas);
        break;
    }

    

  }

  // UseEffect para buscar as cozinhas quando o componente for montado
  useEffect(() => {
    getCozinha();
  }, []); // A lista vazia [] significa que a chamada será feita uma vez após o primeiro render

  return (
    <div className="bg-gradient-to-r from-purple-50 to-purple-100 min-h-screen py-16">
      <div className="container mx-auto px-6">
        <h1 className="text-5xl font-extrabold text-center text-purple-700 mb-10">Explore Nossas Cozinhas</h1>


        <div className="flex justify-center items-center w-full">
          
        <div className="w-2/3 max-w-lg flex justify-center py-4">
        <div className="flex gap-2 border border-gray-500 rounded-full py-1 px-4 shadow-md shadow-black-300 w-full">

          <div className="flex gap-2 border border-gray-300 rounded-full py-0.5 px-4 shadow-md shadow-black-300 w-full items-center">
            <input 
              type="text" 
              placeholder="Estado" 
              className="px-2 py-1 text-sm outline-none border-none bg-transparent"
              value={pesquisaEstado}
              onChange={(ev) => setPesquisaEstado(ev.target.value)}
            />
            <div className="border border-purple-150 h-4"></div>
            <input 
              type="text" 
              placeholder="Avaliação" 
              className="px-2 py-1 text-sm outline-none border-none bg-transparent"
              value={pesquisaNota}
              onChange={(ev) => setPesquisaNota(ev.target.value)}
            />
          </div>

          <div className="border border-purple-150"></div>
            <button className=" flex flex-row items-center justify-center bg-purple-700 text-white p-1 rounded-full gap-1" onClick={pesquisar}>
              <svg xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24" strokeWidth="1.5" stroke="currentColor" className="w-5 h-5">
                <path strokeLinecap="round" strokeLinejoin="round" d="m21 21-5.197-5.197m0 0A7.5 7.5 0 1 0 5.196 5.196a7.5 7.5 0 0 0 10.607 10.607Z" />
              </svg>
              <div>pesquisar</div>
            </button>
           

        </div>
      </div>



      </div>
        {/* Div das cozinhas cadastradas */}
        <div className="grid grid-cols-1 sm:grid-cols-2 lg:grid-cols-3 gap-10">
          {cozinhasPesquisadas.length > 0 ? (
            cozinhasPesquisadas.map((cozinha, index) => (
              <div key={index} className="bg-white p-6 rounded-3xl shadow-lg transform hover:scale-105 hover:shadow-2xl transition-all duration-300">
                <div className="flex justify-center mb-4">
                  <div className="w-16 h-16 bg-purple-300 text-white flex items-center justify-center rounded-full">
                    <FaClipboardList size={24} />
                  </div>
                </div>
                <h3 className="text-3xl font-semibold text-purple-600 text-center">{cozinha.nome}</h3>
                <p className="text-xl text-gray-600 text-center mb-4">{
                cozinha.notas && cozinha.notas.length > 0 ? `Avaliações(${cozinha.notas.length}): ${(cozinha.notas.reduce((acc, nota) => acc + nota, 0) /cozinha.notas.length).toFixed(2)}`
                : "Sem Avaliações"}</p>

                <div className="space-y-4">
                  <div className="flex items-center justify-between">
                    <FaMapMarkerAlt className="text-purple-500" />
                    <span className="text-gray-700">{cozinha.endereco}</span>
                  </div>
                  <div className="flex items-center justify-between">
                    <FaPhoneAlt className="text-purple-500" />
                    <span className="text-gray-700"> {concatTelefone(cozinha)}</span>
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
                  <div className="flex items-center justify-between">
                    <span className="text-gray-700 font-semibold">Estados atuantes :</span>
                    <span className="text-gray-700">{cozinha.estados.join(', ')}</span>
                  </div>
                </div>

                <div className="flex justify-center mt-6">
                  <button 
                  onClick={()=>navigate(`/detalhesCozinha/${cozinha.cnpj}`)}
                  className="bg-gradient-to-r from-purple-500 to-purple-700 text-white py-3 px-8 rounded-full text-lg hover:bg-purple-600 transform transition-all duration-200">
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
