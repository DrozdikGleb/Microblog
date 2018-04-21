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
    private static final String POST_SERVICE_URL = "http://localhost:8090";

    private final RestTemplate restTemplate;

    @Autowired
    public PostExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PostDto getPost(int postId) {
        PostDto postDto = restTemplate.getForObject(
                POST_SERVICE_URL + "/post/get/{userId}", PostDto.class, postId);
        return postDto;
    }

    public PostDto getPost(PostDto postDto) {
        return restTemplate.getForObject(
                POST_SERVICE_URL + "/post/get/{userId}", PostDto.class, postDto.getPostId());
    }

    public List<PostDto> getUsersPost(List<Integer> usersId) {
        List<PostDto> postDtos = restTemplate.postForObject(
                POST_SERVICE_URL + "/post/getall/", usersId, List.class);
        return postDtos;
    }

    public void addPost(String message, int userId) {
        restTemplate.put(POST_SERVICE_URL + "/post/create/{userId}", message, userId);
    }

    public void addPost(PostDto postDto) {
        restTemplate.put(POST_SERVICE_URL +
                "/post/create/{userId}", postDto.getMessage(), postDto.getUserId());
    }

    public void updatePost(String message, int userId) {
        restTemplate.postForLocation(
                POST_SERVICE_URL + "/post/update/{postId}", message, userId);
    }

    public void updatePost(PostDto postDto) {
        restTemplate.postForLocation(POST_SERVICE_URL +
                "/post/update/{postId}", postDto.getMessage(), postDto.getUserId());
    }

    public void deletePost(int postId) {
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/{postId}", postId);
    }

    public void deletePost(PostDto postDto) {
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/{postId}", postDto.getPostId());
    }

    public void deleteAllUserPost(int userId) {
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/all/{userId}", userId);
    }

    public void deleteAllUserPost(PostDto postDto) {
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/all/{userId}", postDto.getUserId());
    }
}
