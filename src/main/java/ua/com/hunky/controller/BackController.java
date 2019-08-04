package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.User;

@Controller
@SessionAttributes("user")
public class BackController {

    @GetMapping(value = "/back")
    private String back(@ModelAttribute("user") User user) {
        System.out.println(user.getLogin());
        System.out.println(user.getPassword());
        System.out.println(user.getRole());
        return "index";
    }

}
