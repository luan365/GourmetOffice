package puc.pi4.Controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import puc.pi4.Entities.Cozinha;
import puc.pi4.Entities.Empresa;
import puc.pi4.Operations.CozinhaOperations;
import puc.pi4.Operations.EmpresaOperations;

public class Controller {
    //Instanciando classes para operações com as entidades
    private final EmpresaOperations empresaOperations = new EmpresaOperations();
    private final CozinhaOperations cozinhaOperations = new CozinhaOperations();
    private final Gson gson = new Gson();

    //Iniciando socket e bufferedReader
    private final Socket clientSocket;
    private final BufferedReader reader;
    private final PrintWriter writer;

    // "construtor" do controller, inicia o socket e os readers.
    public Controller(Socket clientSocket) throws IOException {
        this.clientSocket = clientSocket;
        this.reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        this.writer = new PrintWriter(clientSocket.getOutputStream(), true);
    }

    //Metodo chamado em "serverjava.java", basicamente e o que escuta requisicoes a qualquer momento
    public void handleRequest() throws IOException {
        



        String requestLine = reader.readLine();
        if (requestLine == null) {
            return;
        }

        // Concatenando a request
        String[] requestParts = requestLine.split(" ");
        String method = requestParts[0]; // Metodo (GET,DELETE...)
        String path = requestParts[1]; // Caminho (GetAllEmpresas, DeleteEmpresa....)

       
        if ("OPTIONS".equals(method)) {
            // Adiciona os headers do CORS
            
            writer.println("HTTP/1.1 200 OK");
            writer.println("Access-Control-Allow-Origin: http://localhost:5173");
            writer.println("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
            writer.println("Access-Control-Allow-Headers: Content-Type");
            writer.println("Access-Control-Allow-Credentials: true");
            writer.println(""); 
            return;
        }

        
        //Flag para saber se caiu em algum metodo valido
        boolean handled = false;

        
        if ("GET".equals(method)) {
            
            handled = true;
            if ("/getAllEmpresas".equals(path)) {
                try {
                    List<Empresa> empresas = empresaOperations.getAllEmpresas();

                    //Retornando resposta da requisicao
                    writer.println("HTTP/1.1 200 OK");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println(""); // vazio para sinalizar final do header
                    writer.println(gson.toJson(empresas)); //body
                } catch (Exception e) {
                    System.err.println("Erro ao listar empresas: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro ao listar empresas "));
                }
            } 
            else if ("/getAllCozinhas".equals(path)) {
                try {
                    List<Cozinha> cozinhas = cozinhaOperations.getAllCozinhas();

                    writer.println("HTTP/1.1 200 OK");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson(cozinhas));
                } catch (Exception e) {
                    System.err.println("Erro ao listar cozinhas: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro ao listar cozinhas "));
                }
            } else {
                unknownPath();
            }
        }
        
        if ("PUT".equals(method)) {
            
            handled = true;
            String[] validate = null;
            if ("/insertEmpresa".equals(path)) {
                try {
                    //------------Separa o body da requisicao HTTP-----------
                    int contentLength = getContentLength();
                    if (contentLength == 0) {
                        throw new IllegalArgumentException("Content-Length não especificado ou é zero.");
                    }
                    String requestBody = readRequestBody(contentLength);
                    //--------------------------------------------------------

                    Empresa empresa = gson.fromJson(requestBody, Empresa.class);
                    validate = empresa.validate();

                    //Funcao que valida as informacoes do objeto da classe empresa, porque o gson, que transforma JSON em objetos JAVA, acaba nao usando o construtor da classe EMPRESA.
                    if(validate[1] == "false"){
                        throw new Exception(validate[0]);
                    }
                        empresaOperations.insertEmpresa(empresa);
                    

                    writer.println("HTTP/1.1 200 Created");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson(empresa));

                } catch (Exception e) {
                    System.err.println("Erro ao inserir empresa: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro a inserir empresa: " + validate[0]));
                }
                
            } else if ("/insertCozinha".equals(path)) {
                try {
                    int contentLength = getContentLength();
                    if (contentLength == 0) {
                        throw new IllegalArgumentException("Content-Length não especificado ou é zero.");
                    }
                    String requestBody = readRequestBody(contentLength);

                    Cozinha cozinha = gson.fromJson(requestBody, Cozinha.class);
                    validate = cozinha.validate();

                    if(validate[1] == "false"){
                        throw new Exception(validate[0]);
                    }
                        cozinhaOperations.insertCozinha(cozinha);
                    

                    writer.println("HTTP/1.1 200 Created");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson(cozinha));

                } catch (Exception e) {
                    System.err.println("Erro ao inserir Cozinha: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro a inserir cozinha: " + validate[0]));
                }
            } else {
                unknownPath();
            }
        }

        if ("DELETE".equals(method)) {
            
            handled = true;
            
            if ("/deleteEmpresa".equals(path)) {
                try {
                    int contentLength = getContentLength();
                    if (contentLength == 0) {
                        throw new IllegalArgumentException("Content-Length não especificado ou é zero.");
                    }
                    String requestBody = readRequestBody(contentLength);
                    JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
                    if (!jsonObject.has("cnpj")) {
                        throw new IllegalArgumentException("CNPJ não encontrado no corpo da requisição.");
                    }

                    String cnpj = jsonObject.get("cnpj").getAsString();

                    if (cnpj == null || cnpj.isEmpty() || cnpj.length()!=14) {
                        throw new IllegalArgumentException("CNPJ nao pode ser nulo ou vazio e deve ter 14 digitos.");
                    }

                    if (!cnpj.matches("[0-9]+"))
                    {
                        throw new IllegalArgumentException("CNPJ nao deve ter caracteres nao numericos");
                    }

                    Empresa deletedEmpresa = empresaOperations.deleteEmpresa(cnpj);

                    if(deletedEmpresa != null){
                        writer.println("HTTP/1.1 200 Deleted");
                        addCorsHeaders();
                        writer.println("Content-Type: application/json");
                        writer.println("");
                        writer.println(gson.toJson(deletedEmpresa));
                    }else{
                        writer.println("HTTP/1.1 200 Delete Failed");
                        addCorsHeaders();
                        writer.println("Content-Type: application/json");
                        writer.println("");
                        writer.println("Nenhuma empresa encontrada com CNPJ "+cnpj);
                    }

                    

                } catch (Exception e) {
                    System.err.println("Erro ao deletar empresa: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro ao deletar empresa: cnpj invalido"));
                    
                }
                
            } else if ("/deleteCozinha".equals(path)) {
                try {

                    int contentLength = getContentLength();
                    if (contentLength == 0) {
                        throw new IllegalArgumentException("Content-Length não especificado ou é zero.");
                    }
                    String requestBody = readRequestBody(contentLength);
                    JsonObject jsonObject = JsonParser.parseString(requestBody).getAsJsonObject();
                    if (!jsonObject.has("cnpj")) {
                        throw new IllegalArgumentException("CNPJ não encontrado no corpo da requisição.");
                    }

                    String cnpj = jsonObject.get("cnpj").getAsString();

                    if (cnpj == null || cnpj.isEmpty() || cnpj.length()!=14) {
                        throw new IllegalArgumentException("CNPJ nao pode ser nulo ou vazio e deve ter 14 digitos.");
                    }

                    if (!cnpj.matches("[0-9]+"))
                    {
                        throw new IllegalArgumentException("CNPJ nao deve ter caracteres nao numericos");
                    }

                    Cozinha deletedCozinha = cozinhaOperations.deleteCozinha(cnpj);

                    if(deletedCozinha != null){
                        writer.println("HTTP/1.1 200 Deleted");
                        addCorsHeaders();
                        writer.println("Content-Type: application/json");
                        writer.println("");
                        writer.println(gson.toJson(deletedCozinha));
                    }else{
                        writer.println("HTTP/1.1 200 Delete Failed");
                        addCorsHeaders();
                        writer.println("Content-Type: application/json");
                        writer.println("");
                        writer.println("Nenhuma Cozinha* encontrada com CNPJ "+cnpj);
                    }




                } catch (Exception e) {
                    System.err.println("Erro ao deletar cozinha: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro ao deletar cozinha: cnpj invalido"));
                }
            } else {
                unknownPath();
            }
        }

        if ("PATCH".equals(method)) {
            
            handled = true;
            String[] validate = null;
            if ("/updateEmpresa".equals(path)) {
                try {
                    int contentLength = getContentLength();
                    if (contentLength == 0) {
                        throw new IllegalArgumentException("Content-Length não especificado ou é zero.");
                    }
                    String requestBody = readRequestBody(contentLength);

                    Empresa empresaAtualizada = gson.fromJson(requestBody, Empresa.class);
                    validate = empresaAtualizada.validate();

                    if(validate[1] == "false"){
                        throw new Exception(validate[0]);
                    }

                    String cnpjEmpresa = empresaAtualizada.getCNPJ();
                   
                  
                    empresaOperations.updateEmpresa(empresaAtualizada, cnpjEmpresa);
                    

                    writer.println("HTTP/1.1 200 Updated");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson(empresaAtualizada));

                } catch (Exception e) {
                    System.err.println("Erro ao atualizar empresa: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro ao atualizar empresa: " + validate[0]));
                }
                
            } else if ("/updateCozinha".equals(path))
            {
                try {
                    int contentLength = getContentLength();
                    if (contentLength == 0) {
                        throw new IllegalArgumentException("Content-Length não especificado ou é zero.");
                    }
                    String requestBody = readRequestBody(contentLength);

                    Cozinha cozinhaAtualizada = gson.fromJson(requestBody, Cozinha.class);
                    validate = cozinhaAtualizada.validate();

                    if(validate[1] == "false"){
                        throw new Exception(validate[0]);
                    }

                    String cnpjCozinha = cozinhaAtualizada.getCNPJ();
                   
                  
                    cozinhaOperations.updateCozinha(cozinhaAtualizada, cnpjCozinha);
                    

                    writer.println("HTTP/1.1 200 Updated");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson(cozinhaAtualizada));

                } catch (Exception e) {
                    System.err.println("Erro ao atualizar Cozinha: " + e);
                    e.printStackTrace();
                    writer.println("HTTP/1.1 400 Bad Request");
                    addCorsHeaders();
                    writer.println("Content-Type: application/json");
                    writer.println("");
                    writer.println(gson.toJson("Erro ao atualizar cozinha: " + validate[0]));
                }
            } else {
                unknownPath();
            }
        }

        
        if(!handled){sendNotFoundResponse();}

        // Fecha o socket
        
        clientSocket.close();
    }

    //------------------------------------------------ FUNCOES SEPARADAS PARA LIMPAR O CODIGO-------------------------------------------


    private void sendNotFoundResponse() {
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/plain");
        writer.println(""); 
        writer.println("404 No Method Found");
    }

    private void unknownPath() {
        writer.println("HTTP/1.1 404 Not Found");
        writer.println("Content-Type: text/plain");
        writer.println("");
        writer.println("404 Path not found");
    }


    private String readRequestBody(int contentLength) throws IOException {
        char[] buffer = new char[contentLength];
        reader.read(buffer, 0, contentLength);
        return new String(buffer);
    }
    

    private int getContentLength() throws IOException {
        String line;
        int contentLength = 0;
    
        while ((line = reader.readLine()) != null && !line.isEmpty()) {
            if (line.startsWith("Content-Length:")) {
                contentLength = Integer.parseInt(line.split(":")[1].trim());
            }
        }
    
        return contentLength;
    }
    
    private void addCorsHeaders() {
        writer.println("Access-Control-Allow-Origin: http://localhost:5173");
        writer.println("Access-Control-Allow-Methods: GET, POST, PUT, DELETE, OPTIONS");
        writer.println("Access-Control-Allow-Headers: Content-Type");
        writer.println("Access-Control-Allow-Credentials: true"); // Para permitir cookies, se necessário
    }
    

}
