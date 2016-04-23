package com.ftn.informatika.agents.user_app.service;

import com.ftn.informatika.agents.exception.*;
import com.ftn.informatika.agents.jms_messages.*;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.user_app.bean.UserDbLocal;
import com.ftn.informatika.agents.util.ObjectMessageSender;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/UserAppQueue")
        }
)
public class UserSenderMDBean extends ObjectMessageSender implements MessageListener {

    @EJB
    private UserDbLocal userDbBean;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:jboss/jms/queue/ChatApp")
    private Queue queue;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                Object object = ((ObjectMessage) message).getObject();

                // TODO: Refactor (visitor pattern)
                try {
                    if (object instanceof LoginMessage) {
                        LoginMessage msg = (LoginMessage) object;
                        User user = userDbBean.login(msg.getUsername(), msg.getPassword(), msg.getHost());
                        sendObject(new ResponseMessage(msg, user));
                    } else if (object instanceof RegisterMessage) {
                        RegisterMessage msg = (RegisterMessage) object;
                        User user = userDbBean.register(msg.getUsername(), msg.getPassword());
                        sendObject(new ResponseMessage(msg, user));
                    } else if (object instanceof LogoutMessage) {
                        LogoutMessage msg = (LogoutMessage) object;
                        userDbBean.logout(msg.getUser());
                        sendObject(new ResponseMessage(msg));
                    } else if (object instanceof GetActiveUsersMessage) {
                        GetActiveUsersMessage msg = (GetActiveUsersMessage) object;
                        List<User> users = userDbBean.getAllUsers();
                        sendObject(new ResponseMessage(msg, users));
                    } else {
                        throw new UnsupportedMessageException();
                    }
                } catch (InvalidCredentialsException | InsufficientDataException | AlreadyRegisteredException
                        | UsernameExistsException | UserInactiveException | UnsupportedMessageException e) {
                    sendObject(new ResponseMessage((JmsMessage) object, e, false));
                }
            } catch (JMSException e) {
                e.printStackTrace();
            }
        }
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
