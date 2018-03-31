package ru.sberbank.vkr.microblog.lib;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class User {
    private int id;
    private String firstName;
    private String lastName;
    private Date birthdate;
    private String email;
    private String iconURN;

    public User(int id, String firstName, String iconURN) {
        this.id = id;
        this.firstName = firstName;
        this.iconURN = iconURN;
    }

    public User(int id, String firstName, String lastName, Date birthdate, String email, String iconURN) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.email = email;
        this.iconURN = iconURN;
    }
}
