package com.ftn.informatika.agents.chat_app.util;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

/**
 * @author - Srđan Milaković
 */
@ApplicationPath("/api")
public class ApplicationConfig extends Application {
    public static final String CHAT_APP_URL = "http://%s/chat_app/api/";
}


