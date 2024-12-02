package puc.pi4.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Controller {

    private final Socket clientSocket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    public Controller(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    public void handleRequest() throws IOException {
        String requestLine = reader.readLine();
        if (requestLine == null) {
            return;
        }

        // Concatenando a request
        System.out.println("Request: " + requestLine);
        String[] requestParts = requestLine.split(" ");
        String method = requestParts[0]; // Metodo (GET,DELETE...)
        String path = requestParts[1]; // Caminho (GetAllEmpresas, DeleteEmpresa....)

       
        if ("OPTIONS".equals(method)) {
            // Adiciona os headers do CORS
            sendOptionsResponse();
            return;
        }

        // Gerencia as requisicoes
        if ("GET".equals(method)) {
            if ("/getAllEmpresas".equals(path)) {
                getAllEmpresas();
            } else if ("/getAllCozinhas".equals(path)) {
                getAllCozinhas();
            } else {
                sendNotFoundResponse();
            }
        } else {
            sendNotFoundResponse();
        }

        // Close the socket
        clientSocket.close();
    }

    private void sendOptionsResponse() {
        writer.println("HTTP/1.1 200 OK");
        writer.println("Access-Control-Allow-Origin: *");
        writer.println("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
        writer.println("Access-Control-Allow-Headers: Content-Type");
        writer.println("");  // Empty line indicating the end of headers
    }

    private void getAllEmpresas() {
        // Simulate a response
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: application/json");
        writer.println(""); // Empty line indicating end of headers
        writer.println("[{\"name\": \"Empresa 1\"}, {\"name\": \"Empresa 2\"}]");
    }

    private void getAllCozinhas() {
        // Simulate a response
        writer.println("HTTP/1.1 200 OK");
        writer.println("Content-Type: application/json");
        writer.println(""); // Empty line indicating end of headers
        writer.println("[{\"name\": \"Cozinha 1\"}, {\"name\": \"Cozinha 2\"}]");
    }

    private void sendNotFoundResponse() {
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/plain");
        writer.println("");  // Empty line indicating the end of headers
        writer.println("404 Not Found");
    }
}
