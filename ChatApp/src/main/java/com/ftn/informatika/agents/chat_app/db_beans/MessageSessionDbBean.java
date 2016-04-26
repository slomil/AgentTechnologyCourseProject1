package com.ftn.informatika.agents.chat_app.db_beans;

import javax.ejb.*;
import javax.websocket.Session;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class MessageSessionDbBean implements MessageSessionDbLocal {
    private Map<UUID, Session> messageObjects = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void addMessage(UUID uuid, Session object) {
        messageObjects.put(uuid, object);
    }

    @Lock(LockType.READ)
    @Override
    public Session getMessage(UUID uuid) {
        return messageObjects.get(uuid);
    }

    @Lock(LockType.WRITE)
    @Override
    public Session removeMessage(UUID uuid) {
        return messageObjects.remove(uuid);
    }
}
