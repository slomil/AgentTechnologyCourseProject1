package com.ftn.informatika.agents.chat_app.user_app;


import com.ftn.informatika.agents.exception.AlreadyRegisteredException;
import com.ftn.informatika.agents.exception.InsufficientDataException;
import com.ftn.informatika.agents.exception.InvalidCredentialsException;
import com.ftn.informatika.agents.exception.UsernameExistsException;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;

import javax.ejb.Local;
import javax.jms.JMSException;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Local
public interface UserAppUtilLocal {
    User register(String username, String password)
            throws UsernameExistsException, InsufficientDataException, JMSException;
    User login(String username, String password, Host host)
            throws InsufficientDataException, AlreadyRegisteredException, InvalidCredentialsException, JMSException;
    Boolean logout(User logout) throws JMSException;
    List<User> getAllUsers() throws JMSException;
}
