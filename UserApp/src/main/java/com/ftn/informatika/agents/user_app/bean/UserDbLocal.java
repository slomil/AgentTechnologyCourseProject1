package com.ftn.informatika.agents.user_app.bean;

import com.ftn.informatika.agents.exception.*;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;


import javax.jws.soap.SOAPBinding;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
public interface UserDbLocal {
    User register(String username, String password) throws UsernameExistsException, InsufficientDataException;
    User login(String username, String password, Host host) throws InvalidCredentialsException, InsufficientDataException, AlreadyRegisteredException;
    void logout(User user) throws UserInactiveException;
    List<User> getAllUsers();
}
