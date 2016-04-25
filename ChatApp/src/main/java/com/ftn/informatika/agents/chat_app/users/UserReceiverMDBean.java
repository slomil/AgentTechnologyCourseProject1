package com.ftn.informatika.agents.chat_app.users;

import com.ftn.informatika.agents.exception.UnsupportedMessageException;
import com.ftn.informatika.agents.jms_messages.*;
import com.ftn.informatika.agents.model.User;
import com.google.gson.Gson;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.EJB;
import javax.ejb.MessageDriven;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import java.io.IOException;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "queue/ChatAppQueue")
        }
)
public class UserReceiverMDBean implements MessageListener {

    @EJB
    private MessageObjectsDbLocal messageObjectsDbBean;
    @EJB
    private UsersDbLocal usersDbBean;

    @Override
    public void onMessage(Message message) {
        if (!(message instanceof ObjectMessage)) {
            return;
        }

        try {
            Object object = ((ObjectMessage) message).getObject();
            if (!(object instanceof JmsMessage)) {
                throw new UnsupportedMessageException();
            }

            Object messageObject = messageObjectsDbBean.getMessage(((JmsMessage) object).getUuid());
            if (object instanceof GetActiveUsersMessage) {
                List<User> users = ((GetActiveUsersMessage) object).getResponse();
                usersDbBean.setUsers(users);
            } else if (object instanceof LoginMessage) {

            } else if (object instanceof RegisterMessage) {

            } else if (object instanceof LogoutMessage) {

            } else {
                throw new UnsupportedMessageException();
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (UnsupportedMessageException e) {
            System.err.println("Unsupported message.");
        } /*catch (IOException e) {
            System.err.println("Websocket error! " + e.getMessage());
        }*/

    }

    private String toJson(Object object) {
        if (object == null) {
            return null;
        }
        return new Gson().toJson(object);
    }
}
