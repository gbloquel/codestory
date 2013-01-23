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

	private static final Logger logger = LoggerFactory
			.getLogger(JajaScriptResource.class);

	@POST
	@Path("optimize")
	@Consumes({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
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

	
	/**
	 * 
	 * @param commands
	 * @param bestResult
	 */
	private void processResult(final List<Command> commands,
			final Result bestResult) {

		// Sort the commands.
		orderCommandsByStartTime(commands);

		
		// Iterate on each command: This command will be the start
		int sizeCommands = commands.size();
		for(int i = 0; i < sizeCommands; i++) {
		
			Result result = new Result();
			iterateOnSubCommands(commands.subList(i, sizeCommands), result);
			
			// The result is better ?
			if (result.getProfit() > bestResult.getProfit()) {
				bestResult.update(result);
			}
			
		}
	}

	/**
	 * Iterate on each subCommand
	 * @param commands
	 * @param result
	 */
	private void iterateOnSubCommands(List<Command> commands, Result result) {
		
		// Check if commands exists again 
		if (commands.size() >= 1) {

			// Get the first command from list
			Command startCommand = commands.get(0);

			// Add the command in result
			addCommandInResult(result, startCommand);
			
			// Find if it exists a command possible
			Command possibleCommand = getNextCommandPossible(startCommand,commands);
			
			// If yes recursive call
			if (possibleCommand != null) {
				addCommandInResult(result, possibleCommand);
				iterateOnSubCommands(
						suppressCommandUntilPossibleComamnd(commands,
								possibleCommand), result); // CDR command
			}
		}
		
	}
	
	
	private List<Command> suppressCommandUntilPossibleComamnd(List<Command> commands, Command possibleCommand) {
		
		
		int indexPossibleCommand  = commands.indexOf(possibleCommand);
		
		if(commands.size() > indexPossibleCommand) {
			return commands.subList(indexPossibleCommand, commands.size());
		}
		
		return Lists.newArrayList();
	}
	
	
	private void addCommandInResult(Result result, Command command) {
		if (!result.getFlightPaths().contains(command.getFlightID())) {

			result.setProfit(result.getProfit() + command.getPrice());
			result.getFlightPaths().add(command.getFlightID());
		}
	}

	private Command getNextCommandPossible(Command previousCommand,
			List<Command> commands) {
		Command commandNext = null;
		if (commands.size() >= 1) {
			// Iterate command
			for (Command command : commands) {

				if (previousCommand == command) {
					continue;
				}

				if ((previousCommand.getStartTime() + previousCommand
						.getEllaspedTime()) > command.getStartTime()) {
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

}
