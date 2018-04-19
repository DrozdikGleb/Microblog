package ru.sberbank.vkr.microblog.webuiservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class PostsDto {
    private List<PostDto> items;

    public PostsDto(List<PostDto> items) {
        this.items = items;
    }
}
