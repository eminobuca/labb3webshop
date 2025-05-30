package com.example.labb3webshopemin.controll;

import com.example.labb3webshopemin.model.User;
import com.example.labb3webshopemin.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class RegisterController {

    @Autowired
    private UserService userService;

    // Visa registreringsformuläret
    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User()); // Skapa ett tomt User-objekt till formuläret
        return "register"; // Visa sidan register.html
    }

    // Hantera registrering när användaren skickar formuläret
    @PostMapping("/register")
    public String register(@ModelAttribute User user, Model model) {
        boolean success = userService.register(user.getUsername(), user.getPassword());

        if (success) {
            // Om registreringen lyckas, skicka tillbaka till login-sidan med en parameter som visar lyckat resultat
            return "redirect:/?success=true";
        } else {
            // Om användarnamnet redan finns, visa registreringssidan igen och visa felmeddelande
            model.addAttribute("error", true);
            return "register";
        }
    }

}
