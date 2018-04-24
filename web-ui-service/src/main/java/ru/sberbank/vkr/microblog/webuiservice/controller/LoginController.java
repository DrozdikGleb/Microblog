package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeService;

@Controller
public class LoginController {
    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    public static final String LOGIN_VIEW = "login";

    private final ProfileExchangeService profileExchangeService;

    @Autowired
    public LoginController(ProfileExchangeService profileExchangeService) {
        this.profileExchangeService = profileExchangeService;
    }

    @GetMapping("/login")
    public String getLoginPage() {
        logger.debug("Rendering login page.");
        return LOGIN_VIEW;
    }

    @GetMapping("/logoutSuccessful")
    public String logoutSuccessfulPage() {
        logger.debug("Rendering login successfull page.");
        return "logoutSuccessful";
    }

    @GetMapping("/register")
    public String register(Model model) {
        logger.debug("Rendering registration page.");

        model.addAttribute("newUser", new UserDto());
        return "registration";
    }

    @PostMapping("register")
    public String registerUser(@ModelAttribute UserDto user) {
        logger.debug("Process creating new user: {}", user);

        profileExchangeService.createUser(user);
        return LOGIN_VIEW;
    }
}


