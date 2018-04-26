package ru.sberbank.vkr.microblog.webuiservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.EncrytedPasswordUtils;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.entity.Profile;
import ru.sberbank.vkr.microblog.webuiservice.entity.UserPrincipal;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProfileExchangeService {
    private static final Logger logger = LoggerFactory.getLogger(ProfileExchangeService.class);

    private static final String PROFILE_SERVICE_URL = "http://profile-service";

    private static final String REQUEST_USER_AUTH = "/userauth/{login}";
    private static final String REQUEST_USER_BY_ID = "/user/{id}";
    private static final String REQUEST_USER = "/user/";

    private final RestTemplate restTemplate;


    @Autowired
    public ProfileExchangeService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserPrincipal findUserAccount(String userName) {
        logger.debug("Searching for user with login: {}", userName);
        try {
            UserDto userDto = restTemplate.getForObject(PROFILE_SERVICE_URL + REQUEST_USER_AUTH, UserDto.class, userName);
            return new UserPrincipal.Builder(userDto.getId())
                    .login(userDto.getLogin())
                    .password(userDto.getPassword())
                    .build();
        } catch (HttpClientErrorException hceEx) {
            if (hceEx.getStatusCode() == HttpStatus.NOT_FOUND) {
                return null;
            }
            throw hceEx;
        }
    }

    public Profile getUser(Long id) {
        logger.debug("Request microservice to get user profile for id: {}", id);

        UserDto userDto = restTemplate.getForObject(PROFILE_SERVICE_URL + REQUEST_USER_BY_ID, UserDto.class, id);
        return new Profile.Builder(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build();
    }

    public List<Profile> getUsersList() {
        logger.debug("Request microservice to receive all profiles list");

        UserDto[] users = restTemplate.getForObject(PROFILE_SERVICE_URL + REQUEST_USER, UserDto[].class);
        return Arrays.stream(users).map(userDto -> new Profile.Builder(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build()).collect(Collectors.toList());
    }

    public List<Profile> getFriendsList(List<Long> friendsId) {
        logger.debug("Request microservice to receive friends profiles list for ids: {}", friendsId);

        UserDto[] users = restTemplate.postForObject(PROFILE_SERVICE_URL + "/user/getbyids/", friendsId, UserDto[].class);
        return Arrays.stream(users).map(userDto -> new Profile.Builder(userDto.getId())
                .email(userDto.getEmail())
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .build()).collect(Collectors.toList());
    }

    public void createUser(UserDto user) {
        logger.debug("Request microservice to create a new user: {}", user);
        user.setPassword(EncrytedPasswordUtils.encrytePassword(user.getPassword()));
        restTemplate.postForObject(PROFILE_SERVICE_URL + REQUEST_USER, user, UserDto.class);
    }

    public void updateUser(Profile userProfile) {
        logger.debug("Request microservice to update user: {}", userProfile);
        UserDto user = new UserDto(userProfile);
        restTemplate.put(PROFILE_SERVICE_URL + REQUEST_USER_BY_ID, user, user.getId());
    }

    public void deleteUser(Profile userProfile) {
        logger.debug("Request microservice to delete user: {}", userProfile);
        restTemplate.delete(PROFILE_SERVICE_URL + REQUEST_USER_BY_ID, userProfile.getId());
    }
}
