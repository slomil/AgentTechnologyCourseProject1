package com.ftn.informatika.agents.chat_app.db_beans;

import javax.ejb.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author - Srđan Milaković
 */
@ConcurrencyManagement(ConcurrencyManagementType.CONTAINER)
@Singleton
public class MessageObjectsDbBean implements MessageObjectsDbLocal {
    private Map<UUID, Object> messageObjects = new HashMap<>();

    @Lock(LockType.WRITE)
    @Override
    public void addMessage(UUID uuid, Object object) {
        messageObjects.put(uuid, object);
    }

    @Lock(LockType.READ)
    @Override
    public Object getMessage(UUID uuid) {
        return messageObjects.get(uuid);
    }

    @Lock(LockType.WRITE)
    @Override
    public Object removeMessage(UUID uuid) {
        return messageObjects.remove(uuid);
    }
}
