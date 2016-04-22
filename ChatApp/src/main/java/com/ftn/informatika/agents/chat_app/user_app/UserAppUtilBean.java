package com.ftn.informatika.agents.chat_app.user_app;

import exception.UsernameExistsException;
import model.User;
import org.apache.http.auth.InvalidCredentialsException;

import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.*;
import java.util.List;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class UserAppUtilBean implements UserAppUtilLocal {

    @Resource(mappedName = "java:/ConnectionFactory")
    private QueueConnectionFactory connectionFactory;
    @Resource(mappedName = "java:/jms/queue/UserApp")
    private Queue queue;

    @Override
    public User register(String username, String password) throws UsernameExistsException {
        try {
            QueueConnection connection = connectionFactory.createQueueConnection();
            QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
            connection.start();

            QueueRequestor requestor = new QueueRequestor(session, queue);
            ObjectMessage message = session.createObjectMessage(new User("Username", "Password"));
            TextMessage response = (TextMessage) requestor.request(message);
            System.out.println(response.getText());
            requestor.close();
            connection.stop();
            connection.close();

        } catch (JMSException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public Boolean login(String username, String password) throws InvalidCredentialsException {
        return null;
    }

    @Override
    public Boolean logout(User logout) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }
}
