package ua.com.hunky.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class PersonController {
    private final PersonRepo personRepo;

    public PersonController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @PostMapping("/createperson")
    private String createPerson(@AuthenticationPrincipal User user , @RequestParam String name, @RequestParam String surname, @RequestParam String email, @RequestParam Date dateOfBirth, Map<String, Object> model) {
        name = name == null ? "" : name.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        surname = surname == null ? "" : surname.replaceAll("<", "&lt;").replaceAll(">", "&gt;");
        Calendar validateDateBefore = new GregorianCalendar(1920,0,1);
        List<Person> persons = personRepo.findAllByUserID(user.getId());

        if (dateOfBirth == null) {
            return "addPerson";
        }

        for (Person personDB : persons) {
            if (personDB.getEmail().equals(email) && !email.isBlank()) {
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

        Person newPerson = new Person(name, surname, email, dateOfBirth, user.getId());
        personRepo.save(newPerson);
        model.put("Alert", "Person " + name + " successfully created");

        return "addPerson";
    }

    @GetMapping("/addperson")
    private String moveToAddPerson() {
        return "addPerson";
    }

    @GetMapping("/listofpersons")
    private String listOfPersons(@AuthenticationPrincipal User user, Map<String, Object> model) {
        List<Person> persons = personRepo.findAllByUserID(user.getId());
        model.put("persons", persons.listIterator());

        return "listOfPersons";
    }

    @PostMapping("/removepersons")
    private String removepersons(@RequestParam(value = "isChecked", required = false) List<Long> personId) {
        if (personId != null) {
            for (Long id : personId) {
                Person person = personRepo.findPersonById(id);
                if (person != null) {
                    personRepo.delete(person);
                }
            }
        }

        return "redirect:listofpersons";
    }

}
