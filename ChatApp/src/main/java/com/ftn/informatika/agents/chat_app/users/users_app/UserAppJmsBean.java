package com.ftn.informatika.agents.chat_app.users.users_app;

import com.ftn.informatika.agents.exception.AlreadyRegisteredException;
import com.ftn.informatika.agents.exception.InsufficientDataException;
import com.ftn.informatika.agents.exception.InvalidCredentialsException;
import com.ftn.informatika.agents.exception.UsernameExistsException;
import com.ftn.informatika.agents.jms_messages.*;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.util.ObjectMessageSender;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class UserAppJmsBean extends ObjectMessageSender implements UserAppJmsLocal {

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:jboss/jms/queue/UserApp")
    private Queue queue;

    @Override
    public JmsMessage register(String username, String password)
            throws UsernameExistsException, InsufficientDataException, JMSException {
        JmsMessage message = new RegisterMessage(username, password);
        sendObject(message);
        return message;
    }

    @Override
    public JmsMessage login(String username, String password, Host host)
            throws InsufficientDataException, AlreadyRegisteredException, InvalidCredentialsException, JMSException {
        JmsMessage message = new LoginMessage(username, password, host);
        sendObject(message);
        return message;
    }

    @Override
    public JmsMessage logout(User user) throws JMSException {
        JmsMessage message = new LogoutMessage(user);
        sendObject(message);
        return message;
    }

    @Override
    public JmsMessage getAllUsers() throws JMSException {
        JmsMessage message = new GetActiveUsersMessage();
        sendObject(message);
        return message;
    }

    @Override
    protected ConnectionFactory getConnectionFactory() {
        return connectionFactory;
    }

    @Override
    protected Queue getQueue() {
        return queue;
    }
}
