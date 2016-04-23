package com.ftn.informatika.agents.jms_messages;

/**
 * @author - Srđan Milaković
 */
public class ResponseMessage extends JmsMessage {
    private Object object;
    private boolean success;

    public ResponseMessage(JmsMessage parentMessage) {
        this(parentMessage, null, true);
    }

    public ResponseMessage(JmsMessage parentMessage, Object object) {
        this(parentMessage, object, true);
    }

    public ResponseMessage(JmsMessage parentMessage, Object object, boolean success) {
        super(parentMessage.getUuid());
        this.object = object;
        this.success = success;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
