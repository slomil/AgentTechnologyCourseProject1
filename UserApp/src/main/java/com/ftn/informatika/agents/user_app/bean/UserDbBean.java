package com.ftn.informatika.agents.user_app.bean;

import exception.AlreadyRegisteredException;
import exception.InsufficientDataException;
import exception.InvalidCredentialsException;
import exception.UsernameExistsException;
import model.Host;
import model.User;

import javax.ejb.*;
import java.util.*;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class UserDbBean implements UserDbLocal {

    private Map<String, User> users = new HashMap<>();
    private Map<String, User> activeUsers = new HashMap<>();

    @Override
    @Lock(LockType.WRITE)
    public User register(String username, String password) throws UsernameExistsException, InsufficientDataException {
        if (username == null || password == null) {
            throw new InsufficientDataException();
        }

        if (users.get(username) != null) {
            throw new UsernameExistsException();
        }

        User user = new User(username, password);
        users.put(username, user);

        return user;
    }

    @Override
    @Lock(LockType.WRITE)
    public Boolean login(String username, String password, Host host)
            throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException {
        if (username == null || password == null) {
            throw new InsufficientDataException();
        }

        User user = users.get(username);
        if (!password.equals(user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        if (activeUsers.get(username) != null) {
            throw new AlreadyRegisteredException();
        }

        return true;
    }

    @Override
    @Lock(LockType.WRITE)
    public Boolean logout(User user) {
        return activeUsers.remove(user.getUsername()) != null;
    }

    @Override
    @Lock(LockType.READ)
    public List<User> getAllUsers() {
        List<User> returnUsers = new ArrayList<>();
        activeUsers.values().forEach(u -> returnUsers.add(new User(u.getUsername(), null, u.getHost())));
        return returnUsers;
    }

}
