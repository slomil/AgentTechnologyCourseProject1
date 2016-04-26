package com.ftn.informatika.agents.chat_app.db_beans;

import com.ftn.informatika.agents.model.User;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface ActiveUsersDbLocal {
    void setUsers(List<User> users);
    void addUser(User user);
    void removeUser(User user);
    List<User> getUsers();
}