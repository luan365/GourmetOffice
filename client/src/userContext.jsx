import{createContext, useEffect, useState}from "react";//criar um contexto que pode armazenar e compartilhar informações
export const UserContext = createContext({});
import axios from 'axios'

export function UserContextProvider({children}){ //provedor do contexto, responsável por envolver outros componentes e compartilhar o valor do contexto
const [user,setUser] = useState(null);
const [ready,setReady] = useState(false);
useEffect(() =>{
    if (!user){
        axios.get('/profile').then(({data})=>{
            setUser(data);
            setReady(true);
        });
    }
},[]) // garante a sincronização de dados  

    return(
        
       
          <UserContext.Provider value={{user,setUser,ready,setReady}}>
            {children}
          </UserContext.Provider>
        
    );
}
// componentes filhos do arq app.jsx, o conteudo será provido para todos os filhos 
//pode acessar o valor fornecido no contexto usando o useContext
//.then é usado com Promises em JavaScript, principalmente para lidar com operações assíncronas,
// como requisições HTTP ou leitura de arquivos. Ele é chamado quando uma Promise é resolvida com sucesso, permitindo executar um código baseado no valor retornado.