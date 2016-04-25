package com.ftn.informatika.agents.chat_app.user_app;

import com.ftn.informatika.agents.chat_app.util.ServerManagementLocal;
import com.ftn.informatika.agents.exception.AlreadyRegisteredException;
import com.ftn.informatika.agents.exception.InsufficientDataException;
import com.ftn.informatika.agents.exception.InvalidCredentialsException;
import com.ftn.informatika.agents.exception.UsernameExistsException;
import com.ftn.informatika.agents.jms_messages.GetActiveUsersMessage;
import com.ftn.informatika.agents.jms_messages.LoginMessage;
import com.ftn.informatika.agents.jms_messages.LogoutMessage;
import com.ftn.informatika.agents.jms_messages.RegisterMessage;
import com.ftn.informatika.agents.model.Host;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.util.ObjectMessageSender;

import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.Queue;
import java.util.List;

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
    public User register(String username, String password)
            throws UsernameExistsException, InsufficientDataException, JMSException {
        if (serverManagementBean.isMaster()) {
            sendObject(new RegisterMessage(username, password));
            return null;
        }

        return UserAppRequester.register(serverManagementBean.getMasterAddress(), username, password);
    }

    @Override
    public User login(String username, String password, Host host)
            throws InsufficientDataException, AlreadyRegisteredException, InvalidCredentialsException, JMSException {
        if (serverManagementBean.isMaster()) {
            sendObject(new LoginMessage(username, password, host));
            return null;
        }

        return UserAppRequester.login(serverManagementBean.getMasterAddress(), username, password, host);
    }

    @Override
    public Boolean logout(User user) throws JMSException {
        if (serverManagementBean.isMaster()) {
            sendObject(new LogoutMessage(user));
            return null;
        }

        return UserAppRequester.logout(serverManagementBean.getMasterAddress(), user);

    }

    @Override
    public List<User> getAllUsers() throws JMSException {
        if (serverManagementBean.isMaster()) {
            sendObject(new GetActiveUsersMessage());
            return null;
        }

        return UserAppRequester.getAllUsers(serverManagementBean.getMasterAddress());
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
