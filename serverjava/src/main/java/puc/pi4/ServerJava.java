package puc.pi4;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Properties;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

import puc.pi4.Controllers.Controller;

public class ServerJava {
    
    public static void main(String[] args) {
        try {

            Properties properties = new Properties();
            try (InputStream input = ServerJava.class.getClassLoader().getResourceAsStream("db.properties")) {
                if (input == null) {
                    throw new IOException("Arquivo db.properties não encontrado no classpath");
                }
                properties.load(input);
            }

            // Obter usuário e senha
            String user = properties.getProperty("db_user");
            String password = properties.getProperty("db_password");

            // Criar a URI de conexão com MongoDB
            String connectionString = String.format(
                "mongodb+srv://%s:%s@gourmetoffice.fnzzv.mongodb.net/",
                user, password
            );

            try (// Cria o servidor na porta 8080
            ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("Servidor HTTP iniciado na porta 8080...");
            
                MongoClient mongoClient = MongoClients.create(connectionString);
                MongoDatabase database = mongoClient.getDatabase("GourmetOffice");
                // Espera por conexões de clientes
                while (true) {
                    // Aceita uma conexão de cliente
                    Socket clientSocket = serverSocket.accept();

                    // Cria uma nova thread para tratar a requisição
                    new Thread(() -> {
                        try {
                            // Passa o socket para o Controller para tratar a requisição
                            new Controller(clientSocket, database).handleRequest();
                        } catch (IOException e) {
                            
                            e.printStackTrace();
                        }
                    }).start();
                    
                }
            }
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

