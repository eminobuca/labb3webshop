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

    // Visa inloggningssidan, lägger till ett tomt User-objekt för formuläret
    @GetMapping("/")
    public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

    // Hantera inloggning med POST, kollar användarnamn och lösenord
    @PostMapping("/login")
    public String login(@ModelAttribute("user") User user, HttpSession session) {
        boolean success = userService.login(user.getUsername(), user.getPassword());
        if (success) {
            User fullUser = userService.getUser(user.getUsername());

            // Spara inloggningsstatus, roll och användarnamn i sessionen
            session.setAttribute("loggedIn", true);
            session.setAttribute("role", fullUser.getRole());
            session.setAttribute("username", fullUser.getUsername());

            // Om admin, skicka till adminvyn, annars till kundvyn
            if ("admin".equals(fullUser.getRole())) {
                return "redirect:/admin/products";
            } else {
                return "redirect:/products";
            }
        }
        // Om inloggning misslyckas, skicka tillbaka till login med error-parameter
        return "redirect:/?error=true";
    }

    // Logga ut användaren, ta bort sessionen och skicka till login-sidan
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }
}
