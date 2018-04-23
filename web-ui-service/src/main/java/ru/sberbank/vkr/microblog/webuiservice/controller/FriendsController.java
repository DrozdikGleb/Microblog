package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.service.FriendsExchangeClient;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeClient;

import java.security.Principal;
import java.util.List;

@Controller
@RequestMapping("/friends")
public class FriendsController {

    private static final String FRIENDS_VIEW = "friends";
    private final ProfileExchangeClient profileExchangeClient;
    private final FriendsExchangeClient friendsExchangeClient;

    @Autowired
    public FriendsController(ProfileExchangeClient profileExchangeClient, FriendsExchangeClient friendsExchangeClient) {
        this.profileExchangeClient = profileExchangeClient;
        this.friendsExchangeClient = friendsExchangeClient;
    }

    @GetMapping
    public String getFriends(Model model, Authentication authentication) {
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        List<Long> friendsId = friendsExchangeClient.getFriendsList(currentUser.getId());
        List<UserDto> friends = profileExchangeClient.getFriendsList(friendsId);
        model.addAttribute(friends);
        return FRIENDS_VIEW;
    }

    @GetMapping("/find")
    public String findFriends(Model model) {
        List<UserDto> users = profileExchangeClient.getUsersList();
        model.addAttribute(users);
        return FRIENDS_VIEW;
    }

    @PostMapping
    public String addFriend(@ModelAttribute UserDto newFriend, Authentication authentication) {
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        friendsExchangeClient.follow(currentUser.getId(), newFriend.getId());
        return FRIENDS_VIEW;
    }

    @DeleteMapping
    public String deleteFriend(@ModelAttribute UserDto friend, Authentication authentication) {
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        friendsExchangeClient.unfollow(currentUser.getId(), friend.getId());
        return FRIENDS_VIEW;
    }
}
