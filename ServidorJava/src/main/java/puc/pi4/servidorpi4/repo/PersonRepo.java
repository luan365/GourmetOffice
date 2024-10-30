package puc.pi4.servidorpi4.repo;
import org.springframework.data.mongodb.repository.MongoRepository;
import puc.pi4.servidorpi4.entities.Person;

public interface PersonRepo extends MongoRepository<Person, String> {}
