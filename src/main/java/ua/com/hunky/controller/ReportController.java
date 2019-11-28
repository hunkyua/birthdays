package ua.com.hunky.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;
import ua.com.hunky.service.ExcelPersonExportAndImport;

import java.util.ArrayList;
import java.util.List;

@Controller
@SessionAttributes("user")
public class ReportController {

    private final PersonRepo personRepo;

    public ReportController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @GetMapping("/exportPersons")
    public ModelAndView exportPersons(@AuthenticationPrincipal User user){
        List<Person> personList = new ArrayList<>();
        List<Person> userPersons = personRepo.findAllByUserID(user.getId());
        for (Person userPerson : userPersons) {
            personList.add(new Person(
                    userPerson.getId(),
                    userPerson.getName(),
                    userPerson.getSurname(),
                    userPerson.getEmail(),
                    userPerson.getDateOfBirth()
            ));
        }
        return new ModelAndView(new ExcelPersonExportAndImport(), "personList", personList);
    }
}