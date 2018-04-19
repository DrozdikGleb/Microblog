package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.context.request.WebRequest;
import ru.sberbank.vkr.microblog.webuiservice.entity.LoginForm;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeClient;

@Controller
public class LoginController {
    private final ProfileExchangeClient profileExchangeClient;

    @Autowired
    public LoginController(ProfileExchangeClient profileExchangeClient) {
        this.profileExchangeClient = profileExchangeClient;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "login";
    }

    @PostMapping("/login")
    public String checkLogin(WebRequest webRequest, Model model,
                             @ModelAttribute("loginForm") LoginForm loginForm) {


        Long userId;
        
        if (userId == null) {
            String errorMessage = "Invalid userName or Password";

            model.addAttribute("errorMessage", errorMessage);
            return "login";
        }
        UserDto user  = new UserDto(profileExchangeClient.getUser(userId));

        return "redirect:/feed";
    }

    @GetMapping("/logout")
    public String logout() {
        return "logout";
    }
}
