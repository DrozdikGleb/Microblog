package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.FriendsDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;

import java.util.List;

@Component
public class FriendsExchangeClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String FRIEND_SERVICE_NAME = "FRIEND-SERVICE";

    private static String FRIENDS_SERVICE_URL;
//    private static final String FRIENDS_SERVICE_URL = "http://localhost:8083";

    private final RestTemplate restTemplate;

    private UserDto currentUser;

    @Autowired
    public FriendsExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Long> getFriendsList(UserDto currentUser) {
        if(FRIENDS_SERVICE_URL == null) {
            FRIENDS_SERVICE_URL = getURI(FRIEND_SERVICE_NAME);
        }
        FriendsDto friends = restTemplate.getForObject(FRIENDS_SERVICE_URL + "/{userId}", FriendsDto.class, currentUser.getId());
        return friends.getItems();
    }

    public void follow(UserDto friend) {
        if(FRIENDS_SERVICE_URL == null) {
            FRIENDS_SERVICE_URL = getURI(FRIEND_SERVICE_NAME);
        }
        restTemplate.put(FRIENDS_SERVICE_URL + "/{userId}/{friendId}", currentUser.getId(), friend.getId());
    }

    public void unfollow(UserDto friend) {
        restTemplate.delete(FRIENDS_SERVICE_URL + "/{userId}/{friendId}", currentUser.getId(), friend.getId());
    }

    private String getURI(String appName) {
        if(FRIENDS_SERVICE_URL == null) {
            FRIENDS_SERVICE_URL = getURI(FRIEND_SERVICE_NAME);
        }
        List<ServiceInstance> instances = discoveryClient.getInstances(appName);
        ServiceInstance instance = instances.get(0);
        return instance.getUri().toString();
    }

}
