package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    UserService userService;

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(User user, Map<String, Object> model) {
        if (!userService.addUser(user)) {
            model.put("Error", "User " + user.getUsername() + " already exists!");
            return "registration";
        }

        model.put("Alert", "User " + user.getUsername() + " was added");
        return "redirect:/login";
    }

    @GetMapping("/activate/{code}")
    public String activate(Model model, @PathVariable String code) {
        boolean isActivated = userService.activateUser(code);

        if (isActivated) {
            model.addAttribute("Alert", "User successfully activated");
        } else {
            model.addAttribute("Error", "Activation code is not found");
        }

        return "login";
    }


}
