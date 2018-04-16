package ru.sberbank.vkr.microblog.webuiservice.controllers;

import org.apache.http.HttpRequest;
import org.apache.http.client.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import ru.sberbank.vkr.microblog.webuiservice.entities.Post;
import ru.sberbank.vkr.microblog.webuiservice.services.PostService;

import java.util.List;

@Controller
public class FeedController {

    @Autowired
    PostService postService;

    @GetMapping("/feed")
    public String getFeed(WebRequest webRequest, Model model) {

        // Получать userID
        int userID = 1;

        List<Post> posts = postService.getFeedForUser(userID);
        model.addAttribute("posts", posts);
        return "feed";
    }

    @PostMapping("/feed")
    public String addPost(@ModelAttribute Post post, Model model) {
        model.addAttribute("post", post);
        postService.createPost(post);
        return "feed";
    }

}
