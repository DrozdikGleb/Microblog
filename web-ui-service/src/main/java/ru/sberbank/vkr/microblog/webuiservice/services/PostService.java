package ru.sberbank.vkr.microblog.webuiservice.services;

import ru.sberbank.vkr.microblog.webuiservice.entities.Post;

import java.util.List;

public interface PostService {

    Post getPost(int postId);

    List<Post> getUserPosts (int userId);

    List<Post> getFeedForUser (int userId);

    void createPost(Post post);

    void deletePost(int postId);

}
