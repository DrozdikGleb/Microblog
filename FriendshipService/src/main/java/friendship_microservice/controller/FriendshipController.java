package friendship_microservice.controller;

import friendship_microservice.entity.Friendship;
import friendship_microservice.repository.FriendshipRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/friendship")
public class FriendshipController {

    private final FriendshipRepository friendshipRepository;

    @Autowired
    public FriendshipController(FriendshipRepository friendshipRepository) {
        this.friendshipRepository = friendshipRepository;
    }

    @RequestMapping(method = RequestMethod.POST, value = "/{user_id}/{friend_id}")
    public ResponseEntity<Void> createFriendship(@PathVariable("user_id") long user_id,
                                                 @PathVariable("friend_id") long friend_id) {
        Friendship friendship = new Friendship(user_id, friend_id);
        friendshipRepository.save(friendship);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.DELETE, value = "/{user_id}/{friend_id}")
    @Transactional
    public ResponseEntity<Void> deleteFriendship(@PathVariable("user_id") long user_id,
                                                 @PathVariable("friend_id") long friend_id) {
        if (friendshipRepository.countFriendshipByUserIdAndFriendIdLike(user_id, friend_id) == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        } else {
            friendshipRepository.deleteFriendshipByUserIdAndFriendIdLike(user_id, friend_id);
            return new ResponseEntity<>(HttpStatus.OK);
        }
    }

    @RequestMapping(method = RequestMethod.GET, value = "/{user_id}")
    public ResponseEntity<List<Long>> getFriendsList(@PathVariable("user_id") long user_id) {
        List<Friendship> friendsList = friendshipRepository.findByUserIdLike(user_id);
        if (friendsList.size() == 0) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        List<Long> friends = friendsList.stream()
                .map(Friendship::getFriendId)
                .collect(Collectors.toList());
        return new ResponseEntity<>(friends, HttpStatus.OK);
    }
}
