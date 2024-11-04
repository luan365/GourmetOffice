package puc.pi4.Controllers;


import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import puc.pi4.Entities.Person;
import puc.pi4.Operations.PersonOperations;

public class Controller implements HttpHandler {
    private final PersonOperations personOperations = new PersonOperations();
    private final Gson gson = new Gson();

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String path = exchange.getRequestURI().getPath();
        String response = "";

        System.out.println(exchange.getRequestURI().getPath());

        if ("/getAllPeople".equals(path)) {
            List<Person> persons = personOperations.getAllPersons();
            response = gson.toJson(persons);
            exchange.sendResponseHeaders(200, response.getBytes(StandardCharsets.UTF_8).length);
        } else if ("/insertPerson".equals(path)) {
            Person person = gson.fromJson(new InputStreamReader(exchange.getRequestBody(), StandardCharsets.UTF_8), Person.class);
            Person addedPerson = personOperations.addPerson(person);
            response = gson.toJson(addedPerson);
            exchange.sendResponseHeaders(201, response.getBytes(StandardCharsets.UTF_8).length);
        } else {
            exchange.sendResponseHeaders(405, -1); // Método não permitido
            return;
        }

        try (OutputStream os = exchange.getResponseBody()) {
            os.write(response.getBytes(StandardCharsets.UTF_8));
        }
    }
}