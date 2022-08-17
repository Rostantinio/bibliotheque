package com.biblio.bibliotheque.model;

public class Authors {
    private int id;
    private String firstName;
    private String lastName;

    public Authors() {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Authors(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
