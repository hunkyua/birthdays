package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ua.com.hunky.dao.DaoFactory;
import ua.com.hunky.dao.UserDAO;
import ua.com.hunky.model.ROLE;
import ua.com.hunky.model.User;


@Controller
public class RegistrationController {

    @PostMapping(value = "/doRegistration")
    private String registerNewUser(@ModelAttribute("user") User user, Model model) {
        DaoFactory daoFactory = new DaoFactory();
        UserDAO userDAO = new UserDAO(daoFactory);
        String login = user.getLogin();
        String password = user.getPassword();
        login = login == null ? "" : login.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        password = password == null ? "" : password.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        User newUser = userDAO.getUserByLoginPassword(login,password);

        boolean isUserExist = newUser.isUserExist();

        if (isUserExist) {
            model.addAttribute("Error", "Sorry login is already exist!");
            return "registration";
        } else {
            if (login.isEmpty() && password.isEmpty()) {
                return "registration";
            }
            newUser.setLogin(login);
            newUser.setPassword(password);
            newUser.setRole(ROLE.USER);
            userDAO.createUser(newUser);
            model.addAttribute("Alert","User " + login + " was added");
            return "index";
        }
    }

    @GetMapping(value = "/registration")
    private String registerNewUser() {
        return "registration";
    }
}
