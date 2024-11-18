import {useContext} from "react";
import {UserContext} from "../userContext.jsx";
import { Navigate } from "react-router-dom";

export default function AccontPage(){
const {ready,user} = useContext(UserContext);

if(!ready){
    console.log(ready,'ready aqui')
    return 'carregando...';
}

if(ready && !user){ //verifica se há um usuario logado e verifica se 
    console.log('ready:',ready,'user',user)
    return <Navigate to={'/login'}/>
}

return(
<div>coisas sobre conta aqui {user.nome}</div>
);
}