package com.spotlightchess.game;

import javax.websocket.Session;

public class Player {
    private String username;
    private Session session;
    
    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setSession(Session session) {
        this.session = session;
    }
    
    public Session getSession() {
        return session;
    }
    
    public boolean equals(Object other) {
        return this.toString().toLowerCase().equals(other.toString().toLowerCase());
    }
    
    public String toString() {
        return this.getUsername();
    }
    
    public int hashcode() {
        return username.hashCode();
    }
}
