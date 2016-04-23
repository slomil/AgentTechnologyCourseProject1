package com.ftn.informatika.agents.user_app.bean;

import exception.AlreadyRegisteredException;
import exception.InsufficientDataException;
import exception.InvalidCredentialsException;
import exception.UsernameExistsException;
import model.Host;
import model.User;


import java.util.List;

/**
 * @author - Srđan Milaković
 */
public interface UserDbLocal {
    User register(String username, String password) throws UsernameExistsException, InsufficientDataException;
    Boolean login(String username, String password, Host host) throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException;
    Boolean logout(User user);
    List<User> getAllUsers();
}
