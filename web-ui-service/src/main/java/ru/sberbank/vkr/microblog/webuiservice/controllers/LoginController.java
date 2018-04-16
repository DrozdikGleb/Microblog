package ru.sberbank.vkr.microblog.webuiservice.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import ru.sberbank.vkr.microblog.webuiservice.entities.User;
import ru.sberbank.vkr.microblog.webuiservice.services.UserService;

@Controller
public class LoginController {

    UserService userService;

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(WebRequest webRequest, Model model) {

        String userName = webRequest.getParameter("userName");
        String password = webRequest.getParameter("password");

        User user = userService.authUser(userName, password);

        if (user == null) {
            String errorMessage = "Invalid userName or Password";

            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }

        return "redirect:/feed";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
