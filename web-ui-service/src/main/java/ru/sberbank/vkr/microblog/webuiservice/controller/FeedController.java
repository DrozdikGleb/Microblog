package ru.sberbank.vkr.microblog.webuiservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ru.sberbank.vkr.microblog.webuiservice.entity.PostDto;
import ru.sberbank.vkr.microblog.webuiservice.service.PostExchangeClient;
import ru.sberbank.vkr.microblog.webuiservice.service.ProfileExchangeClient;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/feed")
public class FeedController {

    private static final String FEED_VIEW = "feed";
    private final PostExchangeClient postExchangeClient;

    @Autowired
    public FeedController(PostExchangeClient postExchangeClient, ProfileExchangeClient profileExchangeClient) {
        this.postExchangeClient = postExchangeClient;
    }

    @GetMapping
    public String getFeed(Model model) {
        //TEST!!!! CHANGE THIS METHOD. ADD PARAMETERS List<Integer> listUsersId
        List<Integer> listUsersId = new ArrayList<>();
        listUsersId.add(1);
        List<PostDto> posts = postExchangeClient.getUsersPost(listUsersId);
        //List<PostDto> posts = postExchangeClient.getUsersPost();
        model.addAttribute(posts);
        return FEED_VIEW;
    }

    @PostMapping
    public String addPost(@ModelAttribute PostDto postDto, Model model) {
        model.addAttribute("post", postDto);

        return FEED_VIEW;
    }

}
