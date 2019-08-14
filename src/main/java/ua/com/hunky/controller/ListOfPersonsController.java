package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepository;

import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class ListOfPersonsController {
    @Autowired
    PersonRepository personRepository;

    @GetMapping("/listofpersons")
    private String getListOfPersons(User user, Map<String, Object> model) {
        List<Person> persons = personRepository.findAllByUserID(user.getUserID());
        model.put("persons", persons.listIterator());

        return "listOfPersons";
    }


}
