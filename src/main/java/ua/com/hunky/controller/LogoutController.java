package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.hunky.model.User;

@Controller
public class LogoutController {

    @RequestMapping(value = "/logout")
    private String logout(@ModelAttribute("user") User user, Model model) {
        return "../../index";
    }

}