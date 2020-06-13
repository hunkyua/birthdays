package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;
import ua.com.hunky.service.Messages;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@SessionAttributes("user")
public class PersonController {

    private static final java.util.Date THE_BEGINNING = new GregorianCalendar(1920, Calendar.JANUARY, 1).getTime();
    public static final SimpleDateFormat DATE = new SimpleDateFormat("yyyy/MM/dd");
    private final PersonRepo personRepo;

    @Autowired
    private Messages messages;

    public PersonController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @GetMapping("/addperson")
    private String moveToAddPerson() {
        return "addPerson";
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
            model.put("Error", messages.get("error.user.WrongDataInsideFieldDateOfBirth"));
            return "addPerson";
        }

        Person newPerson = new Person(parse(name), parse(surname), email, birthday, auth.getId());
        personRepo.save(newPerson);
        model.put("Alert", messages.get("error.user.PersonSuccessfullyCreated", name));

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

    @PostMapping("/editPerson")
    private String moveToEditPerson(@RequestParam(value = "isChecked", required = false) Long id,
                                    Map<String, Object> model) {
        Person currentPerson = personRepo.findPersonById(id);

        model.put("person", currentPerson);
        return "editPerson";
    }

    @PostMapping("/savePerson")
    private String savePerson(@AuthenticationPrincipal User auth,
                                @RequestParam String name,
                                @RequestParam String surname,
                                @RequestParam String email,
                                @RequestParam Date dateOfBirth,
                                Person previousPerson,
                                Map<String, Object> model) {

        Person person = personRepo.findPersonById(previousPerson.getId());

        if (dateOfBirth == null) {
            return "editPerson";
        }

        List<Person> persons = personRepo.findAllByUserID(auth.getId());

        if (!isEmailPreviousPerson(person, email) && isEmailAlreadyExists(persons, email)) {
            model.put("Error", messages.get("error.user.EmailAlreadyExists", email));
            return "/editPerson";
        }

        Date sqlDate = new Date(dateOfBirth.getTime());

        if (!isValid(sqlDate)) {
            model.put("Error", messages.get("error.user.WrongDataInsideFieldDateOfBirth"));
            return "editPerson";
        }

        person.setName(name);
        person.setSurname(surname);
        person.setEmail(email);
        person.setDateOfBirth(dateOfBirth);
        personRepo.save(person);

        return "redirect:listofpersons";
    }

    private boolean isEmailPreviousPerson(Person person, String email) {
        if (person.getEmail() == null) {
            return false;
        }
        return person.getEmail().equals(email);
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

}
