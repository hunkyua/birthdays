package ua.com.hunky.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.dao.DaoFactory;
import ua.com.hunky.dao.PersonDAO;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@Controller
@SessionAttributes("user")
public class AddPersonController {

    @RequestMapping(value = "/createperson", method = RequestMethod.POST)
    private String createPerson(@ModelAttribute("person") Person person, Model model) {
        DaoFactory daoFactory = new DaoFactory();
        PersonDAO personDAO = new PersonDAO(daoFactory);
        String name = person.getName();
        String surname = person.getSurname();
        String email = person.getEmail();
        String dateOfBirthday = person.getDateOfBirth();
        name = name == null ? "" : name.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        surname = surname == null ? "" : surname.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        Date date = null;
        Calendar validateDateBefore = new GregorianCalendar(1920,0,1);

        if(email.isEmpty() || dateOfBirthday.isEmpty()) {
            return "addPerson";
        }

        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(dateOfBirthday.replace("-", "/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (date != null && (date.before(validateDateBefore.getTime()) || date.after(Calendar.getInstance().getTime()))) {
            model.addAttribute("Error", "Wrong data inside field \"Date of birth\"");
            return "addPerson";
        }
        User user = (User) model.asMap().get("user");
        int userID = user.getUserID();
        Person newPerson = new Person(name, surname, email, dateOfBirthday, userID);
        personDAO.addPerson(newPerson);

        model.addAttribute("Alert", "Person " + person.getName() + " successfully created");
        return "addPerson";
    }

}
