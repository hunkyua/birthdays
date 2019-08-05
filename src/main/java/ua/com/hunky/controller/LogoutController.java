package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ua.com.hunky.model.User;

@Controller
public class LogoutController {

    @GetMapping("/logout")
    private String logout(@ModelAttribute("user") User user) {
        return "index";
    }

}