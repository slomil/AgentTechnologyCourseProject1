package com.ftn.informatika.agents.chat_app.user_app;

import com.ftn.informatika.agents.chat_app.model.User;
import com.ftn.informatika.agents.chat_app.util.UsernameExistsException;
import org.apache.http.auth.InvalidCredentialsException;

import javax.ejb.Stateless;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class UserAppUtilBean implements UserAppUtilRemote {
    @Override
    public User register(String username, String password) throws UsernameExistsException {
        return null;
    }

    @Override
    public Boolean login(String username, String password) throws InvalidCredentialsException {
        return null;
    }

    @Override
    public Boolean logout(User logout) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
