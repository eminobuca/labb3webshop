package com.example.labb3webshopemin.model;

// Representerar en användare i systemet, med användarnamn, lösenord och roll
public class User {
    private String username;    // Användarnamn, unikt för varje användare
    private String password;    // Lösenordet sparas som text här, men i en riktig app bör det alltid krypteras (hashas) för säkerhetens skull.
    private String role;        // Roll, t.ex. "customer" eller "admin"

    public User() {}

    // Konstruktor med fält
    public User(String username, String password, String role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // Getters för fälten
    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public String getRole() { return role; }

    // Setter för fälten
    public void setUsername(String username) { this.username = username; }
}
