package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.dto.PostDto;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;

@Service
public class PostExchangeClient {

    //    private static final String POST_SERVICE_URL = "http://POST_SERVICE";
    private static final String POST_SERVICE_URL = "http://localhost:8090";

    private final RestTemplate restTemplate;

    @Autowired
    public PostExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PostDto getPost(Long postId) {
        return restTemplate.getForObject(
                POST_SERVICE_URL + "/post/get/{userId}", PostDto.class, postId);
    }

    public PostDto getPost(PostDto postDto) {
        return getPost(postDto.getPostId());
    }

    public List<PostDto> getUsersPost(List<Long> usersId) {
        PostDto[] forNow = restTemplate.postForObject(
                POST_SERVICE_URL + "/post/getall/", usersId, PostDto[].class);
        return Arrays.asList(forNow);
    }

    public void addPost(@NotNull String message, Long userId) {
        ResponseEntity<Void> entity = restTemplate.postForEntity(POST_SERVICE_URL + "/post/create/{userId}",
                message, Void.class, userId);
        if (entity.getStatusCode() != HttpStatus.CREATED){
            throw new RestClientException("Fail to create new user post");
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
