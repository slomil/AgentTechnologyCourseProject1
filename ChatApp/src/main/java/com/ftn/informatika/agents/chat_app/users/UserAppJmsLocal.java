package com.ftn.informatika.agents.chat_app.users;

import com.ftn.informatika.agents.exception.AlreadyRegisteredException;
import com.ftn.informatika.agents.exception.InsufficientDataException;
import com.ftn.informatika.agents.exception.InvalidCredentialsException;
import com.ftn.informatika.agents.exception.UsernameExistsException;
import com.ftn.informatika.agents.jms_messages.JmsMessage;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;

import javax.jms.JMSException;

/**
 * @author - Srđan Milaković
 */
public interface UserAppJmsLocal {
    JmsMessage register(String username, String password)
            throws UsernameExistsException, InsufficientDataException, JMSException;

    JmsMessage login(String username, String password, Host host)
            throws InsufficientDataException, AlreadyRegisteredException, InvalidCredentialsException, JMSException;

    JmsMessage logout(User user) throws JMSException;

    JmsMessage getAllUsers() throws JMSException;
}
