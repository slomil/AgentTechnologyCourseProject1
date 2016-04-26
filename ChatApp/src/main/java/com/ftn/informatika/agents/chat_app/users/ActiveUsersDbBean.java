package com.ftn.informatika.agents.chat_app.users;

import com.ftn.informatika.agents.model.User;

import javax.ejb.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author - Srđan Milaković
 */
//@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class ActiveUsersDbBean implements ActiveUsersDbLocal {

    private Map<String, User> users = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void setUsers(List<User> users) {
        this.users = new HashMap<>();
        users.forEach(u -> this.users.put(u.getUsername(), u));
    }

    @Lock(LockType.WRITE)
    @Override
    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    @Lock(LockType.WRITE)
    @Override
    public void removeUser(User user) {
        users.remove(user.getUsername());
    }

    @Lock(LockType.READ)
    @Override
    public List<User> getUsers() {
        return new ArrayList<>(users.values());
    }
}
