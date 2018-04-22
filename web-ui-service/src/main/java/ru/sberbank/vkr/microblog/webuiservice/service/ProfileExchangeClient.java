package ru.sberbank.vkr.microblog.webuiservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import ru.sberbank.vkr.microblog.webuiservice.entity.AppUser;
import ru.sberbank.vkr.microblog.webuiservice.dto.UserDto;
import ru.sberbank.vkr.microblog.webuiservice.dto.UsersDto;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProfileExchangeClient {
    //    private static final String PROFILE_SERVICE_URL = "http://PROFILE_SERVICE";
    private static final String PROFILE_SERVICE_URL = "http://localhost:8092";

    private final RestTemplate restTemplate;

    @Autowired
    public ProfileExchangeClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public UserDto findUserAccount(String userName) {
        //TODO: должен быть запрос на пользователя по логину
        return new UserDto(1L, "mylogin", "$2a$10$SsBRq81EAU5dL0LcPmyzvOAIsL08DTOm44IFojlKvg71o/pQmgxHS");
//        return restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/{id}", UserDto.class, 1);
    }

    public UserDto getUser(Long id) {
        return restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/{id}", UserDto.class, id);
    }

    public List<UserDto> getUsersList() {
        UsersDto usersDto = restTemplate.getForObject(PROFILE_SERVICE_URL + "/user/", UsersDto.class);
        return usersDto.getItems();
    }

    public List<UserDto> getFriendsList(List<Long> friendsId) {
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
