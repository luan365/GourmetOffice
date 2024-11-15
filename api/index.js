const express = require('express');
const cors = require('cors');
const mongoose = require('mongoose')
const app = express();

app.use(express.json());
app.use(cors({
    credentials: true,
    origin: 'http://localhost:5173',
}));


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


app.get('/teste',(req,res)=>{
    res.json('server ok');
});


app.listen(4000);

