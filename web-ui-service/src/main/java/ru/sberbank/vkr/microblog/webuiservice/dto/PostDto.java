package ru.sberbank.vkr.microblog.webuiservice.dto;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode
@ToString
public class PostDto {
    private Long postId;
    private Long userId;
    private Date date;
    private String message;

    public PostDto(long userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    public PostDto(long postId, long userId, Date date, String message) {
        this.postId = postId;
        this.userId = userId;
        this.date = date;
        this.message = message;
    }

}
