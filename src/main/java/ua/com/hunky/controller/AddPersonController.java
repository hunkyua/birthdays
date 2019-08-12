package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepository;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class AddPersonController {
    @Autowired
    PersonRepository personRepository;

    @PostMapping("/createperson")
    private String createPerson(@RequestParam String name, @RequestParam String surname, @RequestParam String email,@RequestParam Date dateOfBirth,  Map<String, Object> model) {
        name = name == null ? "" : name.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        surname = surname == null ? "" : surname.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        Calendar validateDateBefore = new GregorianCalendar(1920,0,1);
        User user = (User) model.get("user");
        List<Person> persons = personRepository.findAllByUserID(user.getUserID());

        if (email.isEmpty() || dateOfBirth == null) {
            return "addPerson";
        }

        for (Person personDB : persons) {
            if (personDB.getEmail().contains(email)) {
                model.put("Error", "Email \"" + email +"\" already exist");
                return "addPerson";
            }
        }

        java.util.Date date = null;
        try {
            date = new SimpleDateFormat("yyyy/MM/dd").parse(String.valueOf(dateOfBirth).replace("-", "/"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Date sqlDate = new Date(date != null ? date.getTime() : 0);

        if (sqlDate.before(validateDateBefore.getTime()) || sqlDate.after(Calendar.getInstance().getTime())) {
            model.put("Error", "Wrong data inside field \"Date of birth\"");
            return "addPerson";
        }

        Person newPerson = new Person(name, surname, email, dateOfBirth, user.getUserID());
        personRepository.save(newPerson);
        model.put("Alert", "Person " + name + " successfully created");

        return "addPerson";
    }

    @GetMapping("/addperson")
    private String moveToAddPerson() {
        return "addPerson";
    }

}
