package puc.pi4.Controllers;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import puc.pi4.Entities.Cozinha;
import puc.pi4.Entities.Empresa;
import puc.pi4.Operations.CozinhaOperations;
import puc.pi4.Operations.EmpresaOperations;

public class Controller implements HttpHandler {
    private final EmpresaOperations empresaOperations = new EmpresaOperations();
    private final CozinhaOperations cozinhaOperations = new CozinhaOperations();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String method = exchange.getRequestMethod();
        String path = exchange.getRequestURI().getPath();
        String response = "";

        System.out.println(method);


        //Switch getter ->
        if("GET".equals(method)){

            switch(path){
                case "/getAllEmpresas":
                List<Empresa> empresas = empresaOperations.getAllEmpresas();
                response = gson.toJson(empresas);
                System.out.println(response);
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
               
                case "/getAllCozinhas":
                List<Cozinha> cozinhas = cozinhaOperations.getAllCozinhas();
                response = gson.toJson(cozinhas);
                System.out.println(response);
                exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
    
            }
        }
        
         //Switch Insert ->
        if("PUT".equals(method)){
            switch(path){
                case "/insertEmpresa":
                Empresa empresa = gson.fromJson(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8), Empresa.class);
                Empresa addedEmpresa = empresaOperations.insertEmpresa(empresa);
                response = gson.toJson(addedEmpresa);
                exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
    
                case "/insertCozinha":
                Cozinha cozinha = gson.fromJson(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8), Cozinha.class);
                Cozinha addedCozinha = cozinhaOperations.insertCozinha(cozinha);
                response = gson.toJson(addedCozinha);
                exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
            }
               
        }

       


        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
            System.out.println("Resposta enviada com sucesso.");
        } catch (IOException e) {
            System.err.println("Erro ao enviar a resposta: " + e.getMessage());
            e.printStackTrace();
        }



    }
}