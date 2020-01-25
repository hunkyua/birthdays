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
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("user")
public class PersonController {
    private static final java.util.Date THE_BEGINNING = new GregorianCalendar(1920, Calendar.JANUARY, 1).getTime();
    public static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy/MM/dd");
    private final PersonRepo personRepo;

    public PersonController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @PostMapping("/createPerson")
    private String createPerson(@AuthenticationPrincipal User auth,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String email,
                                @RequestParam Date birthday,
                                Map<String, Object> model) {


        if (birthday == null) {
            return "addPerson";
        }

        List<Person> persons = personRepo.findAllByUserID(auth.getId());

        if (isEmailAlreadyExists(persons, email)) {
            model.put("Error", "Email \"" + email + "\" already exist");
            return "addPerson";
        }

        Date sqlDate = new Date(birthday.getTime());

        if (!isValid(sqlDate)) {
            model.put("Error", "Wrong data inside field \"Date of birth\"");
            return "addPerson";
        }

        Person newPerson = new Person(parse(name), parse(surname), email, birthday, auth.getId());
        personRepo.save(newPerson);
        model.put("Alert", "Person " + name + " successfully created");

        return "addPerson";
    }

    private boolean isEmailAlreadyExists(List<Person> persons, String email) {
        return persons.stream()
                .anyMatch(p -> p.isEmailAlreadyExists(email));
    }

    private boolean isValid(Date date) {
        if (date.before(THE_BEGINNING)) {
            return false;
        }

        if (date.after(now())) {
            return false;
        }

        return true;
    }

    private java.util.Date now() {
        return Calendar.getInstance().getTime();
    }

    private String parse(@RequestParam String value) {
        if (value == null) {
            return "";
        }

        return value.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;");
    }

    @GetMapping("/addperson")
    private String moveToAddPerson() {
        return "addPerson";
    }

    @GetMapping("/listofpersons")
    private String listOfPersons(@AuthenticationPrincipal User auth, Map<String, Object> model) {
        List<Person> persons = personRepo.findAllByUserID(auth.getId());

        model.put("persons", persons.listIterator());
        return "listOfPersons";
    }

    @PostMapping("/removePersons")
    private String removePersons(@RequestParam(value = "isChecked", required = false) List<Long> ids) {
        if (ids == null) {
            return "redirect:listofpersons";
        }

        ids.stream()
                .map(personRepo::findPersonById)
                .filter(Objects::nonNull)
                .forEach(personRepo::delete);

        return "redirect:listofpersons";
    }

}
