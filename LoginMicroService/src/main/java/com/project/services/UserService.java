package com.project.services;

import com.project.models.User;

/**
 * Created by ole-espen.lundsor on 08/05/2017.
 */
public interface UserService {

    void save(User user);

    User findByUsername(String username);
}
