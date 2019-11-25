package ua.com.hunky.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.User;

@Controller
@SessionAttributes("user")
public class BackController {

    @GetMapping("/backFromListOfPersons")
    private String back(@AuthenticationPrincipal User user) {
        switch (user.getRoles().iterator().next().name()) {
            case "USER" : return "menu";
            default: return "index";
        }
    }

}
