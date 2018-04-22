package ru.sberbank.vkr.microblog.webuiservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UsersDto {
    private List<UserDto> items;

    public UsersDto(List<UserDto> usersList) {
        this.items = usersList;
    }
}
