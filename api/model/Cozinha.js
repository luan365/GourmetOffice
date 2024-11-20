const mongoose = require('mongoose');
const {Schema} = mongoose;

const CozinhaSchema= new Schema ({
    nome:String,
    cnpj:String,
    email:String,
    senha:String,
    telefone:String,
    endereco:String,
},{
    collection:'Cozinhas' //Especifica o nome da coleção existente no MongoDB
}
);
const CozinhaModel = mongoose.model('Cozinhas',CozinhaSchema,'Cozinhas');
module.exports = CozinhaModel