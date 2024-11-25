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
        // extrai o caminho da URI da requisição HTTP feita ao servidor e o armazena na variável path. 
        //Esse caminho pode então ser usado para verificar qual recurso ou endpoint o cliente está tentando acessa
        String path = exchange.getRequestURI().getPath();
        String response = "";

  // Adicionando cabeçalhos CORS
  exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "http://localhost:5173"); // Permite acesso 
  exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, PUT, PATCH, DELETE, OPTIONS"); // Métodos permitidos
  exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization"); // Cabeçalhos permitidos
  exchange.getResponseHeaders().add("Access-Control-Allow-Credentials", "true"); // Permite enviar cookies

  // Verifica se é uma requisição OPTIONS (preflight request)
  if ("OPTIONS".equals(method)) {
      // Se for uma requisição OPTIONS, apenas responda com status 200 e os cabeçalhos de CORS
      exchange.sendResponseHeaders(200, -1);
      return;
  }


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

            if("/getEmpresaBy".equals(path)){}

            if("/getCozinhaBy".equals(path)){}
            
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
                System.out.println(empresa.toString() );
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
                    response = gson.toJson(addedCozinha);//configurando a resposta json
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
                String erro = null;
                try(InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)) {
                    
                    

                    CNPJRequest cnpjRequest = gson.fromJson(reader, CNPJRequest.class);
                    String cnpj = cnpjRequest.cnpj;

                    if (cnpj == null || cnpj.isEmpty() || cnpj.length()!=14) {
                        erro = "CNPJ não pode ser nulo ou vazio e deve ter 14 digitos.";
                        throw new IllegalArgumentException("");
                        
                    }

                    if (!cnpj.matches("[0-9]+"))
                    {
                        erro = "CNPJ deve ser uma string de inteiros";
                        throw new IllegalArgumentException();
                    }

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
                    System.err.println("Erro ao deletar empresa "+e);
                    e.printStackTrace();
                    response = gson.toJson(erro);
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                }
            }

            
            if("/deleteCozinha".equals(path)){
                String erro = null;
                try(InputStreamReader reader = new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8)) {
                    
                    

                    CNPJRequest cnpjRequest = gson.fromJson(reader, CNPJRequest.class);
                    String cnpj = cnpjRequest.cnpj;

                    if (cnpj == null || cnpj.isEmpty() || cnpj.length()!=14) {
                        erro = "CNPJ não pode ser nulo ou vazio e deve ter 14 digitos.";
                        throw new IllegalArgumentException("");
                        
                    }

                    if (!cnpj.matches("[0-9]+"))
                    {
                        erro = "CNPJ deve ser uma string de inteiros";
                        throw new IllegalArgumentException();
                    }

                    Cozinha deletedCozinha = cozinhaOperations.deleteCozinha(cnpj);
                
                    if (deletedCozinha != null) {
                        response = gson.toJson(deletedCozinha);
                        exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
                    } else {
                        // Retorna erro se nenhuma empresa foi encontrada com o CNPJ fornecido
                        response = gson.toJson("Cozinha com CNPJ " + cnpj + " não encontrada.");
                        exchange.sendResponseHeaders(404, response.getBytes(StandardCharsets.UTF_8).length);
                    }
                
                
                } catch (Exception e) {
                    System.err.println("Erro ao deletar Cozinha "+e);
                    e.printStackTrace();
                    response = gson.toJson(erro);
                    exchange.sendResponseHeaders(400, response.getBytes(StandardCharsets.UTF_8).length);
                }
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