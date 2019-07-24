package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.dao.DaoFactory;
import ua.com.hunky.dao.PersonDAO;
import ua.com.hunky.dao.UserDAO;
import ua.com.hunky.model.User;

@Controller
@SessionAttributes("user")
public class ListOfPersonsController {

    @RequestMapping(value = "/listofpersons", method = RequestMethod.GET)
    private String getListOfPersons(User user, Model model) {
        DaoFactory daoFactory = new DaoFactory();
        PersonDAO personDAO = new PersonDAO(daoFactory);
        UserDAO userDAO = new UserDAO(daoFactory);
        model.addAttribute("persons", personDAO.getAllPersonsByUserId(userDAO.getUserId(user)));

        return "listOfPersons";
    }

    @RequestMapping(value = "/addperson")
    private String moveToAddPerson() {
        return "addPerson";
    }
}
