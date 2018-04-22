package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeClient;

@Controller
public class LoginController {
    private final ProfileExchangeClient profileExchangeClient;

    @Autowired
    public LoginController(ProfileExchangeClient profileExchangeClient) {
        this.profileExchangeClient = profileExchangeClient;
    }

    @GetMapping("/login")
    public String getLoginPage(Model model) {
        return "login";
    }


    public String  register(@ModelAttribute("user")UserDto userDto)
    {
        return "registration";
    }
    public String logout() {
        return "logout";
    }
}
