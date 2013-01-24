package fr.gbloquel.codestory.services;

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
import com.google.common.collect.Maps;

import fr.gbloquel.codestory.jajascript.Command;
import fr.gbloquel.codestory.jajascript.Result;

@Path("/jajascript")
public class JajaScriptResource {

	private static final Logger logger = LoggerFactory
			.getLogger(JajaScriptResource.class);

	private Map<Command, List<Command>> cacheCommand = Maps.newHashMap(); 
	
	@POST
	@Path("optimize")
	@Consumes({ MediaType.APPLICATION_JSON,
			MediaType.APPLICATION_FORM_URLENCODED })
	@Produces(MediaType.APPLICATION_JSON)
	public Response optimize(List<Command> commands) {

		logger.info("********");

		for (Command command : commands) {
			logger.info(command.toString());
		}

		logger.info("********");

		Result result = processResult(commands);

		return Response.status(201).entity(result).build();
	}

	private Result processResult(final List<Command> commands) {

		Result bestResult = new Result();
		
		// Sort the commands by startTime
		orderCommandsByStartTime(commands);

		// Iterate on each command: This command will be the start
		int sizeCommands = commands.size();
		for (int i = 0; i < sizeCommands; i++) {

			Result resultIteration = processSubCommand(commands.subList(i, sizeCommands), bestResult, new Result());

			checkIfBetterResult(bestResult, resultIteration);

		}
		return bestResult;

	}

	/**
	 * Compute the
	 * @param commandRoot
	 * @param commands
	 * @return list of commands where commandRoot have path Possible limited at one level
	 */
	private List<Command> computeListCommandPossible(Command commandRoot, List<Command> commands) {
		
		List<Command> commandsPossible = Lists.newLinkedList();
		
		if(!cacheCommand.containsKey(commandRoot)) {
		
			if (commands.size() >= 1) {
				// Iterate command
				for (Command command : commands) {
	
					if ((commandRoot.getStartTime() + commandRoot
							.getEllaspedTime()) > command.getStartTime()) {
						continue;
					}
	
					// command found
					commandsPossible.add(command);
				}
			}
			cacheCommand.put(commandRoot, commandsPossible);
		} else {
			commandsPossible = cacheCommand.get(commandRoot);
		}
		return commandsPossible;
	}
	
	/**
	 * process on each subCommand
	 * 
	 * @param commands
	 * @param result
	 */
	private Result processSubCommand(List<Command> commands,Result bestResult, Result resultInProgress) {
		
		
		// Check if commands exists again
		if (commands.size() >= 1) {

			// Get the first command from list
			Command startCommand = commands.get(0);

			// Add the command in result
			updateResultFromCommand(resultInProgress, startCommand);

			
			List<Command> commandsToBeTested = computeListCommandPossible(startCommand, suppressCommandUntilNextPossibleCommand(commands, startCommand));
			
			if(!commandsToBeTested.isEmpty()) {
				
				if(commandsToBeTested.size() >= 1) {
					for(Command commandToTest: commandsToBeTested) {
						Result resultTemp = new Result();
						resultTemp.update(resultInProgress);
						
						processSubCommand(suppressCommandUntilPossibleCommand(commands, commandToTest), bestResult, resultTemp);
						
					}
				} else { // No more element => update the result and check if bestResult
					updateResultFromCommand(resultInProgress, commandsToBeTested.get(0));
					checkIfBetterResult(bestResult, resultInProgress);
				}
				
			} else { // If any commands we are in bottow level.
				checkIfBetterResult(bestResult, resultInProgress);
			}

		}
		return bestResult;
	}

	/**
	 * Check if resultinprogress is better than best Result, if yes update.
	 * @param bestResult
	 * @param resultInProgress
	 */
	private void checkIfBetterResult(Result bestResult, Result resultInProgress) {
		if (resultInProgress.getProfit() > bestResult.getProfit()) {
			bestResult.update(resultInProgress);
		}
	}
	
	/**
	 * Return the list after the command specified.
	 * @param commands
	 * @param possibleCommand
	 * @return the list after the element
	 */
	private List<Command> suppressCommandUntilNextPossibleCommand(
			List<Command> commands, Command possibleCommand) {

		int indexPossibleCommand = commands.indexOf(possibleCommand);

		if (commands.size() > indexPossibleCommand) {
			return commands.subList(indexPossibleCommand + 1, commands.size());
		}

		return Lists.newLinkedList();
	}
	
	
	/**
	 * Update the Result with command.
	 * @param result
	 * @param command
	 */
	
	private void updateResultFromCommand(Result result, Command command) {
			result.setProfit(result.getProfit() + command.getPrice());
			result.getFlightPaths().add(command.getFlightID());	
	}
	
	
	/**
	 * Return the list from the element
	 * @param commands
	 * @param possibleCommand
	 * @return list
	 */
	private List<Command> suppressCommandUntilPossibleCommand(
			List<Command> commands, Command possibleCommand) {

		int indexPossibleCommand = commands.indexOf(possibleCommand);

		if (commands.size() > indexPossibleCommand) {
			return commands.subList(indexPossibleCommand, commands.size());
		}

		return Lists.newLinkedList();
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
