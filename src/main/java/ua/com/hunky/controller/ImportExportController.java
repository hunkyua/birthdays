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
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.*;
import static ua.com.hunky.controller.ImportExportController.*;

@Controller
@MultipartConfig(location = "/tmp", fileSizeThreshold = MEGA,
        maxFileSize = 5L * MEGA, maxRequestSize = 25L * MEGA)
public class ImportExportController {

    static final int MEGA = 1024 * 1024;
    private final PersonRepo repo;

    public ImportExportController(PersonRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/exportImport")
    private String exportImport() {
        return "exportImport";
    }

    @GetMapping("/exportPersons")
    public ModelAndView exportPersons(@AuthenticationPrincipal User user) {
        List<Person> persons =
                repo.findAllByUserID(user.getId())
                    .stream()
                    .map(Person::new)
                    .collect(toList());
        return new ModelAndView(new ExcelPersonExportAndImport(), "personList", persons);
    }

    @PostMapping("/importPersons")
    public String importPersons(@RequestParam MultipartFile file,
                                @AuthenticationPrincipal User user,
                                Map<String, Object> model) throws IOException {
        if (file.isEmpty()) {
            model.put("Error", "You didn't choose a file");
            return "exportImport";
        }

        String name = file.getOriginalFilename() != null ? file.getOriginalFilename() : "";

        boolean isExcelFile = name.contains(".xls") || name.contains(".xlsx");

        if (!isExcelFile) {
            model.put("Error", "Only .xls and .xlsx files available");
            return "exportImport";
        }

        tryReadExcelFile(file, user, name);
        model.put("Alert", "Data successfully imported");

        return "exportImport";
    }

    private void tryReadExcelFile(@RequestParam MultipartFile input,
                                  @AuthenticationPrincipal User user,
                                  String name) throws IOException {
        File file = new File(name);
        try (FileOutputStream output = new FileOutputStream(file)) {
            output.write(input.getBytes());
        }

        ExcelReader reader = new ExcelReader(file, repo, user);
        reader.readExcelFile();
    }
}