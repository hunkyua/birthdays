package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.hunky.model.ROLE;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.UserRepository;

import java.util.Map;


@Controller
public class RegistrationController {
    @Autowired
    UserRepository userRepository;

    @PostMapping("/doRegistration")
    private String registerNewUser(@RequestParam String login, @RequestParam String password, Map<String, Object> model) {
        login = login == null ? "" : login.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        password = password == null ? "" : password.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        Iterable<User> users = userRepository.findAll();

        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                model.put("Error", "Sorry login is already exist!");
                return "registration";
            } else {
                if (login.isEmpty() && password.isEmpty()) {
                    return "registration";
                }
            }
        }

        User newUser = new User(login, password, ROLE.USER);
        userRepository.save(newUser);
        model.put("Alert", "User " + login + " was added");
        return "index";
    }

    @GetMapping("/registration")
    private String registerNewUser() {
        return "registration";
    }
}
