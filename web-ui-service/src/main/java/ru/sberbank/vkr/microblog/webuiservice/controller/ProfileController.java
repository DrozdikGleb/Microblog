package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeClient;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/me")
public class ProfileController {


    private static final String PROFILE_VIEW = "me";

    private final ProfileExchangeClient profileExchangeClient;

    @Autowired
    public ProfileController(ProfileExchangeClient profileExchangeClient) {
        this.profileExchangeClient = profileExchangeClient;
    }

    @GetMapping
    public String showProfile(Model model, Authentication authentication) {
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        UserDto user = profileExchangeClient.getUser(currentUser.getId());
        model.addAttribute("user", user);
        model.addAttribute("editMode", false);
        return PROFILE_VIEW;
    }

    @PutMapping
    public String updateProfile(@ModelAttribute UserDto user, Model model) {
        model.addAttribute("editMode", true);
        profileExchangeClient.updateUser(user);
        return PROFILE_VIEW;
    }

    @PostMapping
    public String createProfile(@ModelAttribute UserDto user) {
        profileExchangeClient.createUser(user);
        return "login";
    }

    @DeleteMapping
    public String deleteProfile(@ModelAttribute UserDto user) {
        profileExchangeClient.deleteUser(user);
        return "login";
    }

}
