package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.entity.Profile;
import ru.sberbank.vkr.microblog.webuiservice.service.FriendsExchangeService;
import ru.sberbank.vkr.microblog.webuiservice.service.PostExchangeService;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeService;

@Controller
@RequestMapping("/me")
public class ProfileController {
    private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

    private static final String MODEL_ATTRIBUTE_USER = "user";

    private static final String PROFILE_VIEW = "me";
    private static final String LOGIN_VIEW = "login";

    private final ProfileExchangeService profileExchangeService;
    private final PostExchangeService postExchangeService;
    private final FriendsExchangeService friendsExchangeService;

    @Autowired
    public ProfileController(ProfileExchangeService profileExchangeService,
                             PostExchangeService postExchangeService,
                             FriendsExchangeService friendsExchangeService) {
        this.profileExchangeService = profileExchangeService;
        this.postExchangeService = postExchangeService;
        this.friendsExchangeService = friendsExchangeService;
    }

    @GetMapping
    public String showProfile(@RequestParam(value = "act", required = false) String act,
                              Model model, Authentication authentication) {
        logger.debug("Rendering profile page.");
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        Profile userProfile = profileExchangeService.getUser(currentUser.getId());
        model.addAttribute(MODEL_ATTRIBUTE_USER, userProfile);

        if (act != null && act.equals("edit")) {
            model.addAttribute("enableEdit", true);
        }

        return PROFILE_VIEW;
    }

    @PostMapping("/edit")
    public String updateProfile(@ModelAttribute(MODEL_ATTRIBUTE_USER) Profile user,
                                Authentication authentication) {
        logger.debug("Process updating request for user profile: {}", user);
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        user.setLogin(currentUser.getUsername());

        if (currentUser.getId() != user.getId()) {
            logger.debug("Access denied for updating profile: {} by user {}", user, currentUser);
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Access denied");
        }
        profileExchangeService.updateUser(user);
        return "redirect:/" + PROFILE_VIEW;
    }

    @PostMapping("/delete")
    public String deleteProfile(@ModelAttribute Profile user, Authentication authentication) {
        logger.debug("Process deleting request for user with id: {}", user.getId());
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        if (user.getId() != currentUser.getId()) {
            logger.debug("Access denied for deleting user: {} by user {}", user, currentUser);
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Access denied");
        }

        profileExchangeService.deleteUser(user);
        postExchangeService.deleteAllUserPost(user);
        friendsExchangeService.deleteAllUserFriends(user);
        logger.debug("Deleted user: {}", user);

        return "redirect:/" + LOGIN_VIEW;
    }

}
