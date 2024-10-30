package puc.pi4.servidorpi4.operations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import puc.pi4.servidorpi4.entities.Person;
import puc.pi4.servidorpi4.repo.PersonRepo;

import java.util.List;

@Service
public class PersonOperations {
    @Autowired
    private PersonRepo personRepo;

    // Método para listar todos os documentos
    public List<Person> getAllPersons() {
        return personRepo.findAll();
    }

    // Método para inserir um novo documento
    public Person addPerson(Person person) {
        return personRepo.save(person);
    }
}
