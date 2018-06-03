package com.spotlightchess.game;

import java.io.IOException;

import javax.websocket.CloseReason;
import javax.websocket.CloseReason.CloseCodes;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/game/{username}")
public class GameEndPoint {
    
    private Player player = null;
    
    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username) throws IOException {
        if(!username.matches("\\A\\w+{1,20}\\z")) {
            session.close(new CloseReason(
                    CloseCodes.UNEXPECTED_CONDITION, 
                    "Username must be 1-20 aphanumeric characters."));
        } else if(ChessQueueRestService.chessQueue.contains(new Player(username))) {
            session.close(new CloseReason(
                    CloseCodes.UNEXPECTED_CONDITION, 
                    "Username has already been taken."));
        } else {
            session.setMaxIdleTimeout(30000);
            session.getBasicRemote().sendText("Connection established.");
            Player newPlayer = new Player(username);
            newPlayer.setSession(session);
            player = newPlayer;
            ChessQueueRestService.chessQueue.add(newPlayer);
        }
    }
 
    @OnMessage
    public void onMessage(Session session, String message) throws IOException {
        session.getAsyncRemote().sendText(message); // just echo for now
    }
 
    @OnClose
    public void onClose(Session session) throws IOException {
        if(player != null) {
            ChessQueueRestService.chessQueue.remove(player);
            player = null;
        }
    }
 
    @OnError
    public void onError(Session session, Throwable throwable) {
        throwable.printStackTrace();
    }
}
