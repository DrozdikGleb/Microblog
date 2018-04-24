package ru.sberbank.vkr.microblog.webuiservice.entity;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class Profile {

    private Long id;

    private String login;

    private String email;

    private String firstName;

    private String lastName;

    public static class Builder {

        private Profile built;

        public Builder(Long id) {
            built = new Profile();
            built.id = id;
        }

        public Profile build() {
            return built;
        }

        public Builder login(String login) {
            built.login = login;
            return this;
        }
        public Builder email(String email) {
            built.email = email;
            return this;
        }

        public Builder firstName(String firstName) {
            built.firstName = firstName;
            return this;
        }

        public Builder lastName(String lastName) {
            built.lastName = lastName;
            return this;
        }

    }
}
