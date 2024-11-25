const mongoose = require('mongoose');
const {Schema} = mongoose;

const EmpresaSchema = new Schema ({
    nome:String,
    cnpj:String,
    email:String,
    senha:String,
    telefone:String,
    endereco:String,
    tipo:String,
    
},{
    collection: 'Empresas'  // Especifica o nome da coleção existente no MongoDB
}
    );
const EmpresaModel = mongoose.model('Empresas', EmpresaSchema,'Empresas');
module.exports = EmpresaModel;
 