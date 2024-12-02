package puc.pi4.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;

import puc.pi4.Entities.Cozinha;
import puc.pi4.Entities.Empresa;
import puc.pi4.Operations.CozinhaOperations;
import puc.pi4.Operations.EmpresaOperations;

public class Controller {
    private final EmpresaOperations empresaOperations = new EmpresaOperations();
    private final CozinhaOperations cozinhaOperations = new CozinhaOperations();
    private final Gson gson = new Gson();


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
            writer.println("HTTP/1.1 200 OK");
            writer.println("Access-Control-Allow-Origin: *");
            writer.println("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
            writer.println("Access-Control-Allow-Headers: Content-Type");
            writer.println(""); 
            return;
        }

        

        System.out.println("Metodo" + method);
        System.out.println("Caminho " + path);

        boolean handled = false;

        // Gerencia as requisicoes
        if ("GET".equals(method)) {
            handled = true;
            if ("/getAllEmpresas".equals(path)) {
                System.out.println("DAORA");
                getAllEmpresas();
            } else if ("/getAllCozinhas".equals(path)) {
                getAllCozinhas();
            } else {
                //sendNotFoundResponse(); CRIAR REQUISICAO DE ERRO ESPECIFICA
            }
        }
        
        if ("PUT".equals(method)) {
            handled = true;
            if ("/insertEmpresa".equals(path)) {
                System.out.println("DAORA");
                
            } else if ("/insertCozinha".equals(path)) {
                
            } else {
                //sendNotFoundResponse(); CRIAR REQUISICAO DE ERRO ESPECIFICA
            }
        }

        if ("DELETE".equals(method)) {
            handled = true;
            if ("/deleteEmpresa".equals(path)) {
                System.out.println("DAORA");
                
            } else if ("/deleteCozinha".equals(path)) {
                
            } else {
                //sendNotFoundResponse(); CRIAR REQUISICAO DE ERRO ESPECIFICA
            }
        }

        if ("PATCH".equals(method)) {
            handled = true;
            if ("/updateEmpresa".equals(path)) {
                System.out.println("DAORA");
                
            } else if ("/updateCozinha".equals(path)) {
                
            } else {
                //sendNotFoundResponse(); CRIAR REQUISICAO DE ERRO ESPECIFICA
            }
        }

        // Close the socket
        if(!handled){sendNotFoundResponse();}
        
        clientSocket.close();
    }

    //------------------------------------------------ FUNCOES SEPARADAS PARA LIMPAR O CODIGO-------------------------------------------

    private void getAllEmpresas() {
        try {
            List<Empresa> empresas = empresaOperations.getAllEmpresas();
             
            System.out.println(empresas);        
            


            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: application/json");
            writer.println(""); // Empty line indicating end of headers
            writer.println(empresas);


            } catch (Exception e) {
                System.err.println("Erro ao buscar todas empresas " + e);
                e.printStackTrace();

            }
    }

    private void getAllCozinhas() {

         try {
                        
            List<Cozinha> cozinhas = cozinhaOperations.getAllCozinhas();

            System.out.println(cozinhas.size());

            writer.println("HTTP/1.1 200 OK");
            writer.println("Content-Type: application/json");
            writer.println(""); // Empty line indicating end of headers
            writer.println(cozinhas);
                    
            
            } catch (Exception e) {
                System.err.println("Erro ao buscar todas as cozinhas " + e);
                e.printStackTrace();

            }

    }

    private void sendNotFoundResponse() {
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/plain");
        writer.println("");  // Empty line indicating the end of headers
        writer.println("404 Not Found");
    }
}
