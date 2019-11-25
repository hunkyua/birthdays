package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.UserRepo;

import java.util.Map;

@Controller
@SessionAttributes("user")
public class MainController {
    @Autowired
    UserRepo userRepo;

    @GetMapping("/")
    public String enter(@AuthenticationPrincipal User user, Map<String, Object> model){
        model.put("Error", "");
        model.put("Alert", "");
        if (user != null){
            User userfromDb = userRepo.findByUsername(user.getUsername());
            if (userfromDb != null) {
                model.put("user", user);
                return "menu";
            }
        }
        return "login";
    }

    @PostMapping("/")
    public String login(@AuthenticationPrincipal User user, Map<String, Object> model){
        model.put("Error", "");
        model.put("Alert", "");

        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            return "menu";
        }
        return "login";
    }

    @GetMapping("/menu")
    public String menu( Map<String, Object> model){
        model.put("Error", "");
        model.put("Alert", "");
        return "menu";
    }

    @PostMapping("/menu")
    public String login(@RequestParam String login, @RequestParam String password, Map<String, Object> model) {
        return "menu";
    }


}