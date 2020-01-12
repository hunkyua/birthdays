package ua.com.hunky.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.User;

@Controller
@SessionAttributes("user")
public class BackController {

    @GetMapping("/backFromListOfPersons")
    private String back(@AuthenticationPrincipal User user, Model model) {
        if ("USER".equals(user.getRoles().iterator().next().name())) {
            model.addAttribute("user", user);
            return "menu";
        }
        return "login";
    }

    @GetMapping("/backFromExportImport")
    private String backFromExportImport(@AuthenticationPrincipal User user, Model model) {
        if ("USER".equals(user.getRoles().iterator().next().name())) {
            model.addAttribute("user", user);
            return "menu";
        }
        return "login";
    }

    @GetMapping("/backFromImport")
    private String backFromImport(@AuthenticationPrincipal User user, Model model) {
        if ("USER".equals(user.getRoles().iterator().next().name())) {
            model.addAttribute("user", user);
            return "exportImport";
        }
        return "login";
    }

}
