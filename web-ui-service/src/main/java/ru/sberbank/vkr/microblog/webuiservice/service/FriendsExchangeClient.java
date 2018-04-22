package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.dto.FriendsDto;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;

import java.util.List;

@Service
public class FriendsExchangeClient {

    //    private static final String FRIENDS_SERVICE_URL = "http://FRIENDS_SERVICE";
    private static final String FRIENDS_SERVICE_URL = "http://localhost:8091";

    private final RestTemplate restTemplate;

    @Autowired
    public FriendsExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Long> getFriendsList(Long userId) {
        FriendsDto friends = restTemplate.getForObject(FRIENDS_SERVICE_URL + "/{userId}", FriendsDto.class, userId);
        return friends.getItems();
    }

    public List<Long> getFriendsList(UserDto userDto) {
        return getFriendsList(userDto.getId());
    }

    public void follow(Long userId, Long friendId) {
        restTemplate.put(FRIENDS_SERVICE_URL + "/{userId}/{friendId}", userId, friendId);
    }

    public void follow(UserDto user, UserDto friend) {
        follow(user.getId(), friend.getId());
    }

    public void unfollow(Long userId, Long friendId) {
        restTemplate.delete(FRIENDS_SERVICE_URL + "/{userId}/{friendId}", userId, friendId);
    }

    public void unfollow(UserDto user, UserDto friend) {
        unfollow(user.getId(), friend.getId());
    }

}
