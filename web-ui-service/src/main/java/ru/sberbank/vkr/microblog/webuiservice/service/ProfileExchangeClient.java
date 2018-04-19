package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.UsersDto;

import javax.security.auth.login.AccountNotFoundException;
import java.util.List;

@Component
public class ProfileExchangeClient {
    private static final String PROFILE_SERVICE_URL = "http://PROFILE_SERVICE";

    @LoadBalanced
    private RestTemplate restTemplate;

    private String profileServiceUrl;

    public UserDto getUser(Long id){
        return restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/{id}", UserDto.class, id);
    }

    public List<UserDto> getUsersList() {
        UsersDto usersDto = restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/", UsersDto.class);
        return usersDto.getItems();
    }

    public List<UserDto> getFriendsList(List<Long> friendsId){
        UsersDto usersDto = restTemplate.postForObject(PROFILE_SERVICE_URL + "/user/", friendsId, UsersDto.class);
        return usersDto.getItems();
    }

    public void updateUser(UserDto user) {
        HttpEntity<UserDto> requestBody = new HttpEntity<>(user);
        restTemplate.put(PROFILE_SERVICE_URL + "/user/{id}", requestBody, user.getId());
    }

    public UserDto createUser(UserDto user) {
        return restTemplate.postForObject(PROFILE_SERVICE_URL + "/user/", user, UserDto.class);
    }

    public void deleteUser(UserDto user) {
        restTemplate.delete(PROFILE_SERVICE_URL + "/user/{id}", user.getId());
    }


}
