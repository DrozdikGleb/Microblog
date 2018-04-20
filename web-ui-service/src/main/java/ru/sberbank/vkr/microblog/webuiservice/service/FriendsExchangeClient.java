package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.FriendsDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;

import java.util.List;

@Component
public class FriendsExchangeClient {

//    private static final String FRIENDS_SERVICE_URL = "http://FRIENDS_SERVICE";
    private static final String FRIENDS_SERVICE_URL = "http://localhost:8083";

    private final RestTemplate restTemplate;

    private UserDto currentUser;

    @Autowired
    public FriendsExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Long> getFriendsList(UserDto currentUser) {
        FriendsDto friends = restTemplate.getForObject(FRIENDS_SERVICE_URL + "/{userId}", FriendsDto.class, currentUser.getId());
        return friends.getItems();
    }

    public void follow(UserDto friend) {
        restTemplate.put(FRIENDS_SERVICE_URL + "/{userId}/{friendId}", currentUser.getId(), friend.getId());
    }

    public void unfollow(UserDto friend) {
        restTemplate.delete(FRIENDS_SERVICE_URL + "/{userId}/{friendId}", currentUser.getId(), friend.getId());
    }

}
