package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.dto.UsersDto;
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
    public String getFriends(Model model, Principal principal) {
        AppUser currentUser = (AppUser) principal;

        List<Long> friendsId = friendsExchangeClient.getFriendsList(currentUser.getId());
        UsersDto friends = new UsersDto(profileExchangeClient.getFriendsList(friendsId));
        model.addAttribute(friends);
        return FRIENDS_VIEW;
    }

    @GetMapping("/find")
    public String findFriends(Model model) {
        UsersDto users = new UsersDto(profileExchangeClient.getUsersList());
        model.addAttribute(users);
        return FRIENDS_VIEW;
    }

    @PostMapping
    public String addFriend(@ModelAttribute UserDto newFriend) {
        Long userId = 1L;
        friendsExchangeClient.follow(userId, newFriend.getId());
        return FRIENDS_VIEW;
    }

    @DeleteMapping
    public String deleteFriend(@ModelAttribute UserDto friend) {
        Long userId = 1L;
        friendsExchangeClient.unfollow(userId, friend.getId());
        return FRIENDS_VIEW;
    }
}
