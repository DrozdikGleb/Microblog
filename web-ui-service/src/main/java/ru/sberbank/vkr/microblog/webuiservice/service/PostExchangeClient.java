package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.PostDto;
import java.util.List;

@Component
public class PostExchangeClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String POST_SERVICE_NAME = "POST-SERVICE";

    private static String POST_SERVICE_URL;
    //private static final String POST_SERVICE_URL = "http://localhost:8090";

    private final RestTemplate restTemplate;

    @Autowired
    public PostExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public PostDto getPost(int postId) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        PostDto postDto = restTemplate.getForObject(
                POST_SERVICE_URL + "/post/get/{userId}", PostDto.class, postId);
        return postDto;
    }

    public PostDto getPost(PostDto postDto) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        return restTemplate.getForObject(
                POST_SERVICE_URL + "/post/get/{userId}", PostDto.class, postDto.getPostId());
    }

    public List<PostDto> getUsersPost(List<Integer> usersId) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI("POST-SERVICE");
        }
        List<PostDto> postDtos = restTemplate.postForObject(
                POST_SERVICE_URL + "/post/getall/", usersId, List.class);
        return postDtos;
    }

    public void addPost(String message, int userId) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        restTemplate.put(POST_SERVICE_URL + "/post/create/{userId}", message, userId);
    }

    public void addPost(PostDto postDto) {
        restTemplate.put(POST_SERVICE_URL +
                "/post/create/{userId}", postDto.getMessage(), postDto.getUserId());
    }

    public void updatePost(String message, int userId) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        restTemplate.postForLocation(
                POST_SERVICE_URL + "/post/update/{postId}", message, userId);
    }

    public void updatePost(PostDto postDto) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        restTemplate.postForLocation(POST_SERVICE_URL +
                "/post/update/{postId}", postDto.getMessage(), postDto.getUserId());
    }

    public void deletePost(int postId) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/{postId}", postId);
    }

    public void deletePost(PostDto postDto) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/{postId}", postDto.getPostId());
    }

    public void deleteAllUserPost(int userId) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/all/{userId}", userId);
    }

    public void deleteAllUserPost(PostDto postDto) {
        if(POST_SERVICE_URL == null) {
            POST_SERVICE_URL = getURI(POST_SERVICE_NAME);
        }
        restTemplate.delete(POST_SERVICE_URL + "/post/delete/all/{userId}", postDto.getUserId());
    }

    private String getURI(String appName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(appName);
        ServiceInstance instance = instances.get(0);
        return instance.getUri().toString();
    }
}
