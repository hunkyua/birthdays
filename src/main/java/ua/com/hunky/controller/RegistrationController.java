package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.hunky.model.Role;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.UserRepo;

import java.util.Collections;
import java.util.Map;


@Controller
public class RegistrationController {
    @Autowired
    UserRepo userRepo;

    @GetMapping("/registration")
    private String registration() {
        return "registration";
    }

    @PostMapping("/registration")
    private String addUser(User user, Map<String, Object> model) {
        User userFromDb = userRepo.findByUsername(user.getUsername());

        if (userFromDb != null) {
            model.put("Error", "User " + userFromDb.getUsername() + " already exists!");
            return "registration";
        }

        user.setActive(true);
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
        model.put("Alert", "User " + user.getUsername() + " was added");
        return "redirect:/login";
    }


}
