package ua.com.hunky.controller;

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

    private final UserRepo users;

    public MainController(UserRepo users) {
        this.users = users;
    }

    @GetMapping("/")
    public String enter(@AuthenticationPrincipal User auth, Map<String, Object> model) {

        cleanWarnings(model);

        if (auth == null) {
            return "login";
        }

        User user = users.findByUsername(auth.getUsername());

        if (user == null) {
            return "login";
        }

        if (user.getActivationCode() != null) {
            model.put("Error", "User is not activated");
            return "login";
        }

        model.put("user", auth);
        return "menu";

    }

    @PostMapping("/")
    public String login(@AuthenticationPrincipal User auth, Map<String, Object> model) {

        cleanWarnings(model);

        User user = users.findByUsername(auth.getUsername());

        return user == null ? "login" : "menu";

    }

    private void cleanWarnings(Map<String, Object> model) {
        model.put("Error", "");
        model.put("Alert", "");
    }

    @GetMapping("/menu")
    public String menu(Map<String, Object> model) {
        cleanWarnings(model);
        return "menu";
    }

    @PostMapping("/menu")
    public String login(
            @RequestParam String login,
            @RequestParam String password,
            Map<String, Object> model) {

        return "menu";
    }


}