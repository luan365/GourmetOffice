import { Link } from "react-router-dom";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import axios from "axios";

export default function RegisterPage() {

  const [cozinha, setCozinha] = useState(false);
  const [empresa, setEmpresa] = useState(false);
 
  const navigate = useNavigate();

  async function registerUser(ev) {
    ev.preventDefault();

    // Validações
    if (!cozinha && !empresa) {
      alert("Por favor, selecione uma opção: Cozinha ou Empresa.");
      return;
    }

    if(cozinha){
      navigate("/registerCozinha");
    }

    if(empresa){
      navigate("/registerEmpresa");

    }


    ev.preventDefault();

  }

  const handleCheckboxChange = (type) => {
    if (type === "cozinha") {
      setCozinha(true);
      setEmpresa(false);
      
    } else {
      setCozinha(false);
      setEmpresa(true);
      
    }
  };

  return (
    <div className="min-h-screen flex items-center justify-center bg-gradient-to-r from-purple-100 to-purple-300">
      <div className="w-full max-w-md bg-white p-10 rounded-2xl shadow-lg border border-gray-200">
        <h1 className="text-4xl font-semibold text-center text-purple-800 mb-6">Crie sua Conta</h1>
        <p className="text-center text-gray-600 mb-8">Escolha uma das duas opções</p>

        <form onSubmit={registerUser} className="space-y-6">


          <div className="flex items-center justify-between mt-4">
            <div className="flex items-center space-x-4">
              <label className="flex items-center text-sm text-gray-600">
                <input
                  type="checkbox"
                  checked={cozinha}
                  onChange={() => handleCheckboxChange("cozinha")}
                  className="form-checkbox text-purple-600"
                />
                <span className="ml-2">Cozinha</span>
              </label>
              <label className="flex items-center text-sm text-gray-600">
                <input
                  type="checkbox"
                  checked={empresa}
                  onChange={() => handleCheckboxChange("empresa")}
                  className="form-checkbox text-purple-600"
                />
                <span className="ml-2">Empresa</span>
              </label>
            </div>
          </div>



          <button
            type="submit"
            className="w-full bg-purple-600 text-white py-3 rounded-xl text-lg hover:bg-purple-700 focus:outline-none transition duration-300"
          >
            Continuar
          </button>

          <div className="text-center text-gray-500 py-4">
            Já tem uma conta? 
            <Link to="/login" className="text-purple-600 font-semibold hover:underline">
              Faça Login
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
