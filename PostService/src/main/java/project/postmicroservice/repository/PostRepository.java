package project.postmicroservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import project.postmicroservice.entity.Post;

import java.util.List;

@Repository
public interface PostRepository extends JpaRepository<Post, Long> {

    @Query(value = "DELETE FROM post WHERE post.userID = ?1", nativeQuery = true)
    void deleteAllMessageByUserId(long userId);

    List<Post> findByUserIdLike(long userId);

}
