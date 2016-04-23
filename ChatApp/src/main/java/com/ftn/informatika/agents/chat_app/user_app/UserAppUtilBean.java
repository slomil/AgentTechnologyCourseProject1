package com.ftn.informatika.agents.chat_app.user_app;

import com.ftn.informatika.agents.jms_messages.RegisterMessage;
import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.exception.UsernameExistsException;
import com.ftn.informatika.agents.model.User;
import org.apache.http.auth.InvalidCredentialsException;
import com.ftn.informatika.agents.util.ObjectMessageSender;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.*;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class UserAppUtilBean extends ObjectMessageSender implements UserAppUtilLocal {

    @EJB
    private ServerManagementLocal serverManagementBean;

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:jboss/jms/queue/UserApp")
    private Queue queue;

    @Override
    public boolean register(String username, String password) throws UsernameExistsException {
        if (serverManagementBean.isMaster()) {
            try {
                sendObject(connectionFactory, queue, new RegisterMessage(username, password));
            } catch (JMSException e) {
                e.printStackTrace();
                return false;
            }
        } else {
            // TODO: RestEasyClient
        }

        return true;
    }

    @Override
    public boolean login(String username, String password) throws InvalidCredentialsException {
        return false;
    }

    @Override
    public boolean logout(User logout) {
        return false;
    }

    @Override
    public boolean getAllUsers() {
        return false;
    }
}
