package ru.sberbank.vkr.microblog.webuiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.EncrytedPasswordUtils;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;

import java.util.Arrays;
import java.util.List;

@Service
public class ProfileExchangeClient {
    private static final Logger logger = LoggerFactory.getLogger(ProfileExchangeClient.class);

    private static final String PROFILE_SERVICE_URL = "http://localhost:8092";

    private final RestTemplate restTemplate;

    @Autowired
    public ProfileExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto findUserAccount(String userName) {
        try {
            return restTemplate.getForObject(PROFILE_SERVICE_URL + "/userauth/{login}", UserDto.class, userName);
        } catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc) {
            logger.warn("Profile microservice response for finding user by username {} is {}:{}",
                    userName, httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getStatusText());
            throw new RestClientException(httpClientOrServerExc.getMessage());
        }
    }

    public UserDto getUser(Long id) {
        return restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/{id}", UserDto.class, id);
    }

    public List<UserDto> getUsersList() {
        UserDto[] users = restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/", UserDto[].class);
        return Arrays.asList(users);
    }

    public List<UserDto> getFriendsList(List<Long> friendsId) {
        UserDto[] users = restTemplate.postForObject(PROFILE_SERVICE_URL + "/user/", friendsId, UserDto[].class);
        return Arrays.asList(users);
    }

    public UserDto createUser(UserDto user) {
        try {
            user.setPassword(EncrytedPasswordUtils.encrytePassword(user.getPassword()));
            return restTemplate.postForObject(PROFILE_SERVICE_URL + "/user/", user, UserDto.class);
        }
        catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc){
            logger.warn("Profile microservice response for create user {} is {}:{}",
                    user, httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getStatusText());
            throw new RestClientException(httpClientOrServerExc.getMessage());
        }
    }

    public void updateUser(UserDto user) {
        try {
            restTemplate.put(PROFILE_SERVICE_URL + "/user/{id}", HttpMethod.PUT, user, UserDto.class, user.getId());
        }
        catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc){
            logger.warn("Profile microservice response for update user {} is {}:{}",
                    user, httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getStatusText());

        }
    }

    public void deleteUser(UserDto user) {
        try {
            restTemplate.delete(PROFILE_SERVICE_URL + "/user/{id}", user.getId());
        }
        catch (HttpClientErrorException | HttpServerErrorException httpClientOrServerExc){
            logger.warn("Profile microservice response for delete user {} is {}:{}",
                    user, httpClientOrServerExc.getStatusCode(), httpClientOrServerExc.getStatusText());

        }
    }
}
