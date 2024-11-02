package puc.pi4.Operations;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.bson.Document;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import puc.pi4.Entities.Person;

public class PersonOperations {
    private MongoCollection<Document> collection;

    public PersonOperations() {

        try {
            Properties prop = new Properties();
            prop.load(new FileInputStream("serverjava/src/main/resources/db.properties"));
            String user = prop.getProperty("db_user");
            String pass = prop.getProperty("db_password");

            String uri = String.format("mongodb+srv://%s:%s@gourmetoffice.fnzzv.mongodb.net/", user,pass);

            // Conectando ao MongoDB Atlas
            MongoClient mongoClient = MongoClients.create("mongodb+srv://bruno:123456qwerty@gourmetoffice.fnzzv.mongodb.net/");
            MongoDatabase database = mongoClient.getDatabase("person");
            this.collection = database.getCollection("people");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Listar todas as pessoas
    public List<Person> getAllPersons() {
        List<Person> persons = new ArrayList<>();
        for (Document doc : collection.find()) {
            Person person = new Person();
            person.setId(doc.getObjectId("_id"));
            person.setName(doc.getString("name"));
            person.setAge(doc.getInteger("age"));
            persons.add(person);
        }
        return persons;
    }

    // Adicionar uma nova pessoa
    public Person addPerson(Person person) {
        Document doc = new Document("name", person.getName())
                              .append("age", person.getAge());
        collection.insertOne(doc);
        person.setId(doc.getObjectId("_id"));
        return person;
    }
}
