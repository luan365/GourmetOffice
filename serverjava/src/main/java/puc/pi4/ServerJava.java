package puc.pi4;

import java.io.IOException;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpServer;

import puc.pi4.Controllers.Controller;

public class ServerJava {
    public static void main(String[] args) {
        try {
            // Iniciando o servidor HTTP na porta 8080
            HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);
            System.out.println("Servidor HTTP iniciado na porta 8080...");

            // Registrando o controlador para os endpoints de /api/people
            server.createContext("/", new Controller());
            server.setExecutor(null);
            server.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
