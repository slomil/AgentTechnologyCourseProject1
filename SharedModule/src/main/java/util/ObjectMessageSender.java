package util;

import JmsMessages.JmsMessage;

import javax.jms.*;

/**
 * @author - Srđan Milaković
 */
public class ObjectMessageSender {
    protected void sendObject(ConnectionFactory connectionFactory, Queue queue, JmsMessage object) throws JMSException {
        Connection connection = connectionFactory.createConnection();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        MessageProducer producer = session.createProducer(queue);


        ObjectMessage objectMessage = session.createObjectMessage();
        objectMessage.setObject(object);
        producer.send(objectMessage);

        producer.close();
        session.close();
        connection.close();
    }
}
