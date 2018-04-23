package ru.sberbank.vkr.microblog.webuiservice.dto;

import lombok.*;

@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class UserDto {
    private Long id;

    private String login;

    private String password;

    private String email;

    private String firstName;

    private String lastName;

    private Integer iconId;


    public UserDto(Long id, String login, String password) {
        this.id = id;
        this.login = login;
        this.password = password;
    }

}
