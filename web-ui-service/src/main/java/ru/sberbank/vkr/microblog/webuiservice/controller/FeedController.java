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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;
import ru.sberbank.vkr.microblog.webuiservice.dto.PostDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.entity.Post;
import ru.sberbank.vkr.microblog.webuiservice.entity.Profile;
import ru.sberbank.vkr.microblog.webuiservice.service.FriendsExchangeService;
import ru.sberbank.vkr.microblog.webuiservice.service.PostExchangeService;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/feed")
public class FeedController {
    private static final Logger logger = LoggerFactory.getLogger(FeedController.class);

    private static final String MODEL_ATTRIBUTE_POSTS = "posts";
    private static final String MODEL_ATTRIBUTE_NEW_POST = "newPost";
    private static final String MODEL_ATTRIBUTE_UPDATED_POST = "updatedPost";
    private static final String MODEL_ATTRIBUTE_DELETED_POST = "deletePost";
    private static final String MODEL_ATTRIBUTE_USER = "user";

    private final PostExchangeService postExchangeService;
    private final FriendsExchangeService friendsExchangeService;
    private final ProfileExchangeService profileExchangeService;

    @Autowired
    public FeedController(PostExchangeService postExchangeService,
                          FriendsExchangeService friendsExchangeService,
                          ProfileExchangeService profileExchangeService) {
        this.postExchangeService = postExchangeService;
        this.friendsExchangeService = friendsExchangeService;
        this.profileExchangeService = profileExchangeService;
    }

    @GetMapping
    public String getFeed(Model model, Authentication authentication) {
        logger.debug("Rendering feed page.");

        AppUser currentUser = (AppUser) authentication.getPrincipal();

        Profile user = profileExchangeService.getUser(currentUser.getId());
        model.addAttribute(MODEL_ATTRIBUTE_USER, user);

        List<Long> friendsIdList = new ArrayList<>();
        friendsIdList.add(currentUser.getId());
        friendsIdList.addAll(friendsExchangeService.getFriendsList(currentUser.getId()));

        List<Post> posts = postExchangeService.getUsersPost(friendsIdList);

        if (!posts.isEmpty()) {
            List<Profile> profiles = profileExchangeService.getFriendsList(friendsIdList);

            Map<Long, Profile> profileMap = profiles.stream()
                    .collect(Collectors.toMap(Profile::getId, Function.identity()));

            posts.forEach(post -> {
                post.setFirstName(profileMap.get(post.getUserId()).getFirstName());
                post.setLastName(profileMap.get(post.getUserId()).getLastName());
            });

            posts.sort(Comparator.comparing(PostDto::getDate));

            model.addAttribute(MODEL_ATTRIBUTE_POSTS, posts);
        }
        model.addAttribute(MODEL_ATTRIBUTE_NEW_POST, new PostDto());

        model.addAttribute("title", "Новости");
        return "feed";
    }

    @PostMapping
    public String addPost(@ModelAttribute(MODEL_ATTRIBUTE_NEW_POST) Post post, Authentication authentication) {
        logger.debug("Process creating new post: {}", post);

        if (!post.getMessage().equals("") && post.getMessage() != null) {
            logger.debug("Post is valid");
            post.setUserId(((AppUser) authentication.getPrincipal()).getId());
            postExchangeService.addPost(post);
        }
        return "redirect:/feed";
    }

    @DeleteMapping
    public String deletePost(@ModelAttribute(MODEL_ATTRIBUTE_DELETED_POST) Post post, Authentication authentication) {
        logger.debug("Process deleting post: {}", post);

        AppUser currentUser = (AppUser) authentication.getPrincipal();
        if (post.getUserId() != currentUser.getId()) {
            logger.debug("Access denied for deleting post: {} by user {}", post, currentUser);
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Access denied");
        }

        postExchangeService.deletePost(post.getPostId());
        return "redirect:/feed";
    }

    @PutMapping
    public String updatePost(@ModelAttribute(MODEL_ATTRIBUTE_UPDATED_POST) Post post, Authentication authentication) {
        logger.debug("Process updating post: {}", post);

        AppUser currentUser = (AppUser) authentication.getPrincipal();
        if (post.getUserId() != currentUser.getId()) {
            logger.debug("Access denied for updating post: {} by user {}", post, currentUser);
            throw new HttpClientErrorException(HttpStatus.FORBIDDEN, "Access denied");
        }

        postExchangeService.updatePost(post);
        return "redirect:/feed";
    }
}
