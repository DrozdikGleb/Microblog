package ru.sberbank.vkr.microblog.webuiservice.dto;

import lombok.*;
import ru.sberbank.vkr.microblog.webuiservice.entity.Profile;

@Getter @Setter
@NoArgsConstructor
@ToString
public class UserDto {
    private Long id;

    private String login;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Long iconId;

    public UserDto(Profile profile) {
        this.id = profile.getId();
        this.email = profile.getEmail();
        this.firstName = profile.getFirstName();
        this.lastName = profile.getLastName();
        this.login = profile.getLogin();
    }
}
