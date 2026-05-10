package Controller;

import Service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import Model.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    UserService userService;

    @GetMapping("/")
    public String login(HttpSession session) {
        if (userIsLoggedIn(session)) {
            return "redirect:/index";
        } else {
            return "login/loginPage";
        }
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, HttpSession session){
        User user = userService.login(username, password);
        if(user != null){
            session.setAttribute( "sessionUSer", user);
            return "redirect:/index";
        }
        return "login/loginPage";

    }
    @GetMapping("/index")
    public String index(HttpSession session){

        if(userIsLoggedIn(session)){
            return "index";
        }

        return "redirect:/";
    }

    public boolean userIsLoggedIn(HttpSession session) {
        User user = (User) session.getAttribute("SessionUser");
        return user != null;
    }
}