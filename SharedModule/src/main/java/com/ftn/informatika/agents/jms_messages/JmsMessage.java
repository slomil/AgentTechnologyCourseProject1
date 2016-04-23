package com.ftn.informatika.agents.jms_messages;

import java.io.Serializable;
import java.util.UUID;

/**
 * @author - Srđan Milaković
 */
public class JmsMessage implements Serializable {
    protected UUID uuid;

    public JmsMessage() {
        this(UUID.randomUUID());
    }

    public JmsMessage(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }
}
