package ua.com.hunky.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import ua.com.hunky.model.Person;
import ua.com.hunky.model.User;
import ua.com.hunky.repository.PersonRepo;
import ua.com.hunky.service.ExcelPersonExportAndImport;
import ua.com.hunky.service.ExcelReader;
import ua.com.hunky.service.Messages;

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

    @Autowired
    private Messages messages;

    public ImportExportController(PersonRepo repo) {
        this.repo = repo;
    }

    @GetMapping("/exportImport")
    private String exportImport() {
        return "exportImport";
    }

    @GetMapping("/exportPersons")
    public ModelAndView exportPersons(@AuthenticationPrincipal User auth) {
        List<Person> persons =
                repo.findAllByUserID(auth.getId())
                    .stream()
                    .map(Person::new)
                    .collect(toList());
        return new ModelAndView(new ExcelPersonExportAndImport(), "personList", persons);
    }

    @PostMapping("/importPersons")
    public String importPersons(@RequestParam MultipartFile file,
                                @AuthenticationPrincipal User auth,
                                Map<String, Object> model) throws IOException {
        if (file.isEmpty()) {
            model.put("Error", messages.get("error.user.ChooseFileForImport"));
            return "exportImport";
        }

        String name = file.getOriginalFilename() != null ? file.getOriginalFilename() : "";

        boolean isExcelFile = name.contains(".xls") || name.contains(".xlsx");

        if (!isExcelFile) {
            model.put("Error", messages.get("error.user.OnlyXlsAndXlsxFilesAvailable"));
            return "exportImport";
        }

        tryReadExcelFile(file, auth, name);
        model.put("Alert", messages.get("error.user.DataSuccessfullyImported"));

        return "exportImport";
    }

    private void tryReadExcelFile(@RequestParam MultipartFile input,
                                  @AuthenticationPrincipal User auth,
                                  String name) throws IOException {
        File file = new File(name);
        try (FileOutputStream output = new FileOutputStream(file)) {
            output.write(input.getBytes());
        }

        ExcelReader reader = new ExcelReader(file, repo);
        reader.readExcelFile(auth);
    }
}