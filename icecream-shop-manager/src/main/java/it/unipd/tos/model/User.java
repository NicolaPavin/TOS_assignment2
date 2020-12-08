////////////////////////////////////////////////////////////////////
// Nicola Pavin 1193215
////////////////////////////////////////////////////////////////////
package it.unipd.tos.model;

public class User {
    
    private final int id;
    private final String name;
    private final String surname;
    private final int age;

    public User(int id, String name, String surname, int age) 
    throws IllegalArgumentException {

        if(age < 0) {
            throw new IllegalArgumentException();
        }
        
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public String getFullName() {
        return name + " " + surname;
    }

    public int getAge() {
        return age;
    }

}