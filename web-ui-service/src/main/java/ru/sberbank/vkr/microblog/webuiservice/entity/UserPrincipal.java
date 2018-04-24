package ru.sberbank.vkr.microblog.webuiservice.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserPrincipal {
    private Long id;

    private String login;

    private String password;

    public static class Builder {

        private UserPrincipal built;

        public Builder(Long id) {
            built = new UserPrincipal();
            built.id = id;
        }

        public UserPrincipal build() {
            return built;
        }

        public Builder id(Long id) {
            built.id = id;
            return this;
        }

        public Builder login(String login) {
            built.login = login;
            return this;
        }

        public Builder password(String password) {
            built.password = password;
            return this;
        }

    }
}
