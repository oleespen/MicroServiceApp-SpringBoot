package com.project.repository;

import com.project.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by ole-espen.lundsor on 08/05/2017.
 */
public interface UserRepository extends JpaRepository<User, Long> {

    User findByUsername(String username);
}
