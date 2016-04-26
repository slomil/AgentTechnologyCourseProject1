package com.ftn.informatika.agents.chat_app.db_beans;

import javax.websocket.Session;
import java.util.UUID;

/**
 * @author - Srđan Milaković
 */
public interface MessageSessionDbLocal {
    void addMessage(UUID uuid, Session object);
    Session getMessage(UUID uuid);
    Session removeMessage(UUID uuid);
}
