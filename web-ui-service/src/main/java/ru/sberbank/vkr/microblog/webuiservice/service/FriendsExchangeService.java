package ru.sberbank.vkr.microblog.webuiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.Profile;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service
public class FriendsExchangeService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileExchangeService.class);

    private static final String FRIEND_SERVICE_NAME = "FRIEND-SERVICE";
    private final RestTemplate restTemplate;
    private final DiscoveryClient discoveryClient;

    private String friendsServiceUrl;


    @Autowired
    public FriendsExchangeService(RestTemplate restTemplate, DiscoveryClient discoveryClient) {
        this.restTemplate = restTemplate;
        this.discoveryClient = discoveryClient;

        List<ServiceInstance> instances = discoveryClient.getInstances(FRIEND_SERVICE_NAME);
        ServiceInstance instance = instances.get(0);
        friendsServiceUrl = instance.getUri().toString();
    }

    public List<Long> getFriendsList(Long userId) {
        logger.debug("Request microservice to get friends list for user with id: {}", userId);
        try {
            Long[] friends = restTemplate.getForObject(friendsServiceUrl + "/friendship/{userId}", Long[].class, userId);
            return Arrays.asList(friends);
        } catch (HttpClientErrorException hceEx) {
            if (hceEx.getStatusCode() == HttpStatus.NOT_FOUND) {
                return Collections.emptyList();
            }
            throw hceEx;
        }
    }

    public List<Long> getFriendsList(Profile profile) {
        logger.debug("Request microservice to get friends list for user: {}", profile);
        return getFriendsList(profile.getId());
    }

    public void follow(Long userId, Long friendId) {
        logger.debug("Request microservice to add friendship for user: {} - with: {}", userId, friendId);

        restTemplate.postForObject(friendsServiceUrl + "/friendship/{userId}/{friendId}",
                null, Void.class, userId, friendId);
    }

    public void follow(Profile userProfile, Profile friendProfile) {
        logger.debug("Request microservice to add friendship for user: {} - with: {}", userProfile, friendProfile);
        follow(userProfile.getId(), friendProfile.getId());
    }

    public void unfollow(Long userId, Long friendId) {
        logger.debug("Request microservice to delete friendship for user: {} - with: {}", userId, friendId);
        restTemplate.delete(friendsServiceUrl + "/friendship/{userId}/{friendId}", userId, friendId);
    }

    public void unfollow(Profile userProfile, Profile friendProfile) {
        logger.debug("Request microservice to delete friendship for user: {} - with: {}", userProfile, friendProfile);
        unfollow(userProfile.getId(), friendProfile.getId());
    }

    public void deleteAllUserFriends(Long userId) {
        logger.debug("Request microservice to delete all friendships for user: {}", userId);
        //TODO: Расскоменнтировать как будет такой функционал в микросервисе друзей
//        restTemplate.delete(friendsServiceUrl + "/friendship/{userId}", userId);
    }

    public void deleteAllUserFriends(Profile userProfile) {
        logger.debug("Request microservice to delete all friendships for user: {}", userProfile);
        deleteAllUserFriends(userProfile.getId());

    }
}
