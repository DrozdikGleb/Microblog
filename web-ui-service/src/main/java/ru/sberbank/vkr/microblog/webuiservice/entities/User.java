package ru.sberbank.vkr.microblog.webuiservice.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class User {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String iconURN;

    public User(Long id, String firstName, String iconURN) {
        this.id = id;
        this.firstName = firstName;
        this.iconURN = iconURN;
    }

    public User(Long id, String firstName, String lastName, String email, String iconURN) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.iconURN = iconURN;
    }
}
