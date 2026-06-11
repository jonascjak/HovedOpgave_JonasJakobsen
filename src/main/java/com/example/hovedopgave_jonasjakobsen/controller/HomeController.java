package com.example.hovedopgave_jonasjakobsen.controller;


import com.example.hovedopgave_jonasjakobsen.model.CompanyUser;
import com.example.hovedopgave_jonasjakobsen.model.User;
import com.example.hovedopgave_jonasjakobsen.model.repository.EventParticipantRepository;
import com.example.hovedopgave_jonasjakobsen.model.service.EncryptionService;
import com.example.hovedopgave_jonasjakobsen.model.service.EventService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.hovedopgave_jonasjakobsen.model.service.UserService;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import com.example.hovedopgave_jonasjakobsen.model.Event;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.springframework.security.core.Authentication;

@Controller
public class HomeController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventService eventService;

    @Autowired
    private EventParticipantRepository eventParticipantRepository;

     @Autowired
     private EncryptionService encryptionService;

    @GetMapping("/")
    public String index(Model model) {

        LocalDate now = LocalDate.now();

        int year = now.getYear();
        int month = now.getMonthValue();

        model.addAttribute("year", year);
        model.addAttribute("month", month);

        model.addAttribute("events", eventService.getAllEvents());

        return "index";
    }

    @GetMapping("/auth/login")
    public String loginPage() {
        return "login/loginPage";
    }

    @GetMapping("/butikker")
    public String butikker(Model model) {

        List<CompanyUser> butikker = userService.findAllStores();

        List<String> decryptedNames = butikker.stream()
                .map(butik -> encryptionService.decrypt(butik.getName()))
                .toList();
        model.addAttribute("butikker", butikker);
        model.addAttribute("decryptedNames", decryptedNames);

        return "butikker";
    }

    @GetMapping("/profil")
    public String profil(Authentication authentication, Model model){
        User user = userService.findByUsername(authentication.getName());
        String name = encryptionService.decrypt(user.getName());
            model.addAttribute("user", user);
            model.addAttribute("decryptedName", name);
            return "profil";
    }

    @PostMapping("/profil/update")
    public String updateProfile(@RequestParam long id,
                                @RequestParam String username,
                                @RequestParam String name,
                                @RequestParam String password,
                                @RequestParam String passwordCheck,
                                Authentication authentication,
                                RedirectAttributes redirectAttributes) {

        try {
            userService.updateUser(id, username, name, password, passwordCheck, authentication);
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profil";
        }

        return "redirect:/";
    }

    @PostMapping("/profil/delete")
    public String deleteProfile(@RequestParam long id, RedirectAttributes redirectAttributes, Authentication authentication){
        try{
            userService.deleteUser(id, authentication);
        } catch(RuntimeException e){
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/profil";
        }
    return "redirect:/";
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

    @GetMapping("/event/{id}")
    public String eventDetails(@PathVariable long id, Authentication authentication, Model model) {
        Event event = eventService.findById(id);

        boolean alreadyJoined = eventParticipantRepository.existsByEventIdAndPrivateUserUsername(id, authentication.getName());
        if(event != null) {
            model.addAttribute("event", event);
            model.addAttribute("alreadyJoined", alreadyJoined);
            return "eventDetails";
        }
        return "redirect:/";
    }
    @PostMapping("/event/create")
    public String createEvent(@RequestParam String eventName,
                              @RequestParam String eventGame,
                              @RequestParam String eventDescription,
                              @RequestParam LocalTime startTime,
                              @RequestParam LocalDate eventDate,
                              Authentication authentication,
                              RedirectAttributes redirectAttributes){
        String username = authentication.getName();
        boolean created = eventService.createEvent(eventName, eventGame, eventDescription, eventDate, startTime, username);
        if (!created) {
            redirectAttributes.addFlashAttribute(
                    "errorMessage",
                    "Unable to create event"
            );
        }

        redirectAttributes.addFlashAttribute(
                "successMessage",
                "Event created succesful"
        );
        return "redirect:/";
    }
    @PostMapping("/event/update")
    public String updateEvent(
            @RequestParam long id,
            @RequestParam String eventName,
            @RequestParam String eventGame,
            @RequestParam String eventDescription,
            @RequestParam LocalTime startTime) {

        eventService.updateEvent(
                id,
                eventName,
                eventGame,
                eventDescription,
                startTime
        );

        return "redirect:/event/" + id;
    }

    @PostMapping("/event/{id}/join")
    public String joinEvent(@PathVariable long id, Authentication auth) {

        eventService.joinEvent(id, auth.getName());

        return "redirect:/event/" + id;
    }

    @PostMapping("/event/{id}/delete")
    public String deleteEvent(@PathVariable long id){
        eventService.deleteEvent(id);

        return "redirect:/";
    }

    @GetMapping("/privatlivspolitik")
    public String privatlivspolitik() {
        return "privatliv";
    }

    @GetMapping("/om-os")
    public String omOs() {
        return "omOs";
    }

    @GetMapping("/kontakt")
    public String kontakt() {
        return "kontakt";
    }
}