package fr.gbloquel.codestory.services;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.gbloquel.codestory.CodeStory;



@Path("/enonce")
public class EnonceResource {

	private static final Logger logger =
		    LoggerFactory.getLogger(EnonceResource.class);
	
	
	@POST
	@Path("{id}")
	@Produces("text/plain")
	public Response createStatement(@PathParam("id") String id, String content) {
		logger.info("content:" + content);
		return Response.status(201).entity("").build();
	}
}
