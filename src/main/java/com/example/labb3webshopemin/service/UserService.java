package com.example.labb3webshopemin.service;

import com.example.labb3webshopemin.model.User;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class UserService {

        private Map<String, User> users = new HashMap<>();

        public boolean register(String username, String password, String role) {
                if (users.containsKey(username)) return false;
                users.put(username, new User(username, password, role));
                return true;
        }

        public boolean register(String username, String password) {
                return register(username, password, "customer");
        }

        public boolean login(String username, String password) {
                if (!users.containsKey(username)) return false;
                return users.get(username).getPassword().equals(password);
        }

        public User getUser(String username) {
                return users.get(username);
        }
}
