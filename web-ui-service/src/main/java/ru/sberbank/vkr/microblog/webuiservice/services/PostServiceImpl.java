package ru.sberbank.vkr.microblog.webuiservice.services;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import ru.sberbank.vkr.microblog.webuiservice.entities.Post;
import ru.sberbank.vkr.microblog.webuiservice.interceptors.AuthInterceptor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PostServiceImpl implements PostService {

    private static final String serviceUrl = "http://localhost:8080";
    private static final Logger logger = LoggerFactory.getLogger(AuthInterceptor.class);


    @Override
    public Post getPost(int postId) {
        String url = serviceUrl + " /post/get/" + postId;

        String request = request(url, "GET");
        if (request != null) {
            JSONObject myResponse = new JSONObject();
            LocalDateTime dateTime = LocalDateTime.parse(myResponse.getString("date"));
            return new Post(myResponse.getLong("postID"),
                    myResponse.getLong("userID"),
                    dateTime,
                    myResponse.getString("message"));
        }
        return null;
    }

    @Override
    public List<Post> getUserPosts(int userId) {
        String url = serviceUrl + " /post/getall/" + userId;

        String request = request(url, "GET");
        if (request != null) {
            JSONObject myResponse = new JSONObject();
            LocalDateTime dateTime = LocalDateTime.parse(myResponse.getString("date"));

//            myResponse.getJSONArray();
            List<Post> posts = new ArrayList<>();

            posts.add(new Post(myResponse.getLong("postID"),
                    myResponse.getLong("userID"),
                    dateTime,
                    myResponse.getString("message")));

            return posts;
        }

        return Collections.emptyList();
    }

    @Override
    public List<Post> getFeedForUser(int userId) {
//TODO: добавить, как появится в api другого микросервиса
        return Collections.emptyList();
    }

    @Override
    public void createPost(Post post) {
        String url = serviceUrl + " /post/create/" + post.getUserId();
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            if (con.getResponseCode() == HttpStatus.CREATED.value()) {

            }
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    @Override
    public void deletePost(int postId) {
        String url = serviceUrl + " /post/delete/" + postId;
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("POST");
            if (con.getResponseCode() == HttpStatus.OK.value()) {

            }
        } catch (IOException e) {
            logger.warn(e.toString());
        }
    }

    private String request(String url, String requestMethod) {
        try {
            URL obj = new URL(url);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod(requestMethod);
            if (con.getResponseCode() == HttpStatus.OK.value()) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(con.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();
                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
                return response.toString();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
