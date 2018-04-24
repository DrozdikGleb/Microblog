package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.entity.Profile;
import ru.sberbank.vkr.microblog.webuiservice.service.FriendsExchangeService;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeService;

import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendsController {
    private static final Logger logger = LoggerFactory.getLogger(FriendsController.class);

    private static final String MODEL_ATTRIBUTE_FRIENDS = "friends";
    private static final String MODEL_ATTRIBUTE_USERS = "users";

    private static final String FRIENDS_VIEW = "friends";

    private final ProfileExchangeService profileExchangeService;
    private final FriendsExchangeService friendsExchangeService;

    @Autowired
    public FriendsController(ProfileExchangeService profileExchangeService, FriendsExchangeService friendsExchangeService) {
        this.profileExchangeService = profileExchangeService;
        this.friendsExchangeService = friendsExchangeService;
    }

    @GetMapping
    public String getFriends(@RequestParam(value = "find", required = false) boolean edit,
                             Model model, Authentication authentication) {
        logger.debug("Rendering friends page.");

        AppUser currentUser = (AppUser) authentication.getPrincipal();
        model.addAttribute("enableSearch", edit);
        if (edit) {
            List<Profile> users = profileExchangeService.getUsersList();
            model.addAttribute(MODEL_ATTRIBUTE_USERS, users);
        } else {
            List<Long> friendsId = friendsExchangeService.getFriendsList(currentUser.getId());
            if(!friendsId.isEmpty()) {
                List<Profile> friends = profileExchangeService.getFriendsList(friendsId);
                model.addAttribute(MODEL_ATTRIBUTE_FRIENDS, friends);
            }
        }
        return FRIENDS_VIEW;
    }

    @PostMapping
    public String addFriend(@ModelAttribute Profile friendProfile, Authentication authentication) {
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        logger.debug("Process adding new friendship for user: {} with: {}", currentUser.getId(), friendProfile);

        friendsExchangeService.follow(currentUser.getId(), friendProfile.getId());
        return FRIENDS_VIEW;
    }

    @DeleteMapping
    public String deleteFriend(@ModelAttribute Profile friendProfile, Authentication authentication) {
        AppUser currentUser = (AppUser) authentication.getPrincipal();
        logger.debug("Process deleting friendship for user: {} with: {}", currentUser.getId(), friendProfile);

        friendsExchangeService.unfollow(currentUser.getId(), friendProfile.getId());
        return FRIENDS_VIEW;
    }
}
