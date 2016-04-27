package com.ftn.informatika.agents.chat_app.jms_user_queue;

import com.ftn.informatika.agents.chat_app.db_beans.ActiveUsersDbLocal;
import com.ftn.informatika.agents.chat_app.db_beans.HostsDbLocal;
import com.ftn.informatika.agents.chat_app.db_beans.MessageSessionDbLocal;
import com.ftn.informatika.agents.chat_app.db_beans.UserSessionDbLocal;
import com.ftn.informatika.agents.chat_app.requesters.ActiveUsersManagementRequester;
import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.chat_app.web_client.WebsocketPacket;
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
import javax.websocket.Session;
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
    private MessageSessionDbLocal messageObjectsDbBean;
    @EJB
    private ActiveUsersDbLocal usersDbBean;
    @EJB
    private HostsDbLocal hostsDbBean;
    @EJB
    private ServerManagementLocal serverManagementBean;
    @EJB
    private UserSessionDbLocal userSessionDbBean;

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

            Session session = messageObjectsDbBean.getMessage(((JmsMessage) object).getUuid());
            if (object instanceof GetActiveUsersMessage) {
                List<User> users = ((GetActiveUsersMessage) object).getResponse();
                usersDbBean.setUsers(users);
            } else if (object instanceof LoginMessage) {
                LoginMessage msg = (LoginMessage) object;
                hostsDbBean.getHosts().forEach(h -> ActiveUsersManagementRequester.addUser(h.getAddress(), msg.getResponse()));

                WebsocketPacket packet = new WebsocketPacket(WebsocketPacket.LOGIN, msg.getResponse(), true);
                session.getBasicRemote().sendText(toJson(packet));

                if (!userSessionDbBean.add(new UserSessionDbLocal.UserSession(session, msg.getResponse()))) {
                    throw new Exception("Server error, can not add user for specified session.");
                }
            } else if (object instanceof RegisterMessage) {
                RegisterMessage msg = (RegisterMessage) object;
                WebsocketPacket packet = new WebsocketPacket(WebsocketPacket.REGISTER, msg.getResponse(), true);
                session.getBasicRemote().sendText(toJson(packet));
            } else if (object instanceof LogoutMessage) {
                LogoutMessage msg = (LogoutMessage) object;
                hostsDbBean.getHosts().forEach(h -> ActiveUsersManagementRequester.removeUser(h.getAddress(), msg.getResponse()));
                userSessionDbBean.remove(msg.getResponse());
                WebsocketPacket packet = new WebsocketPacket(WebsocketPacket.LOGOUT, msg.getResponse(), true);
                session.getBasicRemote().sendText(toJson(packet));
            } else {
                throw new UnsupportedMessageException();
            }

        } catch (JMSException e) {
            e.printStackTrace();
        } catch (UnsupportedMessageException e) {
            System.err.println("Unsupported message.");
        } catch (IOException e) {
            System.err.println("Socket exception: " + e.getClass().getSimpleName()  + " \nMessage" + e.getMessage());
        } catch (Exception e) {
            System.err.println("Exception: " + e.getClass().getSimpleName() + " \nMessage" + e.getMessage());
        }

    }

    private String toJson(Object object) {
        if (object == null) {
            return null;
        }
        return new Gson().toJson(object);
    }
}
