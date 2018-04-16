import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

import org.springframework.web.client.RestTemplate;
import project.postmicroservice.entity.Post;

import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;



public class TestPostService {

    private static final String URL = "http://localhost:8090";

//    @BeforeClass
//    public static void createPullPost() {
//        createPost(1, "AbraKadabra");
//        createPost(2, "Expeliarmus");
//        createPost(3, "PetrificusTotalus");
//        createPost(1, "Lumos");
//        createPost(5, "Nox");
//        createPost(6, "VinguardimLeviosa");
//    }
//
//    @AfterClass
//    public static void deleteAllPost() {
//        deletePost(1L);
//        deletePost(2L);
//        deletePost(3L);
//        deletePost(1L);
//        deletePost(5L);
//        deletePost(6L);
//    }

    public static void main(String args[]){
        System.out.println("Create Post");
        createPost(1, "AbraKadabra");
        createPost(2, "Expeliarmus");
        createPost(3, "PetrificusTotalus");
        createPost(1, "Lumos");
        createPost(5, "Nox");
        createPost(6, "VinguardimLeviosa");
        createPost(2, "Bum");

        System.out.println("Update Post");
        updatePost(2, "AAAAAAAAA");

        System.out.println("Get one Post");
        Post post;
        post = getPost(1);
        assertEquals("AbraKadabra", post.getMessage());
        post = getPost(6);
        assertEquals("VinguardimLeviosa", post.getMessage());
        post = getPost(2);
        assertEquals("AAAAAAAAA", post.getMessage());

        System.out.println("Get many Post`s");
        ArrayList<Integer> arrayList = new ArrayList();
        arrayList.add(1);
        arrayList.add(2);
        arrayList.add(3);
        List<Post> posts = getPostByUsers(2);
        assertEquals(2, posts.size());

        System.out.println("Delete All Post");
        deletePost(1L);
        deletePost(2L);
        deletePost(3L);
        deletePost(4L);
        deletePost(5L);
        deletePost(6L);

    }

    /* GET */
    @SuppressWarnings("unchecked")
    private static void listAllUsers(){
        System.out.println("Testing listAllUsers API-----------");

        RestTemplate restTemplate = new RestTemplate();
        List<LinkedHashMap<String, Object>> usersMap = restTemplate.getForObject(URL+"/user/", List.class);

        if(usersMap!=null){
            for(LinkedHashMap<String, Object> map : usersMap){
                System.out.println("User : id= "+map.get("id")+", login = "+map.get("login"));
            }
        }else{
            System.out.println("No user exist----------");
        }
    }

    /* GET */
    private static Post getPost(long postId){
        RestTemplate restTemplate = new RestTemplate();
        Post post = restTemplate.getForObject(URL+"/post/get/" + String.valueOf(postId), Post.class);
        return post;
    }

    private static List<Post> getPostByUsers(long userId){
        RestTemplate restTemplate = new RestTemplate();
        List<Post> posts = restTemplate.getForObject(URL+"/post/getall/" + String.valueOf(userId), List.class);
        return posts;
    }



    /* POST */
    private static void createPost(long userId, String message) {
        RestTemplate restTemplate = new RestTemplate();
        URI uri = restTemplate.postForLocation(URL + "/post/create/" + String.valueOf(userId),
                message, String.class);
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


    /* DELETE */
    private static void deleteAllUsers() {
        System.out.println("Testing all delete Users API----------");
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.delete(URL+"/user/");
    }
}
