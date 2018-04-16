package ru.sberbank.vkr.microblog.webuiservice.entities;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter @Setter
@EqualsAndHashCode
@ToString
public class Post {
    private Long id;
    private Long userId;
    private LocalDateTime dateTime;
    private String text;

    public Post(Long id, Long userId, LocalDateTime dateTime, String text) {
        this.id = id;
        this.userId = userId;
        this.dateTime = dateTime;
        this.text = text;
    }

}
