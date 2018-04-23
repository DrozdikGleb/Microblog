package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.UsersDto;

import java.util.List;

@Component
public class ProfileExchangeClient {

    @Autowired
    private DiscoveryClient discoveryClient;

    private static final String PROFILE_SERVICE_NAME = "PROFILE-SERVICE";

    private static String PROFILE_SERVICE_URL;
//    private static final String PROFILE_SERVICE_URL = "http://localhost:8081";

    private final RestTemplate restTemplate;

    @Autowired
    public ProfileExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto getUser(Long id){
        if(PROFILE_SERVICE_URL == null) {
            PROFILE_SERVICE_URL = getURI(PROFILE_SERVICE_NAME);
        }
        return restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/{id}", UserDto.class, id);
    }

    public List<UserDto> getUsersList() {
        if(PROFILE_SERVICE_URL == null) {
            PROFILE_SERVICE_URL = getURI(PROFILE_SERVICE_NAME);
        }
        UsersDto usersDto = restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/", UsersDto.class);
        return usersDto.getItems();
    }

    public List<UserDto> getFriendsList(List<Long> friendsId){
        if(PROFILE_SERVICE_URL == null) {
            PROFILE_SERVICE_URL = getURI(PROFILE_SERVICE_NAME);
        }
        UsersDto usersDto = restTemplate.postForObject(PROFILE_SERVICE_URL + "/user/", friendsId, UsersDto.class);
        return usersDto.getItems();
    }

    public void updateUser(UserDto user) {
        if(PROFILE_SERVICE_URL == null) {
            PROFILE_SERVICE_URL = getURI(PROFILE_SERVICE_NAME);
        }
        HttpEntity<UserDto> requestBody = new HttpEntity<>(user);
        restTemplate.put(PROFILE_SERVICE_URL + "/user/{id}", requestBody, user.getId());
    }

    public UserDto createUser(UserDto user) {
        if(PROFILE_SERVICE_URL == null) {
            PROFILE_SERVICE_URL = getURI(PROFILE_SERVICE_NAME);
        }
        return restTemplate.postForObject(PROFILE_SERVICE_URL + "/user/", user, UserDto.class);
    }

    public void deleteUser(UserDto user) {
        if(PROFILE_SERVICE_URL == null) {
            PROFILE_SERVICE_URL = getURI(PROFILE_SERVICE_NAME);
        }
        restTemplate.delete(PROFILE_SERVICE_URL + "/user/{id}", user.getId());
    }

    private String getURI(String appName) {
        List<ServiceInstance> instances = discoveryClient.getInstances(appName);
        ServiceInstance instance = instances.get(0);
        return instance.getUri().toString();
    }
}
