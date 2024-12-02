package puc.pi4;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import puc.pi4.Controllers.Controller;

public class ServerJava {

    public static void main(String[] args) {
        try {
            try (// Cria o servidor na porta 8080
            ServerSocket serverSocket = new ServerSocket(8080)) {
                System.out.println("Servidor HTTP iniciado na porta 8080...");

                // Espera por conexões de clientes
                while (true) {
                    // Aceita uma conexão de cliente
                    Socket clientSocket = serverSocket.accept();

                    // Cria uma nova thread para tratar a requisição
                    new Thread(() -> {
                        try {
                            // Passa o socket para o Controller para tratar a requisição
                            new Controller(clientSocket).handleRequest();
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

