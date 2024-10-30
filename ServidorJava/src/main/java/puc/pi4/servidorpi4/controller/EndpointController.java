package puc.pi4.servidorpi4.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import puc.pi4.servidorpi4.entities.Person;
import puc.pi4.servidorpi4.operations.PersonOperations;

import java.util.List;

@RestController
@RequestMapping("/api/people")
public class EndpointController {
    @Autowired
    private PersonOperations personoperations;

    // Endpoint para listar todos os documentos
    @GetMapping
    public List<Person> getAllPersons() {
        return personoperations.getAllPersons();
    }

    // Endpoint para inserir um novo documento
    @PostMapping
    public Person addPerson(@RequestBody Person person) {
        return personoperations.addPerson(person);
    }
}
