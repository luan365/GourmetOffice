const express = require('express');
const cors = require('cors');
const jwt = require('jsonwebtoken')
const mongoose = require('mongoose')
const app = express();
const cookieParser = require('cookie-parser');
const EmpresaModel = require('./model/Empresa.js');
const CozinhaModel = require('./model/Cozinha.js');

//midware
app.use(express.json());
app.use(cookieParser()); // -> lendo o cookeies dem json
app.use(cors({
    credentials: true,
    origin: 'http://localhost:5173',
}));


const  jwtSecret = 'kljhdfkjsdhskhjdhf'//string de assinatura para token


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
const filePath = '../serverjava/src/main/resources/db.properties';


//caminho pc brunosh : 'C:/users/bruno/Desktop/GourmetOffice/serverjava/src/main/resources/db.properties'

// Carregar as credenciais do arquivo
const credentials = loadDbProperties(filePath);

// Obter usuário e senha
const user = credentials.db_user;
const pass = credentials.db_password;

// Construir a URI de conexão com MongoDB
const uri = `mongodb+srv://${user}:${pass}@gourmetoffice.fnzzv.mongodb.net/GourmetOffice?retryWrites=true&w=majority&appName=gourmetOffice`;


mongoose.connect(uri);

/*
mongoose.connect(uri, {
  useNewUrlParser: true,
  useUnifiedTopology: true,
}).then(() => {
  console.log('Conectado ao MongoDB');
}).catch((err) => {
  console.log('Erro de conexão com o MongoDB', err);
});


mongoose.connection.once('open', () => {
  console.log('Conectado ao MongoDB na base:', mongoose.connection.name);
});
*/



app.get('/teste',async (req,res)=>{
    res.json('server ok');
});


app.put('/login/empresa', async (req, res) => {
  const { email, senha } = req.body;
  console.log('Requisição recebida com:', req.body);

  // Procurando a empresa pelo email
  const empresa = await EmpresaModel.findOne({ email });

  if (empresa) {
    // Verificando se a senha fornecida corresponde à senha armazenada no banco
    if (senha === empresa.senha) {
      // Gerando o token JWT
      jwt.sign({ email: empresa.email, id: empresa._id }, jwtSecret, {}, (err, token) => {
        if (err) {
          return res.status(500).json({ message: 'Erro ao gerar token' });
        }
        // Enviando o token no cookie e os dados da empresa
        res.cookie('token', token).json(empresa);
      });
    } else {
      // Retornando um erro adequado caso a senha esteja errada
      return res.status(400).json({ message: 'Senha incorreta' });
    }
  } else {
    // Caso a empresa não seja encontrada
    return res.status(404).json({ message: 'Empresa não encontrada' });
  }
});



app.put('/login/cozinha',async(req,res)=>{
  const {email,senha}=req.body;
  console.log('Requisição recebida com:', req.body);
//procurando email recebido no banco
  const cozinha = await CozinhaModel.findOne({email});

  if (cozinha){
    // Verificando se a senha fornecida corresponde à senha armazenada no banco
    if(senha===cozinha.senha){
      //gerando token JWT
      jwt.sign({email:cozinha.email,id:cozinha._id},jwtSecret, {}, (err,token)=>{
        if (err) {
          return res.status(500).json({ message: 'Erro ao gerar token' });
        }
        res.cookie('token',token).json(cozinha)
      })//gerando uma assinatura token com os dados email e id no payload
    }else{
      return res.status(400).json({message:'Senha incorreta'});
    }
  }else {
        // Caso a empresa não seja encontrada
        return res.status(404).json({ message: 'Empresa não encontrada' });
      }
  
  });

  app.get('/profile', async (req, res) => {
    const { token } = req.cookies;
    if (!token) {
      return res.status(401).json({ message: 'Token não fornecido' });
    }
  
    try {
      jwt.verify(token, jwtSecret, {}, async (err, userData) => {
        if (err) {
          return res.status(403).json({ message: 'Token inválido' });
        }
  
        // Tenta buscar no modelo Empresa
        let userProfile = await EmpresaModel.findById(userData.id);
        if (userProfile) {
          const { tipo:empresaTipo,nome: empresaNome, email: empresaEmail, _id: empresaId,senha: empresaSenha, cnpj: empresaCNPJ, endereco: empresaEndereco, telefone: empresaTelefone } = userProfile;
          return res.json({ tipo: empresaTipo, nome: empresaNome, email: empresaEmail, id: empresaId, senha: empresaSenha,cnpj: empresaCNPJ, endereco: empresaEndereco, telefone: empresaTelefone}); 
        }
  
        // Caso não encontre no modelo Empresa, tenta buscar no modelo Cozinha
        userProfile = await CozinhaModel.findById(userData.id);
        if (userProfile) {
          const { tipo:cozinhaTipo,nome: cozinhaNome, email: cozinhaEmail, _id: cozinhaId, senha: cozinhaSenha, cnpj: cozinhaCNPJ, endereco: cozinhaEndereco, telefone: cozinhaTelefone} = userProfile;
          return res.json({ tipo: cozinhaTipo, nome: cozinhaNome, email: cozinhaEmail, id: cozinhaId, senha: cozinhaSenha, cnpj: cozinhaCNPJ, endereco: cozinhaEndereco, telefone: cozinhaTelefone }); 
        }
  
        // Se não encontrar em nenhum dos modelos
        return res.status(404).json({ message: 'Usuário não encontrado' }); // Importante: usar return
      });
    } catch (error) {
      console.error('Erro no profile:', error);
      res.status(500).json({ message: 'Erro interno do servidor' });
    }
  });

app.put('/logout',(req,res)=>{
  console.log("entrou no index logout")
  res.cookie('token','').json("saiu")
})



app.listen(4000);

