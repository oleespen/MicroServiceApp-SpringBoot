package com.project.services;

/**
 * Created by ole-espen.lundsor on 08/05/2017.
 */
public interface SecurityService {

    String findLoggedInUsername();

    void autologin(String username, String password);
}
