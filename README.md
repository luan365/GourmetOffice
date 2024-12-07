# GourmetOffice

Repositório do **Grupo 8** do projeto integrador 4.

**Integrantes do grupo:**

- *Vitor Hugo Amaro Aristides, RA: 20018040*

- *Bruno Tasso Savoia, RA:22000354*

- *João Pedro Simões Serra, RA:19072362*

- *Ryan Matheus moreira Barbosa, RA:22900872*

- *Luan de Campos Ferreira, RA:23005247*

## Ferramentas utilizadas

- Yarn para gerenciar dependencias no front-end

- Maven para gerenciar dependencias no servidor/backend

## Como instalar dependências e rodar o programa (por enquanto)

***IMPORTANTE***

- *Criar um arquivo "db.properties" no diretório "GourmetOffice\serverjava\src\main\resources\db.properties" com campos "db_user" e "db_password"*

- *Alterar o filepath no index.js na pasta api*

- *Instalar o yarn*

- *instalar maven* (opcional)

### Iniciando o front-end

- *Executar o comando "yarn" no diretório "client" para instalar dependências do yarn*

- *Executar "npm install axios" no mesmo terminal*

- *Digitar "yarn dev" no temrinal e clickar no link localhost*

### Iniciando servidor com node.js
- *na pasta api*
- *importar express =  yarn add express*
- *importar cors = yarn add cors*
- *importar JsonWebToken = yarn add jsonwebtoken*
- *importar cookieParser = yarn add cookie-parser*
- *importar mongo = yarn add mongoose*



- para rodar o servidor com node + express
- *na pasta api digitar "nodemon index.js"*


### Iniciando o servidor java

- *Executar "ServerJava.java" que está no diretório "GourmetOffice\serverjava\src\main\java\puc\pi4\" para iniciar o servidor*

FALTANTES

 arrumar erros da pagina login

    erro quando os usuarios escolhe o checkbox errado mas conta existente não existe o usuario na  collection mas mesmo assim loga

    e erro de senha 

 separar a pagina de conta do usuario cozinha e empresa  ----------- (Feito)

    para empresa -> apenas add um botao para deslogar EmpresaAccont.jsx  ----------- (Feito)
   
    para cozinha -> uma opção de adicionar sua cozinha CozinhaAccont.jsx ----------- (Feito)
      

 index.jsx 
    recuperar os dados da cozinha 
    
        para pagina index(principal)
