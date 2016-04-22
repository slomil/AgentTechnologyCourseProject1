package com.ftn.informatika.agents.chat_app.user_app;



import exception.UsernameExistsException;
import model.User;
import org.apache.http.auth.InvalidCredentialsException;

import javax.ejb.Local;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface UserAppUtilLocal {
    User register(String username, String password) throws UsernameExistsException;
    Boolean login(String username, String password) throws InvalidCredentialsException;
    Boolean logout(User logout);
    List<User> getAllUsers();
}
