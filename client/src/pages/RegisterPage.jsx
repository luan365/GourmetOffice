import { Link } from "react-router-dom";
import React from "react";
import axios from "axios";
import { useState } from "react";

export default function RegisterPage() {
  const [email, setEmail] = useState("");
  const [nome, setNome] = useState("");
  const [senha, setSenha] = useState("");
  const [senhaConfirma, setSenhaConfirma] = useState("");
  const [endereco, setEndereco] = useState("");
  const [cnpj, setCnpj] = useState("");
  const [telefone, setTelefone] = useState("");
  const [cozinha, setCozinha] = useState(false);
  const [empresa, setEmpresa] = useState(false);
  async function registerUser(ev){
    //VALIDATIONS

    if (!cozinha && !empresa) {
      alert("Por favor, selecione uma opção: Cozinha ou Empresa.");
      return;
    }

    if(senha!=senhaConfirma){
      alert("Senhas não correspondem");
      return;
    }

    if(senha.length<8){
      alert("Senha precisa ter no minimo 8 digitos");
      return;
    }

    if(cnpj.length!=14){
      alert("CNPJ inválido");
      return;
    }

    if(nome.length==0){
      alert("Preencha seu nome");
      return;
    }

    if(email.length==0){
      alert("Preencha seu email");
      return;
    }

    if(telefone.length==0){
      alert("Preencha seu telefone");
      return;
    }

    if(endereco.length==0){
      alert("Preencha seu endereço");
      return;
    }




    ev.preventDefault();
    try{

      if(cozinha==true){
        await axios.put('http://localhost:8080/insertCozinha',{
          nome,
          senha,
          email,
          endereco,
          cnpj,
          telefone,
        });
        alert('registro deu certo');
      }

      if(empresa==true){
        await axios.put('http://localhost:8080/insertEmpresa',{
          nome,
          senha,
          email,
          endereco,
          cnpj,
          telefone,
        });
        alert('registro deu certo');
      }


    }catch(e){
      alert('registro falhou' + e)//adicionar possiveis erros, esse email já esta em uso por exemplo
    }
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
          <input type="password"
           placeholder="confirme sua senha"
           value={senhaConfirma}
           onChange={(ev)=>setSenhaConfirma(ev.target.value)}
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
          <div className="flex items-center space-x-4 mt-4">
            <label>
              <input
                type="checkbox"
                checked={cozinha}
                onChange={() => handleCheckboxChange("cozinha")}
              />
              Cozinha
            </label>
            <label>
              <input
                type="checkbox"
                checked={empresa}
                onChange={() => handleCheckboxChange("empresa")}
              />
              Empresa
            </label>
          </div>
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

