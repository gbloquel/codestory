package fr.gbloquel.codestory.jajascript;


public class PlanningCommand {

	
	private Command command;
	
	private PlanningCommand nextPlanning = null;
	
	private long profitTotal;
	
	/**
	 * Constructor
	 * @param command
	 */
	public PlanningCommand(Command command) {
		this.command = command;
		profitTotal = command.getPrice();
	}
	
	
	public PlanningCommand(Command currentCommand, PlanningCommand nextPlanning) {
		this(currentCommand);
		this.nextPlanning = nextPlanning;
		profitTotal = currentCommand.getPrice() + nextPlanning.getProfitTotal();
	}
	
	/**
	 * Compute the Result
	 * @param result
	 * @param planningCommand
	 * @return the result
	 */
	public Result computeResult(Result result, PlanningCommand planningCommand) {
	
		result.getFlightPaths().add(planningCommand.command.getFlightID());
		result.setProfit(profitTotal);
		
		
		if(planningCommand.nextPlanning != null) {
			computeResult(result, planningCommand.nextPlanning);
		}
		
		return result;
	}
	
	public long getProfitTotal() {
		return profitTotal;
	}
}
