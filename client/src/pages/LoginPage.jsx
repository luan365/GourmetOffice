import { Link, Navigate } from "react-router-dom";
import React, { useState, useContext } from "react";
import axios from "axios";
import { UserContext } from "../userContext.jsx";

export default function LoginPage() {
  const [cozinha, setCozinha] = useState(false);
  const [empresa, setEmpresa] = useState(false);
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState(""); 
  const { user, setUser } = useContext(UserContext);
  const [redirect, setRedirect] = useState(false);

  const handleCheckboxChange = (type) => {
    if (type === "cozinha") {
      setCozinha(true);
      setEmpresa(false);
    } else {
      setCozinha(false);
      setEmpresa(true);
    }
  };

  async function login(ev) {
    ev.preventDefault();
    try {
      if (empresa === false && cozinha === false) {
        alert('Escolha o tipo de perfil para logar');
        return;
      }

      if (empresa === true) {
        const { data } = await axios.put('http://localhost:4000/login/empresa', { email, senha });
        setUser(data);
        alert('Login realizado com sucesso!');
        setRedirect(true);
      }
      if (cozinha === true) {
        const { data } = await axios.put('http://localhost:4000/login/cozinha', { email, senha });
        setUser(data);
        alert('Login realizado com sucesso!');
        setRedirect(true);
      }
    } catch (e) {
      alert('Erro ao tentar fazer login. Verifique seus dados e tente novamente.');
    }
  }

  if (redirect === true) {
    return <Navigate to={'/'} />;
  }

  return (
    <div className="flex items-center justify-center min-h-screen bg-gradient-to-r from-purple-100 to-purple-200">
      <div className="w-full max-w-md bg-white p-10 rounded-2xl shadow-lg border border-gray-200">
        <h1 className="text-4xl font-semibold text-center text-purple-800 mb-6">Bem-vindo de Volta!</h1>
        <p className="text-center text-gray-600 mb-8">Entre com seu e-mail e senha para continuar.</p>
        
        <form onSubmit={login} className="space-y-6">
          <div className="flex flex-col space-y-4">
            <input
              type="email"
              placeholder="Email"
              value={email}
              onChange={(ev) => setEmail(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
            <input
              type="password"
              placeholder="Senha"
              value={senha}
              onChange={(ev) => setSenha(ev.target.value)}
              className="p-4 border border-gray-300 rounded-xl focus:outline-none focus:ring-2 focus:ring-purple-500 placeholder:text-gray-400 transition duration-200"
              required
            />
          </div>

          <div className="flex items-center justify-between">
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
            Login
          </button>

          <div className="text-center text-gray-500 py-4">
            Ainda não tem uma conta? 
            <Link to="/registerFirst" className="text-purple-600 font-semibold hover:underline">
              Registre-se agora
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
