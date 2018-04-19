package ru.sberbank.vkr.microblog.webuiservice.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class FriendsDto {
    private List<Long> items;
}
