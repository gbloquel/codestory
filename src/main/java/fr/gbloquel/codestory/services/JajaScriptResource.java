package fr.gbloquel.codestory.services;

import java.util.Collections;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;

import fr.gbloquel.codestory.jajascript.Command;
import fr.gbloquel.codestory.jajascript.Result;

@Path("/jajascript")
public class JajaScriptResource {

	private static final Logger logger =
		    LoggerFactory.getLogger(JajaScriptResource.class);
	
	@POST
	@Path("optimize")
    @Consumes({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
    public Response optimize(List<Command> commands) {
		
        Result result = new Result();
        logger.info("********");

        for (Command command : commands) {
            logger.info(command.toString());
		}

        logger.info("********");

        processResult(commands, result);

        return Response.status(201).entity(result).build();
	}

    private void processResult(final List<Command> commands, final Result bestResult) {

        // Sort the commands.
        orderCommandsByStartTime(commands);

        // Get the First command
        if (commands.size() >= 1) {

            for (Command command : commands) {
                Result result = new Result();
                addCommandInResult(result, command);
                Command possibleCommand = getNextCommandPossible(command, commands);

                if (possibleCommand != null) {
                    addCommandInResult(result, possibleCommand);
                }
                
                if(result.getProfit() > bestResult.getProfit()) {
                    bestResult.update(result);
                }

            }

        }
    }


    private void addCommandInResult(Result result, Command command) {
        result.setProfit(result.getProfit() + command.getPrice());
        result.getFlightPaths().add(command.getFlightID());
    }

    private Command getNextCommandPossible(Command previousCommand, List<Command> commands) {
        Command commandNext = null;
        if (commands.size() >= 1) {
            // Iterate command
            for (Command command : commands) {

                if (previousCommand == command) {
                    continue;
                }

                if ((previousCommand.getStartTime() + previousCommand.getEllaspedTime()) > command.getStartTime()) {
                    continue;
                }

                // command found
                return command;
            }
        }
        return commandNext;
    }

    /**
     * Order the commands by start Time.
     * 
     * @param commands
     *            the command list
     */
    @VisibleForTesting
    void orderCommandsByStartTime(List<Command> commands) {
        Collections.sort(commands);
    }

    public static void main(String[] args) {

        List<Command> commands = Lists.newArrayList(new Command("FLIGHT-A", 0, 2, 3));

        Result result = new Result();
        new JajaScriptResource().processResult(commands, result);

        System.out.println("RESULT=" + result.toString());

    }

}
