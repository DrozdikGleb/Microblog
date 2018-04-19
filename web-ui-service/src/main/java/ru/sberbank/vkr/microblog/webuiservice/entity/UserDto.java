package ru.sberbank.vkr.microblog.webuiservice.entity;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    private Long id;

    private String login;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Integer iconId;

    public UserDto(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public UserDto(UserDto user) {
        this.id = user.id;
        this.login = user.login;
        this.password = user.password;
        this.email = user.email;
        this.firstName = user.firstName;
        this.lastName = user.lastName;
        this.iconId = user.iconId;
    }
}
