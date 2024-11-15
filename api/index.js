const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose')
const app = express();
const EmpresaModel =require('./model/User.js')
app.use(express.json());
app.use(cors({
    credentials: true,
    origin: 'http://localhost:5173',
}));




// pegando os dados sensiveis sem usar o dotenv
const fs = require('fs');

// Função para carregar e extrair as propriedades do arquivo db.properties
function loadDbProperties(filePath) {
    const properties = {};
    const data = fs.readFileSync(filePath, 'utf8'); // Lê o arquivo de forma síncrona

    // Divide as linhas do arquivo e extrai as chaves e valores
    data.split('\n').forEach(line => {
        // Remove espaços e ignora linhas em branco
        const trimmedLine = line.trim();
        if (trimmedLine && trimmedLine.includes('=')) {
            const [key, value] = trimmedLine.split('=').map(str => str.trim());
            properties[key] = value; // Armazena as propriedades em um objeto
        }
    });

    return properties;
}

// Caminho para o arquivo db.properties
const filePath = 'C:/Users/55199/GourmetOffice/serverjava/target/classes/db.properties'

// Carregar as credenciais do arquivo
const credentials = loadDbProperties(filePath);

// Obter usuário e senha
const user = credentials.db_user;
const pass = credentials.db_password;

// Construir a URI de conexão com MongoDB
const uri = `mongodb+srv://${user}:${pass}@gourmetoffice.fnzzv.mongodb.net/?retryWrites=true&w=majority&appName=gourmetOffice`;

mongoose.connect(uri);


mongoose.connect(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
}).then(() => {
  console.log('Conectado ao MongoDB');
}).catch((err) => {
  console.log('Erro de conexão com o MongoDB', err);
});






app.get('/teste',async (req,res)=>{
    res.json('server ok');
});





app.put('/login',async(req,res)=>{
const {email,senha}=req.body;
console.log('Requisição recebida com:', req.body);
const empresa = await EmpresaModel.findOne({email});
if (empresa){
      res.json('achou')
    console.log(empresa.email)      
}else{
    res.json('não achou')
    console.log(empresa)
}



})


app.listen(4000);

