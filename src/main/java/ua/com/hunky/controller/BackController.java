package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.User;

@Controller
@SessionAttributes("user")
public class BackController {

    @GetMapping("/backFromListOfPersons")
    private String back(@ModelAttribute("user") User user) {
        switch (user.getRole()) {
            case USER : return "user_menu";
            case ADMIN : return  "admin_menu";
            default: return "index";
        }
    }

}
