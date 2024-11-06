package puc.pi4.Controllers;

//Controla todas as requisições HTTP

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

        System.out.println("MÉTODO CHAMADO " + method);

        System.out.println("CAMINHO : "+path);


        //Switch getter ->
        if("GET".equals(method)){

            if("/getAllEmpresas".equals(path)){
                try {
                    List<Empresa> empresas = empresaOperations.getAllEmpresas();
                    response = gson.toJson(empresas);
                    
                    exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                } catch (Exception e) {
                    System.err.println("Erro ao buscar todas empresas " + e);
                    e.printStackTrace();
                    response = gson.toJson("Erro ao processar a solicitação.");
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                }
            }

            
            if("/getAllCozinhas".equals(path)){
                try {
                        
                    List<Cozinha> cozinhas = cozinhaOperations.getAllCozinhas();
                    response = gson.toJson(cozinhas);
                    
                    exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    } catch (Exception e) {
                        System.err.println("Erro ao buscar todas as cozinhas " + e);
                        e.printStackTrace();
                        response = gson.toJson("Erro ao processar a solicitação.");
                        exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                    }
            }
        }
        
         //Switch Insert ->
        if("PUT".equals(method)){

            if("/insertEmpresa".equals(path)){
                System.out.println("INSERINDO EMPRESA");
                try {    
                Empresa empresa = gson.fromJson(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8), Empresa.class);
                Empresa addedEmpresa = empresaOperations.insertEmpresa(empresa);
                response = gson.toJson(addedEmpresa);
                exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
                } catch (Exception e) {
                    System.err.println("Erro ao inserir empresa "+e);
                    e.printStackTrace();
                    response = gson.toJson("Erro ao processar a solicitação.");
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);

                }
            }

            
            if("/insertCozinha".equals(path)){
                System.out.println("INSERINDO COZINHA");
                try {   
                    Cozinha cozinha = gson.fromJson(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8), Cozinha.class);
                    Cozinha addedCozinha = cozinhaOperations.insertCozinha(cozinha);
                    response = gson.toJson(addedCozinha);
                    exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);   
                    } catch (Exception e) {
                        System.err.println("Erro ao inserir cozinha "+e);
                        e.printStackTrace();
                        response = gson.toJson("Erro ao processar a solicitação.");
                        exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                    }
            }
               
        }

        if("PATCH".equals(method)){

            if("updateEmpresa".equals(path)){
                try {
                            
                    
                    Empresa empresaAtualizada = gson.fromJson(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8), Empresa.class);
                    String cnpjEmpresa = empresaAtualizada.getCNPJ();
                    

                    Empresa empresaUpdate = empresaOperations.updateEmpresa(empresaAtualizada, cnpjEmpresa);
                    response = gson.toJson(empresaUpdate);
                    exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);                        
                    } catch (Exception e) {
                        System.err.println("Erro ao atualizar empresa "+e);
                        e.printStackTrace();
                        response = gson.toJson("Erro ao processar a solicitação.");
                        exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                    }
            }

            
            if("updateCozinha".equals(path)){
                try {
                            
                    
                    Cozinha cozinhaAtualizada = gson.fromJson(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8), Cozinha.class);
                    String cnpjCozinha = cozinhaAtualizada.getCNPJ();
                    

                    Cozinha cozinhaUpdate = cozinhaOperations.updateCozinha(cozinhaAtualizada, cnpjCozinha);
                    response = gson.toJson(cozinhaUpdate);
                    exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);   
                    } catch (Exception e) {
                        System.err.println("Erro ao atualizar cozinha "+e);
                        e.printStackTrace();
                        response = gson.toJson("Erro ao processar a solicitação.");
                        exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                    }                
            }
        }

        if("DELETE".equals(method)){

            if("/deleteEmpresa".equals(path)){
                try(InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)) {
                    String cnpj =gson.fromJson(reader, String.class);
                    Empresa deletedEmpresa = empresaOperations.deleteEmpresa(cnpj);
                
                    if (deletedEmpresa != null) {
                        response = gson.toJson(deletedEmpresa);
                        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    } else {
                        // Retorna erro se nenhuma empresa foi encontrada com o CNPJ fornecido
                        response = gson.toJson("Empresa com CNPJ " + cnpj + " não encontrada.");
                        exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
                    }
                
                
                } catch (Exception e) {
                    System.err.println("Erro ao deletar empresa"+e);
                    e.printStackTrace();
                    response = gson.toJson("Erro ao processar a solicitação.");
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                }
            }

            
            if("/deleteCozinha".equals(path)){
                
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