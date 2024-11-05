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
                MongoClient mongoClient = MongoClients.create("mongodb+srv://bruno:123456qwerty@gourmetoffice.fnzzv.mongodb.net/");
                MongoDatabase database = mongoClient.getDatabase("GourmetOffice");
                this.collection = database.getCollection("Empresas");
    
            } catch (IOException e) {
                System.err.println("DEU MERDA");
                e.printStackTrace();
            }
        } 
        
        public  List<Empresa> getAllEmpresas() {
            System.out.println("Funcao buscar iniciada");
            List<Empresa> empresas = new ArrayList<>();

            for (Document doc : collection.find()) {
                

                Empresa empresa = new Empresa(doc.getString("nome"),
                doc.getString("cnpj"),
                doc.getString("email"),
                doc.getString("senha"),
                doc.getString("telefone"),
                doc.getString("endereco"));

                empresas.add(empresa);

        }
        
        return empresas;
    }

    public Empresa insertEmpresa(Empresa x){

        Gson gson = new Gson();
        String json = gson.toJson(x);

        Document doc = Document.parse(json);
        collection.insertOne(doc);

        System.out.println(doc.toJson());
        return x;

    }



}