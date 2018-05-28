package com.spotlightchess.game;

public class Player {
    private String username;
    
    public Player(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
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
    
    public static void main(String[] args) {
        Player p1 = new Player("robert");
        Player p2 = new Player("ROBERT");
        System.out.println(p1.equals(p2));
    }
}
