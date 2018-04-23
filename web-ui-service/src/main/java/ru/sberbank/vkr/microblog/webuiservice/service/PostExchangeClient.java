package ru.sberbank.vkr.microblog.webuiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.dto.PostDto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Service
public class PostExchangeClient {
    private static final Logger logger = LoggerFactory.getLogger(PostExchangeClient.class);

    private static final String POST_SERVICE_URL = "http://localhost:8090";

    private final RestTemplate restTemplate;

    @Autowired
    public PostExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PostDto getPost(Long postId) {
        try {
            return restTemplate.getForObject(POST_SERVICE_URL + "/post/get/{userId}", PostDto.class, postId);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.warn("Users' posts microservice response for get post with id {} is {}:{}",
                    postId, httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getStatusText());
            throw new RestClientException(httpClientOrServerExc.getMessage());
        }
    }

    public PostDto getPost(PostDto postDto) {
        return getPost(postDto.getPostId());
    }

    public List<PostDto> getUsersPost(List<Long> usersId) {
        try {
            PostDto[] posts = restTemplate.postForObject(
                    POST_SERVICE_URL + "/post/getall/", usersId, PostDto[].class);
            return Arrays.asList(posts);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.warn("Users' posts microservice response for get posts for users with id {} is {}:{}",
                    usersId, httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getStatusText());
            throw new RestClientException(httpClientOrServerExc.getMessage());
        }
    }

    public void addPost(String message, Long userId) {
        try {
            restTemplate.postForObject(POST_SERVICE_URL + "/post/create/{userId}", message, Void.class, userId);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.warn("Users' posts microservice response for create post for user with id {} is {}:{}",
                    userId, httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getStatusText());
            throw new RestClientException(httpClientOrServerExc.getMessage());
        }
    }

    public void addPost(PostDto postDto) {
        addPost(postDto.getMessage(), postDto.getUserId());
    }

    public void updatePost(String message, Long userId) {
        restTemplate.put(
                POST_SERVICE_URL + "/post/update/{postId}", message, userId);
    }

    public void updatePost(PostDto postDto) {
        updatePost(postDto.getMessage(), postDto.getUserId());
    }

    public void deletePost(Long postId) {
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/{postId}", postId);
    }

    public void deletePost(PostDto postDto) {
        deletePost(postDto.getPostId());
    }

    public void deleteAllUserPost(Long userId) {
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/all/{userId}", userId);
    }

    public void deleteAllUserPost(PostDto postDto) {
        deleteAllUserPost(postDto.getUserId());
    }
}
