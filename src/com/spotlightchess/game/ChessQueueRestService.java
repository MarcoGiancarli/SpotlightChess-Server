package com.spotlightchess.game;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;

import java.util.concurrent.ConcurrentLinkedQueue;

import javax.ws.rs.DELETE;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.FormParam;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import javax.json.Json;
import javax.json.JsonArrayBuilder;

@Path("/queue")
public class ChessQueueRestService {

	private static final Logger logger = Logger.getLogger(ChessQueueRestService.class);
	private static ConcurrentLinkedQueue<Player> chessQueue = new ConcurrentLinkedQueue<Player>();

	@GET
	@Path("/get")
    @Produces(MediaType.APPLICATION_JSON)
	public String getQueue() {
	    Player[] playersInQueue = chessQueue.toArray(new Player[0]);
	    
	    JsonArrayBuilder builder = Json.createArrayBuilder();
	    for(int position=0; position<playersInQueue.length; position++) {
	        builder.add(
	                Json.createObjectBuilder()
	                .add("username", playersInQueue[position].getUsername())
	                .add("position", position)
	        );
	    }
        return builder.build().toString();
	}

	@POST
	@Path("/join")
    @Produces(MediaType.TEXT_PLAIN)
	public Response joinQueue(@QueryParam("username") String username) {
        if(username.length() > 20) {
            return Response.status(422, "username cannot be longer than 20 characters").build();
        } else if(chessQueue.contains(new Player(username))) {
            return Response.status(422, "username already exists in the queue").build();
        } else {
            Player newPlayer = new Player(username);
            chessQueue.add(newPlayer);
	        return Response.ok().build();
        }
	}

	@PUT
	@Path("/poopy")
    @Produces(MediaType.TEXT_PLAIN)
	public String putSomething(@FormParam("request") String request , 
	        @DefaultValue("1") @FormParam("version") int version) {
		if (logger.isDebugEnabled()) {
			logger.debug("Start putSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}

		String response = null;

        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

	                response = "Response from Jersey Restful Webservice : " + request;
                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	response = e.getMessage().toString();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("result: '"+response+"'");
            logger.debug("End putSomething");
        }
        return response;	
	}

	@DELETE
	@Path("/doodoo")
	public void deleteSomething(@FormParam("request") String request ,  @DefaultValue("1") @FormParam("version") int version) {
		
		if (logger.isDebugEnabled()) {
			logger.debug("Start deleteSomething");
			logger.debug("data: '" + request + "'");
			logger.debug("version: '" + version + "'");
		}


        try{			
            switch(version){
	            case 1:
	                if(logger.isDebugEnabled()) logger.debug("in version 1");

                    break;
                default: throw new Exception("Unsupported version: " + version);
            }
        }
        catch(Exception e){
        	e.printStackTrace();
        }
        
        if(logger.isDebugEnabled()){
            logger.debug("End deleteSomething");
        }
	}
}
