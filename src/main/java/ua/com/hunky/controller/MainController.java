package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.UserRepo;
import ua.com.hunky.service.Messages;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class MainController {

    private final UserRepo users;

    @Autowired
    private Messages messages;

    public MainController(UserRepo users) {
        this.users = users;
    }

    @GetMapping("/")
    public String enter(
            @AuthenticationPrincipal User auth,
            Map<String, Object> model) {

        cleanWarnings(model);

        if (auth == null) {
            return "login";
        }

        User user = users.findByUsername(auth.getUsername());

        if (user == null) {
            return "login";
        }

        if (user.getActivationCode() != null) {
            model.put("Error", messages.get("error.user.UserIsNotActivated"));
            return "login";
        }

        model.put("user", auth);
        return "menu";

    }

    @PostMapping("/")
    public String login(
            @AuthenticationPrincipal User auth,
            Map<String, Object> model) {

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
    public String login() {
        return "menu";
    }

    @GetMapping("/login")
    public String loginError(
            @AuthenticationPrincipal User auth,
            Map<String, Object> model) {

        cleanWarnings(model);

        if (auth == null) {
            model.put("Error", messages.get("error.user.UserNotExistsOrWrongPassword"));
            return "login";
        }

        return "menu";
    }

    @GetMapping("/logout")
    public String getLogoutPage(HttpServletRequest request, HttpServletResponse response){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }

        return "redirect:/";
    }


}