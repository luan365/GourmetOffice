package puc.pi4.Entities;

import org.bson.types.ObjectId;

public class Person {
    private ObjectId id;
    private String name;
    private int age;

    // Construtores
    public Person() {}
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // Getters e Setters
    public ObjectId getId() { return id; }
    public void setId(ObjectId id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public int getAge() { return age; }
    public void setAge(int age) { this.age = age; }
}
