package puc.pi4.Operations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;

import puc.pi4.Entities.Empresa;

public class EmpresaOperations {
    private  MongoCollection<Document> collection;
    
        public EmpresaOperations(){
                    try {
                Properties prop = new Properties();
                prop.load(new FileInputStream("serverjava/src/main/resources/db.properties"));
                String user = prop.getProperty("db_user");
                String pass = prop.getProperty("db_password");
    
                String uri = String.format("mongodb+srv://%s:%s@gourmetoffice.fnzzv.mongodb.net/", user,pass);
    
                // Conectando ao MongoDB Atlas
                MongoClient mongoClient = MongoClients.create(uri);
                MongoDatabase database = mongoClient.getDatabase("GourmetOffice");
                this.collection = database.getCollection("Empresas");
    
            } catch (IOException e) {
                System.err.println("Erro ao conectar mongo");
                e.printStackTrace();
            }
        } 
        
    public  List<Empresa> getAllEmpresas() throws Exception {
        
        List<Empresa> empresas = new ArrayList<>();


        for (Document doc : collection.find()) {
                

            Empresa empresa = new Empresa(doc.getString("nome"),
            doc.getString("cnpj"),
            doc.getString("email"),
            doc.getString("senha"),
            doc.getString("telefone"),
            doc.getString("endereco"),
            doc.getString("tipo"));
            empresas.add(empresa);

        }
        
        return empresas;
    }

    public Empresa getEmpresaByCNPJ(String cnpj){
        Gson gson = new Gson();

        Document filter = new Document("cnpj", cnpj);

        Document doc = collection.find(filter).first();

        if(doc == null){
            return null;
        }else{
            return gson.fromJson(doc.toJson(), Empresa.class);
        }
            
        
    }



    public void insertEmpresa(Empresa x) throws Exception{

        

        if(getEmpresaByCNPJ(x.getCNPJ())!=null){
            throw new Exception("Empresa já existe");
        }

        Gson gson = new Gson();
        String json = gson.toJson(x);

        Document doc = Document.parse(json);
        collection.insertOne(doc);

        
        
 
    }

    public void updateEmpresa(Empresa x, String cnpj){
        Gson gson = new Gson();
        String json = gson.toJson(x);

        Document filter = new Document("cnpj", cnpj);

        Document doc = Document.parse(json);

        Document updateDoc = new Document("$set",doc);

        

        try {
            collection.findOneAndUpdate(filter, updateDoc, new FindOneAndUpdateOptions());
        } catch (Exception e) {
            System.err.println("Erro ao atualizar" + e);
        }
        

    }

    public Empresa deleteEmpresa(String cnpj) throws Exception {
        

        Document filter = new Document("cnpj", cnpj);

        Document empresaDeletada = null;

        try {
            empresaDeletada = collection.findOneAndDelete(filter);
        } catch (Exception e) {
            System.err.println("Erro interno mongoDB \n");
            throw new Exception(e);
            
        }

        

        if (empresaDeletada != null) {
            // Converte o Document de volta para um objeto Empresa
            Gson gson = new Gson();
            
            return gson.fromJson(empresaDeletada.toJson(), Empresa.class);
        } else {
            System.out.println("Nenhuma empresa encontrada com o CNPJ fornecido.");
            return null; // Retorna null caso não encontre o documento
        }

    }



}
