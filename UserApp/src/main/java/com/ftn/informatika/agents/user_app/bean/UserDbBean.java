package com.ftn.informatika.agents.user_app.bean;

import com.ftn.informatika.agents.exception.*;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;

import javax.annotation.PostConstruct;
import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class UserDbBean implements UserDbLocal {

    private Map<String, User> users = new HashMap<>();
    private Map<String, User> activeUsers = new HashMap<>();

    @PostConstruct
    public void init() {
        users.put("admin", new User("admin", "admin"));
        for (char c = 'A'; c <= 'Z'; c++) {
            User user = new User("user" + c, "user");
            users.put(user.getUsername(), user);
        }
    }

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
    public User login(String username, String password, Host host)
            throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException {
        if (username == null || password == null) {
            throw new InsufficientDataException();
        }

        User user = users.get(username);
        if (user == null || !password.equals(user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        if (activeUsers.get(username) != null) {
            throw new AlreadyRegisteredException();
        }

        user.setHost(host);
        activeUsers.put(user.getUsername(), user);

        return user;
    }

    @Override
    @Lock(LockType.WRITE)
    public void logout(User user) throws UserInactiveException {
        if (activeUsers.remove(user.getUsername()) == null) {
            throw new UserInactiveException();
        }
    }

    @Override
    @Lock(LockType.READ)
    public List<User> getAllUsers() {
        return new ArrayList<>(activeUsers.values());
    }

}
