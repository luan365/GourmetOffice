import { Link } from "react-router-dom";
import React from "react";
import axios from "axios";
import { useState } from "react";

export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState(""); 
  async function login(ev){
        ev.preventDefault()
        try{
          await axios.put('http://localhost:4000/login',{
           email,
           senha
          });
          alert('login deu certo deu certo');
        }catch(e){
          alert('registro falhou')//adicionar possiveis erros, esse email já esta em uso por exemplo
        }
    }
  return (
    <div className="mt-4 grow flex items-center justify-around mt-20">
      <div className="mb-72">
        <h1 className="text-4xl text-center">Login</h1>
        <form className="max-w-md mx-auto " onSubmit={login}>

        <input
          type="email"
          placeholder="your@email.com"
          value={email}
          onChange={(ev)=>setEmail(ev.target.value)}
          />

          <input
           type="password"
           placeholder="password"
           value={senha}
           onChange={(ev)=>setSenha(ev.target.value)}

            />

          <button className="text-white">Login</button>
          <div className="text-center py-2">
            Ainda não tem uma conta?
            <Link className="underline font-bold" to={"/register"}>
              Registra-se agora
            </Link>
          </div>
        </form>
      </div>
    </div>
  );
}
