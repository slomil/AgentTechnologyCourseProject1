package com.ftn.informatika.agents.user_app.service;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;

/**
 * @author - Srđan Milaković
 */
@MessageDriven(
        activationConfig = {
                @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge"),
                @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
                @ActivationConfigProperty(propertyName = "destination", propertyValue = "jms/queue/UserApp")
        }
)
public class UserMDBean implements MessageListener {

    @Resource(mappedName = "java:/ConnectionFactory")
    private QueueConnectionFactory connectionFactory;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                //Object object = ((ObjectMessage) message).getObject();

                Destination from = message.getJMSReplyTo();
                Queue queue = (Queue) from;

                QueueConnection connection = connectionFactory.createQueueConnection();
                QueueSession session = connection.createQueueSession(false, Session.AUTO_ACKNOWLEDGE);
                QueueSender sender = session.createSender(queue);
                sender.send(session.createTextMessage("Hello from MD bean."));
                sender.close();
                connection.close();

            } catch (JMSException e) {
                e.printStackTrace();
            }

        }
        System.out.println(message);
    }
}
