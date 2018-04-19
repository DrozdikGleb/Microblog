package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;

@Controller
@RequestMapping("/me")
public class ProfileController {
    private static final String PROFILE_VIEW = "me";

    @GetMapping
    public String showProfile(){

        return PROFILE_VIEW;
    }

    @PutMapping
    public String updateProfile(@ModelAttribute UserDto user){

        return PROFILE_VIEW;
    }

    @PostMapping
    public String createProfile(@ModelAttribute UserDto user){

        return PROFILE_VIEW;
    }

    @DeleteMapping
    public String deleteProfile(@ModelAttribute UserDto user){

        return "login";
    }

}
