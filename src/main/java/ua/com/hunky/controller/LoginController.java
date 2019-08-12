package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.ROLE;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.UserRepository;

@Controller
@SessionAttributes("user")
public class LoginController {
    @Autowired
    UserRepository userRepository;

    @GetMapping("/")
    public String enter(Model model){
        model.addAttribute("Error", "");
        model.addAttribute("Alert", "");
        return "index";
    }

    @PostMapping("/")
    public String login(@RequestParam String login, @RequestParam String password, Model model) {
        Iterable<User> users = userRepository.findAll();
        User sessionUser = new User();
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                sessionUser = user;
            }
        }

        if(!sessionUser.isUserExist()) {
                model.addAttribute("Error", "User doesn't not exist :( Try again.");
        }
        model.addAttribute("user", sessionUser);
        return moveToMenu(sessionUser.getRole());
    }

    /**
     * Move user to menu.
     * If access 'admin' move to admin menu.
     * If access 'user' move to user menu.
     */
    private String moveToMenu(final ROLE role) {
        switch (role) {
            case ADMIN:
                return "admin_menu";
            case USER:
                return "user_menu";
            default:
                return "index";
        }
    }

}