package ru.sberbank.vkr.microblog.webuiservice.services;

import ru.sberbank.vkr.microblog.webuiservice.entities.User;

public interface UserService {

    User authUser(String userName, String password);

    User getUser(int id);

    void createUser(User user);

    void updateUser(User user);

    void deleteUser(int id);
}
