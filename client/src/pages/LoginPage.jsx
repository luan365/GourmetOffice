import React from "react";
import { Link } from "react-router-dom";
export default function LoginPage() {
  const [email, setEmail] = useState("");
  const [senha, setSenha] = useState(""); 
  async function login(ev){
        ev.preventDefault()
    }
  return (
    <div className="mt-4 grow flex items-center justify-around mt-20">
      <div className="mb-72">
        <h1 className="text-4xl text-center">Login</h1>
        <form className="max-w-md mx-auto ">

          <input type="email"
           placeholder="your@rmail.com">
            value={email}
            onChange = {(ev)=>setEmail(ev.target.value)}
           </input>

          <input type="password"
           placeholder="password"
           value={senha}
           onChange={(ev)=>setSenha(ev.target.setSenha)}

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
