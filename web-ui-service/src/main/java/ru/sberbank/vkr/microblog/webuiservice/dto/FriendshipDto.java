package ru.sberbank.vkr.microblog.webuiservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FriendshipDto {
    private long id;
    private long user_id;
    private long friend_id;

    public FriendshipDto(long user_id, long friend_id) {
        this.user_id = user_id;
        this.friend_id = friend_id;
    }

    public FriendshipDto(long id, long user_id, long friend_id) {
        this(user_id, friend_id);
        this.id = id;
    }
}
