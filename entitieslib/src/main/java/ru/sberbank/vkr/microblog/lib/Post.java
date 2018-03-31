package ru.sberbank.vkr.microblog.lib;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class Post {
    private long id;
    private int userId;
    private LocalDateTime dateTime;
    private String text;

    public Post(long id, int userId, LocalDateTime dateTime, String text) {
        this.id = id;
        this.userId = userId;
        this.dateTime = dateTime;
        this.text = text;
    }
}
