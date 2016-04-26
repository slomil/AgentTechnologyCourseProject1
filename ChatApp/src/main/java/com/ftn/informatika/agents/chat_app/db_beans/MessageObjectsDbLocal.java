package com.ftn.informatika.agents.chat_app.db_beans;

import java.util.UUID;

/**
 * @author - Srđan Milaković
 */
public interface MessageObjectsDbLocal {
    void addMessage(UUID uuid, Object object);
    Object getMessage(UUID uuid);
    Object removeMessage(UUID uuid);
}