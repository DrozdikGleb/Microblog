package friendship_microservice.controller;

import friendship_microservice.entity.Friendship;
import friendship_microservice.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Gleb on 16.04.2018
 */
@RestController
@RequestMapping("/friendship")
public class FriendshipController {
    @Autowired
    FriendshipRepository friendshipRepository;

    @RequestMapping(value = "/{user_id}/{friend_id}", method = RequestMethod.PUT)
    public ResponseEntity<Void> createFriendship(@PathVariable("user_id") long user_id,
                                                 @PathVariable("friend_id") long friend_id) {
        Friendship friendship = new Friendship(user_id, friend_id);
        friendshipRepository.save(friendship);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @RequestMapping(value = "/{user_id}/{friend_id}", method = RequestMethod.DELETE)
    public ResponseEntity<Void> deleteFriendship(@PathVariable("user_id") long user_id,
                                                 @PathVariable("friend_id") long friend_id) {
        System.out.println("Deleting Friendship " + user_id + " and " + friend_id);
        Friendship friendship = new Friendship(user_id, friend_id);
        friendshipRepository.delete(friendship);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @RequestMapping(value = "/{user_id}", method = RequestMethod.GET)
    public ResponseEntity<List<Long>> getFriendsList(@PathVariable("user_id") long user_id) {
        List<Friendship> friendsList = friendshipRepository.findFriendshipById(user_id);
        if (friendsList == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Long> friends = new ArrayList<>();
        for (Friendship friend:friendsList) {
            friends.add(friend.getFriend_id());
        }
        return new ResponseEntity<List<Long>>(friends, HttpStatus.OK);
    }
}
