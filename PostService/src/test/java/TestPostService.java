import org.junit.AfterClass;
import org.junit.BeforeClass;
import static org.junit.Assert.*;

import org.springframework.web.client.RestTemplate;
import project.postmicroservice.entity.Post;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;


/**
 * Для старта необходим параметр create в properties
 */
public class TestPostService {

    private static final String URL = "http://localhost:8090";

    public static void main(String args[]){
        System.out.println("Create Post");
        createPost(1, "AbraKadabra");
        createPost(2, "Expeliarmus");
        createPost(3, "PetrificusTotalus");
        createPost(1, "Lumos");
        createPost(5, "Nox");
        createPost(6, "VinguardimLeviosa");
        createPost(2, "Bum");

//        System.out.println("Update Post");
//        updatePost(2, "AAAAAAAAA");
//
//        System.out.println("Get one Post");
//        Post post;
//        post = getPost(1);
//        assertEquals("AbraKadabra", post.getMessage());
//        post = getPost(6);
//        assertEquals("VinguardimLeviosa", post.getMessage());
//        post = getPost(2);
//        assertEquals("AAAAAAAAA", post.getMessage());
//
//        System.out.println("Get many Post`s");
//        ArrayList<Integer> arrayList = new ArrayList();
//        arrayList.add(1);
//        arrayList.add(2);
//        arrayList.add(3);
//        List<Post> posts = getPostByUsers(arrayList);
//        assertEquals(5, posts.size());
//
//        System.out.println("Delete post dy user`s ID");
//        deleteAllPostByUserId(2L);
//
//        System.out.println("Delete All Post");
//        deletePost(1L);
//        deletePost(3L);
//        deletePost(4L);
//        deletePost(5L);
//        deletePost(6L);
    }

    /* GET */
    private static Post getPost(long postId){
        RestTemplate restTemplate = new RestTemplate();
        Post post = restTemplate.getForObject(URL+"/post/get/" + String.valueOf(postId), Post.class);
        return post;
    }

    /* POST */
    private static void createPost(long userId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(URL + "/post/create/" + String.valueOf(userId),
                message, String.class);
    }

    private static List<Post> getPostByUsers(List<Integer> usersId){
        RestTemplate restTemplate = new RestTemplate();
        List<Post> posts = restTemplate.postForObject(URL+"/post/getall/", usersId, List.class);
        return posts;
    }

    /* PUT */
    private static void updatePost(long postId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.put(URL+"/post/update/" + String.valueOf(postId), message, String.class);
    }

    /* DELETE */
    private static void deletePost(Long postId) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(URL+"/post/delete/" + String.valueOf(postId));
    }

    private static void deleteAllPostByUserId(Long userId) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(URL+"/post/delete/all/" + String.valueOf(userId));
    }

    @BeforeClass
    public static void createPullPost() {
        createPost(1, "AbraKadabra");
        createPost(2, "Expeliarmus");
        createPost(3, "PetrificusTotalus");
        createPost(1, "Lumos");
        createPost(5, "Nox");
        createPost(6, "VinguardimLeviosa");
    }

    @AfterClass
    public static void deleteAllPost() {
        deletePost(1L);
        deletePost(2L);
        deletePost(3L);
        deletePost(1L);
        deletePost(5L);
        deletePost(6L);
    }
}
