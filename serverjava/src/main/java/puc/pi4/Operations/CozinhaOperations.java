package puc.pi4.Operations;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.google.gson.Gson;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.FindOneAndUpdateOptions;

import puc.pi4.Entities.Cozinha;



public class CozinhaOperations {
    private MongoCollection<Document> collection;
    
    public CozinhaOperations(){

            // Conectando ao MongoDB Atlas
            MongoClient mongoClient = MongoClients.create("mongodb+srv://bruno:123456qwerty@gourmetoffice.fnzzv.mongodb.net/");
            MongoDatabase database = mongoClient.getDatabase("GourmetOffice");
            this.collection = database.getCollection("Cozinhas");

     
    }

    public  List<Cozinha> getAllCozinhas() throws Exception {
        System.out.println("Funcao buscar iniciada");
        List<Cozinha> cozinhas = new ArrayList<>();

        for (Document doc : collection.find()) {
                

            Cozinha cozinha = new Cozinha(doc.getString("nome"),
            doc.getString("cnpj"),
            doc.getString("email"),
            doc.getString("senha"),
            doc.getString("telefone"),
            doc.getString("endereco"),
            doc.getString("descricao"),
            doc.getString("tipo"));

            cozinhas.add(cozinha);

    }
        System.out.println(cozinhas);
        return cozinhas;
    }

    public Cozinha getCozinhaByCNPJ(String cnpj){
        Gson gson = new Gson();

        Document filter = new Document("cnpj", cnpj);

        Document doc = collection.find(filter).first();

        if(doc == null){
            return null;
        }else{
            return gson.fromJson(doc.toJson(), Cozinha.class);
        }
            
        
    }

    public void insertCozinha(Cozinha x) throws Exception{

        if(getCozinhaByCNPJ(x.getCNPJ())!=null){
            throw new Exception("Cozinha já existe");
        }


        Gson gson = new Gson();
        String json = gson.toJson(x);

        Document doc = Document.parse(json);
        collection.insertOne(doc);

        System.out.println(doc.toJson());
        

    }

        public Cozinha updateCozinha(Cozinha x, String cnpj){
        Gson gson = new Gson();
        String json = gson.toJson(x);

        Document filter = new Document("cnpj", cnpj);

        Document doc = Document.parse(json);

        Document updateDoc = new Document("$set",doc);

        System.out.println(updateDoc);

        try {
            collection.findOneAndUpdate(filter, updateDoc, new FindOneAndUpdateOptions());
        } catch (Exception e) {
            System.err.println("Erro ao atualizar" + e);
        }
        

        return x;



    }

        public Cozinha deleteCozinha(String cnpj) throws Exception {
        

        Document filter = new Document("cnpj", cnpj);

        Document cozinhaDeletada = null;

        try {
            cozinhaDeletada = collection.findOneAndDelete(filter);
        } catch (Exception e) {
            System.err.println("Erro interno mongoDB \n");
            throw new Exception(e);
            
        }

        

        if (cozinhaDeletada != null) {
            // Converte o Document de volta para um objeto Empresa
            Gson gson = new Gson();
            
            return gson.fromJson(cozinhaDeletada.toJson(), Cozinha.class);
        } else {
            System.out.println("Nenhuma Cozinha encontrada com o CNPJ fornecido.");
            return null; // Retorna null caso não encontre o documento
        }

    }


}
