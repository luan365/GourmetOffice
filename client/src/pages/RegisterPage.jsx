import { Link } from "react-router-dom";
import React from "react";
import axios from "axios";
import { useState } from "react";

export default function RegisterPage() {
  const [email, setEmail] = useState("");
  const [nome, setNome] = useState("");
  const [senha, setSenha] = useState("");
  const [endereco, setEndereco] = useState("");
  const [cnpj, setCnpj] = useState("");
  const [telefone, setTelefone] = useState("");
  async function registerUser(ev){
    ev.preventDefault();
    try{
      await axios.put('http://localhost:8080/insertEmpresa',{
        nome,
        senha,
        email,
        endereco,
        cnpj,
        telefone,
      });
      alert('registro deu certo');
    }catch(e){
      alert('registro falhou')//adicionar possiveis erros, esse email já esta em uso por exemplo
    }
  }
  return (
    <div className="mt-4 grow flex items-center justify-around mt-20">
      <div className="mb-64">
        <h1 className="text-4xl text-center">Registra-se</h1>
        <form className="max-w-md mx-auto" onSubmit={registerUser}>
          <input
            type="text"
            placeholder="nome"
            title="Apenas letras são permitidas"
            value={nome}
            onChange={(ev)=>setNome(ev.target.value)}
          />
          <input 
          type="email" 
          placeholder="your@rmail.com"
          value={email}
          onChange={(ev)=>setEmail(ev.target.value)}
          />
          <input type="password"
           placeholder="senha"
           value={senha}
           onChange={(ev)=>setSenha(ev.target.value)}
            />
          <input type="text"
           placeholder="cnpj" 
           pattern="\d*" 
           maxLength={14}
           value={cnpj}
           onChange={(ev)=>setCnpj(ev.target.value)}
           />
          <input
            type="text"
            placeholder="tel:(xx)xxxxx-xxxx"
            pattern="\d*"
            maxLength={11}
            value={telefone}
            onChange={(ev)=>setTelefone(ev.target.value)}
          />
          <input type="text" 
           placeholder="Endereço" 
           value={endereco}
           onChange={(ev)=>setEndereco(ev.target.value)}
          />
          <button className="text-white">Registrar</button>
          <div className="text-center py-2">
            Já possui uma conta?
            <Link className="underline font-bold" to={"/login"}>
              entre em Login
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
