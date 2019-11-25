package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;

import java.util.List;
import java.util.Map;

@Controller
@SessionAttributes("user")
public class ListOfPersonsController {
    @Autowired
    PersonRepo personRepo;

    @GetMapping("/listofpersons")
    private String getListOfPersons(@AuthenticationPrincipal User user, Map<String, Object> model) {
        List<Person> persons = personRepo.findAllByUserID(user.getId());
        model.put("persons", persons.listIterator());

        return "listOfPersons";
    }


}
