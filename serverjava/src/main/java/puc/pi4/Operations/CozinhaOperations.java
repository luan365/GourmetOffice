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

import puc.pi4.Entities.Cozinha;


public class CozinhaOperations {
    private MongoCollection<Document> collection;
    
    public CozinhaOperations(){
                try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("serverjava/src/main/resources/db.properties"));
            String user = prop.getProperty("db_user");
            String pass = prop.getProperty("db_password");

            String uri = String.format("mongodb+srv://%s:%s@gourmetoffice.fnzzv.mongodb.net/", user,pass);

            // Conectando ao MongoDB Atlas
            MongoClient mongoClient = MongoClients.create("mongodb+srv://bruno:123456qwerty@gourmetoffice.fnzzv.mongodb.net/");
            MongoDatabase database = mongoClient.getDatabase("GourmetOffice");
            this.collection = database.getCollection("Cozinhas");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public  List<Cozinha> getAllCozinhas() {
        System.out.println("Funcao buscar iniciada");
        List<Cozinha> cozinhas = new ArrayList<>();

        for (Document doc : collection.find()) {
                

            Cozinha cozinha = new Cozinha(doc.getString("nome"),
            doc.getString("cnpj"),
            doc.getString("email"),
            doc.getString("senha"),
            doc.getString("telefone"),
            doc.getString("endereco"));

            cozinhas.add(cozinha);

    }
        
        return cozinhas;
    }

    public Cozinha insertCozinha(Cozinha x){

        Gson gson = new Gson();
        String json = gson.toJson(x);

        Document doc = Document.parse(json);
        collection.insertOne(doc);

        System.out.println(doc.toJson());
        return x;

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


}
