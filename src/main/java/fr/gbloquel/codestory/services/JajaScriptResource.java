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
import com.google.common.collect.Maps;

import fr.gbloquel.codestory.jajascript.Command;
import fr.gbloquel.codestory.jajascript.PlanningCommand;
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

		// Map startTime, Profit contains best Hour 
		Map<Integer, Long> bestHourProfits = Maps.newHashMap();
		
		// Map startTime
		Map<Integer, PlanningCommand> bestPlanningCommands = Maps.newHashMap();
		
		/**
		 * Iterate on each command
		 */
		for(int i = commands.size()-1; i >= 0; i--) {
			Command currentCommand = commands.get(i);
			
			// Get the List depart
			int bestNextDepart = findNextBestDepartPossible(currentCommand.getEndTime(), bestHourProfits);
			PlanningCommand bestPlanningCommand = bestPlanningCommands.get(bestNextDepart);
			
			// If no next depart possible.
			if(bestPlanningCommand == null) {
				bestHourProfits.put(currentCommand.getStartTime(), currentCommand.getPrice());
				bestPlanningCommands.put(currentCommand.getStartTime(), new PlanningCommand(currentCommand));
			} 
			// If first Time for this startTime or this current command is better than the bestPlanning  we update the bestPlanning
			else if(bestHourProfits.get(currentCommand.getStartTime()) == null || bestHourProfits.get(currentCommand.getStartTime()) <  (currentCommand.getPrice() + bestPlanningCommand.getProfitTotal())) {
					bestPlanningCommands.put(currentCommand.getStartTime(), new PlanningCommand(currentCommand, bestPlanningCommand));
					bestHourProfits.put(currentCommand.getStartTime(), currentCommand.getPrice() + bestPlanningCommand.getProfitTotal());
			}
			
			// Update the bestResult if this solution is better.
			if(bestResult.getProfit() < bestPlanningCommands.get(currentCommand.getStartTime()).getProfitTotal()) {
				bestResult = bestPlanningCommands.get(currentCommand.getStartTime()).computeResult(new Result(), bestPlanningCommands.get(currentCommand.getStartTime()));
			}

		}
		
		
		return bestResult;
	}
	
	/**
	 * Find the best next startTime with the best profit.
	 * @param endTimePreviousCommand
	 * @param bestHourProfits
	 * @return the best next start time
	 */
	private int findNextBestDepartPossible(long endTimePreviousCommand, Map<Integer,Long> bestHourProfits) {
		
		int bestStartTime = -1;
		long bestProfit = -1;
		
		for(Integer startTime: bestHourProfits.keySet()) {
			
			if(endTimePreviousCommand <= startTime && bestHourProfits.get(startTime) > bestProfit) {
				bestProfit = bestHourProfits.get(startTime);
				bestStartTime = startTime; 
			}
		}
		return bestStartTime;
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
