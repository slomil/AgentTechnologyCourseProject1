package com.ftn.informatika.agents.chat_app.user_app;



import com.ftn.informatika.agents.exception.UsernameExistsException;
import com.ftn.informatika.agents.model.User;
import org.apache.http.auth.InvalidCredentialsException;

import javax.ejb.Local;

/**
 * @author - Srđan Milaković
 */
@Local
public interface UserAppUtilLocal {
    boolean register(String username, String password) throws UsernameExistsException;
    boolean login(String username, String password) throws InvalidCredentialsException;
    boolean logout(User logout);
    boolean getAllUsers();
}
