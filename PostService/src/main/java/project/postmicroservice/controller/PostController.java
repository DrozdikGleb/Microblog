package project.postmicroservice.controller;

import javafx.geometry.Pos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import project.postmicroservice.entity.Post;
import project.postmicroservice.repository.PostRepository;

import javax.transaction.Transactional;
import java.util.List;

//TODO Отправить посты нескольких пользователей

@RestController
public class PostController {

    @Autowired
    PostRepository postRepository;

    //Return one post by id

    @RequestMapping(value = "/post/get/{postId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<Post> getPost(@PathVariable("postId") long postId) {
        Post post = postRepository.findOne(postId); //проггулить
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //Return all post by User`s ID

    @RequestMapping(value = "/post/getall/{userId}", method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @Transactional
    public ResponseEntity<List<Post>> getAllUserPost(@PathVariable("userId") long userId) {
        List<Post> posts = postRepository.findByUserIdLike(userId);
        if (posts == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(posts, HttpStatus.OK);
    }


    //Create post

    @RequestMapping(value = "/post/create/{userId}", method = RequestMethod.POST)
    @Transactional
    public ResponseEntity<Void> createPost(@PathVariable("userId") long userId,
                                           @RequestBody String message, UriComponentsBuilder ucBuilder) {
        System.out.println("Create user`s post.");
        Post post = new Post(userId, message);
        postRepository.save(post);
        return new ResponseEntity<>(HttpStatus.CREATED);
        //return new ResponseEntity<>(headers, HttpStatus.CREATED);

//        if (postRepository.existsById(post.getPostId())) {
//            System.out.println("A Post with id " + post.getPostId() + " already exist");
//            return new ResponseEntity<>(HttpStatus.CONFLICT);
//        }


//        HttpHeaders headers = new HttpHeaders();
//        headers.setLocation(ucBuilder.path("/post/{id}").buildAndExpand(post.getId()).toUri());
    }


    //Update post by post id

    @RequestMapping(value = "/post/update/{postId}", method = RequestMethod.PUT)
    @Transactional
    public ResponseEntity<Post> updatePost(@PathVariable("postId") long postId, @RequestBody String message) {

        Post post = postRepository.findOne(postId);
        if (post==null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        post.setMessage(message);
        postRepository.save(post);
        return new ResponseEntity<>(post, HttpStatus.OK);
    }

    //Delete post by id

    @RequestMapping(value = "/post/delete/{postId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<Post> deletePost(@PathVariable("postId") long postId) {

        Post post = postRepository.findOne(postId);
        if (post == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        postRepository.delete(postId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //Delete all message by userId

    @RequestMapping(value = "/post/delete/all/{userId}", method = RequestMethod.DELETE)
    @Transactional
    public ResponseEntity<Post> deleteAllPost(@PathVariable("userId") long userId) {
        //Либо...
        List<Post> postList = postRepository.findByUserIdLike(userId);
        if(postList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        for(Post p : postList) {
            postRepository.delete(p);
        }
        return new ResponseEntity<>(HttpStatus.OK);
        //Либо
//        postRepository.deleteAllMessageByUserId(userId);
//        return new ResponseEntity<>(HttpStatus.OK);
    }
}
