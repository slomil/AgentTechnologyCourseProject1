package com.ftn.informatika.agents.chat_app.user_app;

import com.ftn.informatika.agents.chat_app.model.User;
import com.ftn.informatika.agents.chat_app.util.UsernameExistsException;
import org.apache.http.auth.InvalidCredentialsException;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface UserAppUtilRemote {
    User register(String username, String password) throws UsernameExistsException;
    Boolean login(String username, String password) throws InvalidCredentialsException;
    Boolean logout(User logout);
    List<User> getAllUsers();
}
