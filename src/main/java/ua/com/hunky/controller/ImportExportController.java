package ua.com.hunky.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;
import ua.com.hunky.service.ExcelPersonExportAndImport;
import ua.com.hunky.service.ExcelReader;

import javax.servlet.annotation.MultipartConfig;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024,
        maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
public class ImportExportController {

    private final PersonRepo personRepo;

    public ImportExportController(PersonRepo personRepo) {
        this.personRepo = personRepo;
    }

    @GetMapping("/exportImport")
    private String exportImport() {
        return "exportImport";
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

    @PostMapping("/importPersons")
    public String importPersons(@RequestParam MultipartFile file, @AuthenticationPrincipal User user, Map<String, Object> model) throws IOException {
        if (!file.isEmpty()) {
            File convFile = new File(file.getOriginalFilename());
            FileOutputStream fos = new FileOutputStream(convFile);
            fos.write(file.getBytes());
            fos.close();

            ExcelReader er = new ExcelReader(convFile, personRepo, user);
            er.ReadXLSX();
        }

        return "exportImport";
    }
}