package fr.gbloquel.codestory.services;

import java.util.List;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fr.gbloquel.codestory.jajascript.Command;

@Path("/jajascript")
public class JajaScriptResource {

	private static final Logger logger =
		    LoggerFactory.getLogger(JajaScriptResource.class);
	
	@POST
	@Path("optimize")
	@Produces(MediaType.APPLICATION_JSON)
	public Response optimize(List<Command> commands) {
		
		for(Command command: commands) {
			logger.info(command.toString());
		}

		return Response.status(201).build();
	}



}
