package com.spotlightchess.game;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class ChessRestApplication extends ResourceConfig {
    public ChessRestApplication() {
    	packages("com.spotlightchess");
    }
}
