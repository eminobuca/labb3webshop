package com.example.labb3webshopemin.controll;

import com.example.labb3webshopemin.model.User;
import com.example.labb3webshopemin.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class LoginController {

    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session) {
        boolean success = userService.login(user.getUsername(), user.getPassword());
        if (success) {
            User fullUser = userService.getUser(user.getUsername());
            session.setAttribute("loggedIn", true);
            session.setAttribute("role", fullUser.getRole());
            session.setAttribute("username", fullUser.getUsername());

            if ("admin".equals(fullUser.getRole())) {
                return "redirect:/admin/products"; // Går till adminvy
            } else {
                return "redirect:/products"; // Går till kundens vy
            }
        }
        return "redirect:/?error=true";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}