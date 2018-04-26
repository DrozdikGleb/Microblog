package ru.sberbank.vkr.microblog.webuiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.dto.PostDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.Post;
import ru.sberbank.vkr.microblog.webuiservice.entity.Profile;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostExchangeService {
    private static final Logger logger = LoggerFactory.getLogger(PostExchangeService.class);

    private static final String POST_SERVICE_URL = "http://post-service";

    private static final String REQUEST_GET_ALL_POST = "/post/getall/";
    private static final String REQUEST_GET_POST_ID = "/post/get/{postId}";
    private static final String REQUEST_CREATE_POST = "/post/create/{userId}";
    private static final String REQUEST_UPDATE_POST = "/post/update/{postId}";
    private static final String REQUEST_DELETE_POST = "/post/delete/{postId}";
    private static final String REQUEST_DELETE_ALL_POST = "/post/delete/all/{userId}";

    private final RestTemplate restTemplate;

    @Autowired
    public PostExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PostDto getPost(Long postId) {
        logger.debug("Request microservice to get post for id: {}", postId);
        return restTemplate.getForObject(POST_SERVICE_URL + REQUEST_GET_POST_ID, PostDto.class, postId);
    }

    public PostDto getPost(Post post) {
        logger.debug("Request microservice to get post: {}", post);
        return getPost(post.getPostId());
    }

    public List<Post> getUsersPost(List<Long> usersId) {
        logger.debug("Request microservice to get posts for users: {}", usersId);

        PostDto[] posts = restTemplate.postForObject(
                POST_SERVICE_URL + REQUEST_GET_ALL_POST, usersId, PostDto[].class);
        return Arrays.stream(posts).map(postDto -> new Post.Builder(postDto.getPostId())
                .userId(postDto.getUserId())
                .date(postDto.getDate())
                .message(postDto.getMessage())
                .build()).collect(Collectors.toList());
    }

    public void addPost(String message, Long userId) {
        logger.debug("Request microservice to add new post {} for user: {}", message, userId);
        restTemplate.postForObject(POST_SERVICE_URL + REQUEST_CREATE_POST, message, Void.class, userId);
    }

    public void addPost(Post post) {
        logger.debug("Request microservice to add new post: {}", post);
        addPost(post.getMessage(), post.getUserId());
    }

    public void updatePost(String message, Long userId) {
        logger.debug("Request microservice to update post {} for user: {}", message, userId);
        restTemplate.put(POST_SERVICE_URL + REQUEST_UPDATE_POST, message, userId);
    }

    public void updatePost(Post post) {
        logger.debug("Request microservice to update post {}", post);
        updatePost(post.getMessage(), post.getUserId());
    }

    public void deletePost(Long postId) {
        logger.debug("Request microservice to delete post with id {} ", postId);
        restTemplate.delete(POST_SERVICE_URL + REQUEST_DELETE_POST, postId);
    }

    public void deletePost(Post post) {
        logger.debug("Request microservice to delete post: {}", post);
        deletePost(post.getPostId());
    }

    public void deleteAllUserPost(Long userId) {
        logger.debug("Request microservice to delete all post for user with id: {}", userId);
        restTemplate.delete(POST_SERVICE_URL + REQUEST_DELETE_ALL_POST, userId);
    }

    public void deleteAllUserPost(Profile profile) {
        logger.debug("Request microservice to delete all post for user: {}", profile);
        deleteAllUserPost(profile.getId());
    }
}
