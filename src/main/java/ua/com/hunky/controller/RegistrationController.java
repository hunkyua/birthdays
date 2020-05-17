package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.hunky.model.User;
import ua.com.hunky.service.Messages;
import ua.com.hunky.service.UserService;

import java.util.Map;


@Controller
public class RegistrationController {

    private final UserService userService;

    @Autowired
    private Messages messages;

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
            model.put("Error", messages.get("error.user.UserAlreadyExists", user.getUsername()));
            return "registration";
        }

        model.put("Alert", messages.get("error.user.UserWasAdded", user.getUsername()));
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(@PathVariable String code, Model model) {
        boolean success = userService.activateUser(code);

        if (!success) {
            model.addAttribute("Error", messages.get("error.user.ActivationCodeIsNotFound"));
            return "login";
        }

        model.addAttribute("Alert", messages.get("error.user.UserSuccessfullyActivated"));
        return "login";

    }


}
