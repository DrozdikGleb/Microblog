package friendship_microservice.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Created by Gleb on 16.04.2018
 */

public interface FriendshipRepository extends PagingAndSortingRepository<com.javasampleapproach.restdata.model.Friendship, Long> {
    //List<Friendship> getAllByUser_id(long id);
    //List<Friendship> findAllByuser_id(long user_id);
    @Query(value = "SELECT friendship FROM friendship_tbl friendship WHERE user_id = :user_id", nativeQuery = true)
    List<Friendship> findFriendshipById(@Param("user_id") Long user_id);
}
