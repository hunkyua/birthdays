package ua.com.hunky.controller;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import ua.com.hunky.model.Role;
import ua.com.hunky.model.User;

@Controller
@SessionAttributes("user")
public class BackController {

    @GetMapping("/backFromListOfPersons")
    private String back(@AuthenticationPrincipal User auth, Model model) {
        if (isUser(auth)) {
            model.addAttribute("user", auth);
            return "menu";
        }
        return "login";
    }

    @GetMapping("/backFromExportImport")
    private String backFromExportImport(@AuthenticationPrincipal User auth, Model model) {
        if (isUser(auth)) {
            model.addAttribute("user", auth);
            return "menu";
        }
        return "login";
    }

    @GetMapping("/backFromImport")
    private String backFromImport(@AuthenticationPrincipal User auth, Model model) {
        if (isUser(auth)) {
            model.addAttribute("user", auth);
            return "exportImport";
        }
        return "login";
    }

    private boolean isUser(@AuthenticationPrincipal User auth) {
        return (auth.getRoles() != null) && auth.getRoles().contains(Role.USER);
    }

}
