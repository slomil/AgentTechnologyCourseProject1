package com.ftn.informatika.agents.chat_app.users;

import com.ftn.informatika.agents.model.User;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface UsersDbLocal {
    void setUsers(List<User> users);
    void addUser(User user);
    void removeUser(User user);
    List<User> getUsers();
}
