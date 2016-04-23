package com.ftn.informatika.agents.user_app.service;

import JmsMessages.JmsMessage;
import util.ObjectMessageSender;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.jms.*;
import java.io.Serializable;

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

    @Resource(mappedName = "java:/ConnectionFactory")
    private ConnectionFactory connectionFactory;
    @Resource(mappedName = "java:jboss/jms/queue/ChatApp")
    private Queue queue;

    @Override
    public void onMessage(Message message) {
        if (message instanceof ObjectMessage) {
            try {
                Object object = ((ObjectMessage) message).getObject();
                sendObject(connectionFactory, queue, new JmsMessage());

            } catch (JMSException e) {
                e.printStackTrace();
            }

        }

        System.out.println(message);
    }
}
