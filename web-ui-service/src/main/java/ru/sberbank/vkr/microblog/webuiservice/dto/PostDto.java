package ru.sberbank.vkr.microblog.webuiservice.dto;

import lombok.*;

import java.util.Date;

@Getter @Setter
@NoArgsConstructor
@ToString
public class PostDto {
    private Long postId;
    private Long userId;
    private Date date;
    private String message;
}
