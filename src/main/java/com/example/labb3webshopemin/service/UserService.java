package com.example.labb3webshopemin.service;

import com.example.labb3webshopemin.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

        // Enkelt "minne" för registrerade användare (username som nyckel)
        private Map<String, User> users = new HashMap<>();

        // Registrera ny användare med valfri roll
        public boolean register(String username, String password, String role) {
                if (users.containsKey(username)) return false;  // Om användarnamn finns returnera false
                users.put(username, new User(username, password, role));  // Annars spara ny användare
                return true;
        }

        // Registrera ny kund (default roll = "customer")
        public boolean register(String username, String password) {
                return register(username, password, "customer");
        }

        // Kontrollera inloggning (användarnamn + lösenord)
        public boolean login(String username, String password) {
                if (!users.containsKey(username)) return false; // Om användarnamn saknas
                return users.get(username).getPassword().equals(password); // Kolla lösenord matchar
        }

        // Hämta användarobjekt baserat på användarnamn
        public User getUser(String username) {
                return users.get(username);
        }
}
