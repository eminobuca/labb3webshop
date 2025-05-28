package com.example.labb3webshopemin.controll;

import com.example.labb3webshopemin.model.User;
import com.example.labb3webshopemin.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        boolean success = userService.register(user.getUsername(), user.getPassword());

        if (success) {
            // Skicka tillbaka till login med success-parameter
            return "redirect:/?success=true";
        } else {
            model.addAttribute("error", true);
            return "register";
        }
    }

}