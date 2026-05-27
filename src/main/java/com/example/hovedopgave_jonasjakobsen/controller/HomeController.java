package com.example.hovedopgave_jonasjakobsen.controller;

import com.example.hovedopgave_jonasjakobsen.service.CustomUserDetailsService;
import com.example.hovedopgave_jonasjakobsen.service.EventService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.hovedopgave_jonasjakobsen.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.hovedopgave_jonasjakobsen.model.Event;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @GetMapping("/")
    public String index(Model model) {
        LocalDate now = LocalDate.now();

        List<Event> events =
                eventService.getEventsForMonth(
                        now.getYear(),
                        now.getMonthValue()
                );

        Map<LocalDate, List<Event>> eventMap = events.stream()
                .collect(Collectors.groupingBy(Event::getDate));

        model.addAttribute("eventMap", eventMap);
        model.addAttribute("month", now.getMonthValue());
        model.addAttribute("year", now.getYear());


        return "index";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login/loginPage";
    }

    @GetMapping("auth/createuser")
    public String createUserPage(){
        return "login/createUser";
    }
    @PostMapping("/auth/createuser")
    public String createUser(@RequestParam String username,
                             @RequestParam String password,
                             @RequestParam String name, RedirectAttributes redirectAttributes) {

        boolean created = userService.createPrivateUser(username, password, name);

        if (!created) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "En bruger med dette brugernavn findes allerede."
            );

            return "redirect:/auth/createuser";
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Bruger oprettet."
        );

        return "redirect:/auth/login";
    }
}