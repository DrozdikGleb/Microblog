package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;
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
    public String showProfile(@ModelAttribute UserDto user, HttpServletRequest request, Model model) {
        model.addAttribute("editMode", false);
        Long currentUserId = Long.parseLong(request.getHeader("userId"));
        UserDto currentUser = profileExchangeClient.getUser(currentUserId);
        model.addAttribute(currentUser);
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

        return PROFILE_VIEW;
    }

    @DeleteMapping
    public String deleteProfile(@ModelAttribute UserDto user) {
        profileExchangeClient.deleteUser(user);
        return "login";
    }

}
