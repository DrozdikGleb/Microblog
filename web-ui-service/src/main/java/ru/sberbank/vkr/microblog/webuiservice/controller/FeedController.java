package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.sberbank.vkr.microblog.webuiservice.dto.PostDto;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.service.FriendsExchangeClient;
import ru.sberbank.vkr.microblog.webuiservice.service.PostExchangeClient;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeClient;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
@RequestMapping("/feed")
public class FeedController {

    private static final String FEED_VIEW = "feed";
    private final PostExchangeClient postExchangeClient;
    private final FriendsExchangeClient friendsExchangeClient;
    private final ProfileExchangeClient profileExchangeClient;

    @Autowired
    public FeedController(PostExchangeClient postExchangeClient,
                          FriendsExchangeClient friendsExchangeClient,
                          ProfileExchangeClient profileExchangeClient) {
        this.postExchangeClient = postExchangeClient;
        this.friendsExchangeClient = friendsExchangeClient;
        this.profileExchangeClient = profileExchangeClient;
    }

    @GetMapping
    public String getFeed(Model model, Authentication authentication) {
        AppUser currentUser = (AppUser) authentication.getPrincipal();

        UserDto user = profileExchangeClient.getUser(currentUser.getId());
        model.addAttribute("user", user);

        List<Long> friendsIdList = new ArrayList<>();
        friendsIdList.add(currentUser.getId());
        friendsIdList.addAll(friendsExchangeClient.getFriendsList(currentUser.getId()));

        List<PostDto> posts = postExchangeClient.getUsersPost(friendsIdList);
        posts.sort(Comparator.comparing(PostDto::getDate));

        model.addAttribute("posts", posts);
        model.addAttribute("newPost", new PostDto(currentUser.getId(), null));
        return FEED_VIEW;
    }

    @PostMapping
    public String addPost(@ModelAttribute("newPost") PostDto postDto, Model model, Authentication authentication) {
        postExchangeClient.addPost(postDto);
        return getFeed(model, authentication);
    }

    @DeleteMapping
    @ResponseBody
    public String deletePost(@ModelAttribute("deletePost") PostDto postDto, Model model, Authentication authentication) {
        postExchangeClient.deletePost(postDto.getPostId());
        return getFeed(model, authentication);
    }

    @PutMapping
    @ResponseBody
    public String updatePost(@ModelAttribute("updatedPost") PostDto postDto, Model model, Authentication authentication) {
        return getFeed(model, authentication);
    }
}
