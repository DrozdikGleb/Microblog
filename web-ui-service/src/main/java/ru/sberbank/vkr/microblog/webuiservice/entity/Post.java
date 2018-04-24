package ru.sberbank.vkr.microblog.webuiservice.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import ru.sberbank.vkr.microblog.webuiservice.dto.PostDto;

import java.util.Date;

@Getter
@Setter
public class Post extends PostDto {
private String firstName;
private String lastName;

    public static class Builder {

        private Post built;

        public Builder(Long id) {
            built = new Post();
            built.setPostId(id);
        }

        public Post build() {
            return built;
        }

        public Builder firstName(String firstName) {
            built.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            built.lastName = lastName;
            return this;
        }

        public Builder userId(Long userId) {
            built.setUserId(userId);
            return this;
        }

        public Builder date(Date date) {
            built.setDate(date);
            return this;
        }

        public Builder message(String message) {
            built.setMessage(message);
            return this;
        }

    }
}
