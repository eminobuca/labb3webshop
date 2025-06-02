package com.example.labb3webshopemin.service;

import com.example.labb3webshopemin.model.User;  // viktig import!
import com.example.labb3webshopemin.repository.UserRepository;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

@Service
@Scope(value = WebApplicationContext.SCOPE_SESSION)
public class UserService {

        private final UserRepository userRepository;

        private User loggedInUser;

        public UserService(UserRepository userRepository) {
                this.userRepository = userRepository;
        }

        public boolean login(String username, String password) {
                User user = userRepository.findByUsername(username);
                if (user != null && user.getPassword().equals(password)) {
                        this.loggedInUser = user;
                        return true;
                }
                return false;
        }

        public void logout() {
                this.loggedInUser = null;
        }

        public User getLoggedInUser() {
                return loggedInUser;
        }

        public User register(User user) {
                return userRepository.save(user);
        }

        public boolean isLoggedIn() {
                return loggedInUser != null;
        }
}
