package ru.sberbank.vkr.microblog.webuiservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FriendsDto {
    private List<Long> items;

    public FriendsDto(List<Long> items) {
        this.items = items;
    }
}
