package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.dao.DaoFactory;
import ua.com.hunky.dao.UserDAO;
import ua.com.hunky.model.ROLE;
import ua.com.hunky.model.User;

@Controller
@SessionAttributes("user")
public class LoginController {

    @ModelAttribute("user")
    public User setUpUser() {
        return new User();
    }

    @RequestMapping(value = "/menu")
    public String login(@ModelAttribute("user") User user, Model model) {
        DaoFactory daoFactory = new DaoFactory();
        UserDAO userDAO = new UserDAO(daoFactory);
        user = userDAO.getUserByLoginPassword(user.getLogin(), user.getPassword());
        user.setRole(userDAO.getUserRole(user));

        if(!user.isUserExist()) {
            model.addAttribute("error", "User doesn't not exist :( Try again.");
        }
        model.addAttribute("user", user);
        return moveToMenu(user.getRole());
    }

    @RequestMapping(value = "/index")
    public String index(Model model) {
        return "forward:index.jsp";
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
                return "../../index";
        }
    }

}