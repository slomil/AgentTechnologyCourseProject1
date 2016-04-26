package com.ftn.informatika.agents.chat_app.rest;

import com.ftn.informatika.agents.model.Message;
import com.ftn.informatika.agents.model.User;
import com.ftn.informatika.agents.rest_endpoints.MessagesEndpoint;

import javax.ejb.Stateless;

/**
 * @author - Srđan Milaković
 */
@Stateless
public class MessagesREST implements MessagesEndpoint {
    @Override
    public void publish(Message message) {
        User user = message.getTo();
        if (message.getTo() == null) {

        } else {

        }
    }
}
