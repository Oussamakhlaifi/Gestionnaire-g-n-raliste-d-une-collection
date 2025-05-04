package com.example.demo;

public class User {
    private String nom;
    private String email;
    private String role;

    public User(String nom, String email, String role) {
        this.nom = nom;
        this.email = email;
        this.role = role;
    }

    public String getNom() { return nom; }
    public String getEmail() { return email; }
    public String getRole() { return role; }
}
