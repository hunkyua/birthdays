package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.hunky.model.User;
import ua.com.hunky.service.UserService;

import java.util.Map;


@Controller
public class RegistrationController {

    private final UserService userService;

    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(User user, Map<String, Object> model) {
        boolean success = userService.addUser(user);

        if (!success) {
            model.put("Error", "User " + user.getUsername() + " already exists!");
            return "registration";
        }

        model.put("Alert", "User " + user.getUsername() + " was added");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean success = userService.activateUser(code);

        if (!success) {
            model.addAttribute("Error", "Activation code is not found");
            return "login";
        }

        model.addAttribute("Alert", "User successfully activated");
        return "login";

    }


}
