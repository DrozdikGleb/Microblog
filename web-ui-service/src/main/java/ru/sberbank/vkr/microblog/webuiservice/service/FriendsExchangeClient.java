package ru.sberbank.vkr.microblog.webuiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;

import java.util.*;

@Service
public class FriendsExchangeClient {
    private static final Logger logger = LoggerFactory.getLogger(ProfileExchangeClient.class);

    private static final String FRIENDS_SERVICE_URL = "http://localhost:8091";

    private final RestTemplate restTemplate;

    @Autowired
    public FriendsExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public List<Long> getFriendsList(Long userId) {
        try {
            Long[] friends = restTemplate.getForObject(FRIENDS_SERVICE_URL + "/friendship/{userId}", Long[].class, userId);
            return Arrays.asList(friends);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.debug("Getting friends id list for user {} returns {} : {}", userId,
                    httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getMessage());
            return Collections.emptyList();
        }
    }

    public List<Long> getFriendsList(UserDto userDto) {
        return getFriendsList(userDto.getId());
    }

    public void follow(Long userId, Long friendId) {
        restTemplate.put(FRIENDS_SERVICE_URL + "/friendship/{userId}/{friendId}", userId, friendId);
    }

    public void follow(UserDto user, UserDto friend) {
        follow(user.getId(), friend.getId());
    }

    public void unfollow(Long userId, Long friendId) {
        restTemplate.delete(FRIENDS_SERVICE_URL + "/friendship/{userId}/{friendId}", userId, friendId);
    }

    public void unfollow(UserDto user, UserDto friend) {
        unfollow(user.getId(), friend.getId());
    }

}
