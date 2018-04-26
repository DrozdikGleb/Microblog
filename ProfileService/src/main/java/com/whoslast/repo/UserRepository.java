package com.whoslast.repo;

import com.whoslast.entities.*;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

// Auto-implemented by Spring
@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    //Iterable<User> findById(Long id);

    boolean existsByLogin(String login);

    User findByLogin(String login);
}