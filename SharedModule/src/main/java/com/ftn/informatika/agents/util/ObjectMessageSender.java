package com.ftn.informatika.agents.util;

import com.ftn.informatika.agents.jms_messages.JmsMessage;

import javax.jms.*;

/**
 * @author - Srđan Milaković
 */
public abstract class ObjectMessageSender {
    protected void sendObject(JmsMessage object) throws JMSException {
        Connection connection = getConnectionFactory().createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(getQueue());

        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(object);
        producer.send(objectMessage);

        producer.close();
        session.close();
        connection.close();
    }

    protected abstract ConnectionFactory getConnectionFactory();
    protected abstract Queue getQueue();

}
