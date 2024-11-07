import React from "react"
import {Link} from "react-router-dom";
export  default function LoginPage(){
    return(
       <div className="mt-4 grow flex items-center justify-around">
        <div className="mb-64">
            <h1 className="text-4xl text-center">Login</h1>
            <form className="max-w-md mx-auto ">
            <input type="email"placeholder='your@rmail.com'></input>
            <input type = "password" placeholder="password"/>
            <button className="text-white">Login</button>
            <div className="text-center py-2">
                Ainda não tem uma conta? 
                 <Link className="underline font-bold" to={'/register'}>Registra-se agora</Link>
                </div>
        </form>
        </div>
    </div> 
    )
    
}