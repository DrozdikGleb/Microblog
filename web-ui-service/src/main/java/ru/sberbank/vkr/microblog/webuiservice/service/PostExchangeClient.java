package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.PostDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.PostsDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;

import java.util.List;

@Component
public class PostExchangeClient {

//    private static final String POST_SERVICE_URL = "http://POST_SERVICE";
    private static final String POST_SERVICE_URL = "http://localhost:8082";

    private UserDto currentUser;

    private final RestTemplate restTemplate;

    @Autowired
    public PostExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<PostDto> getFriendsPosts() {
        PostsDto postsDto = restTemplate.getForObject(
                POST_SERVICE_URL + "/post/{userId}", PostsDto.class, currentUser.getId());
        return postsDto.getItems();
    }

    public List<PostDto> getMyPosts() {
        //List<Integer> in Post-request body
        PostsDto postsDto = restTemplate.getForObject(
                POST_SERVICE_URL + "/post/getall/", PostsDto.class);
        return postsDto.getItems();
    }

    public PostDto addPost(PostDto post) {
        return restTemplate.postForObject(
                POST_SERVICE_URL + "/post/create/{userId}", post, PostDto.class, currentUser.getId());
    }

    public void deletePost(PostDto post) {
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/{postId}", post.getPostId());
    }
}
