package friendship_microservice.repository;

import friendship_microservice.entity.Friendship;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface FriendshipRepository extends PagingAndSortingRepository<Friendship, Long> {
    List<Friendship> findByUserIdLike(long userId);

    int countFriendshipByUserIdAndFriendIdLike(long userId, long friendId);

    void deleteFriendshipByUserIdAndFriendIdLike(long userId, long friendId);
}
