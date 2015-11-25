package com.joaopires.servidor_intro;

import java.io.Serializable;

public class Cliente implements Serializable{
    private static final long serialVersionUID = 1L;
    protected int clienteID;
    protected String firstName;
    protected String lastName;
    protected String street;
    protected String city;
    public Cliente(int id) {
        clienteID = id;
    }
    public Cliente() {
        clienteID = -1;
    }
    public void setId(int id) {
        clienteID = id;
    }
    public int getId() {
        return clienteID;
    }
    public void setFirstName(String s) {
        firstName = s;
    }
    public String getFirstName() {
        return firstName;
    }
    public void setLastName(String s) {
        lastName = s;
    }
    public String getLastName() {
        return lastName;
    }
    public void setStreet(String s) {
        street = s;
    }
    public String getStreet() {
        return street;
    }
    public void setCity(String s) {
        city = s;
    }
    public String getCity() {
        return city;
    }
    @Override
    public String toString() {
        return clienteID + ": " + firstName + lastName;
    }
    public String getMorada() {
        return street + ", " + city;
    }
}