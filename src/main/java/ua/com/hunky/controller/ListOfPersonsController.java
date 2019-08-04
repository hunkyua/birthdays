package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.dao.DaoFactory;
import ua.com.hunky.dao.PersonDAO;
import ua.com.hunky.dao.UserDAO;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;

import java.util.List;

@Controller
@SessionAttributes("user")
public class ListOfPersonsController {

    @GetMapping(value = "/listofpersons")
    private String getListOfPersons(User user, Model model) {
        DaoFactory daoFactory = new DaoFactory();
        PersonDAO personDAO = new PersonDAO(daoFactory);
        UserDAO userDAO = new UserDAO(daoFactory);
        List<Person> persons = personDAO.getAllPersonsByUserId(userDAO.getUserId(user));
        model.addAttribute("persons", persons.listIterator());

        return "listOfPersons";
    }


}
