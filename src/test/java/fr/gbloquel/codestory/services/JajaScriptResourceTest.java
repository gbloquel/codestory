package fr.gbloquel.codestory.services;

import static org.fest.assertions.api.Assertions.assertThat;

import java.util.List;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.Lists;

import fr.gbloquel.codestory.jajascript.Command;

public class JajaScriptResourceTest {

	private JajaScriptResource jajaScriptResource;

	@Before
	public void setUp() {
		jajaScriptResource = new JajaScriptResource();
	}

	@Test
	public void testOrderCommandsByStartTime() {

		Command flightA = new Command("FLIGHT-A", 10, 2, 3);
		Command flightB = new Command("FLIGHT-B", 0, 4, 6);
		Command flightC = new Command("FLIGHT-C", 1, 7, 6);

		List<Command> commands = Lists.newArrayList(flightA, flightB, flightC);

		jajaScriptResource.orderCommandsByStartTime(commands);

		assertThat(commands).containsSequence(flightB, flightC, flightA);

	}

	@Test
	public void testZeroCommand() {

		List<Command> commands = Lists.newArrayList();

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=0, path=[]}");

	}

	@Test
	public void testOneCommand() {
		Command flightA = new Command("FLIGHT-A", 10, 2, 3);

		List<Command> commands = Lists.newArrayList(flightA);

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=3, path=[FLIGHT-A]}");

	}

	@Test
	public void testTwoCommands() {
		Command flightA = new Command("FLIGHT-A", 10, 2, 3);
		Command flightB = new Command("FLIGHT-B", 0, 4, 6);

		List<Command> commands = Lists.newArrayList(flightA, flightB);

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=9, path=[FLIGHT-B, FLIGHT-A]}");

	}

	@Test
	public void testTwoCommandsNotEnchainable() {
		Command flightA = new Command("FLIGHT-A", 1, 2, 3);
		Command flightB = new Command("FLIGHT-B", 0, 4, 6);

		List<Command> commands = Lists.newArrayList(flightA, flightB);

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=6, path=[FLIGHT-B]}");

	}

	@Test
	public void testTwoCommandsNotEnchainableButResultIsLastAfterSort() {
		Command flightA = new Command("FLIGHT-A", 1, 8, 30);
		Command flightB = new Command("FLIGHT-B", 0, 4, 6);

		List<Command> commands = Lists.newArrayList(flightA, flightB);

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=30, path=[FLIGHT-A]}");

	}

	@Test
	public void testForCommands() {
		Command flightA = new Command("MONAD42", 0, 5, 10);
		Command flightB = new Command("META18", 3, 7, 14);
		Command flightC = new Command("LEGACY01", 5, 9, 8);
		Command flightD = new Command("YAGNI17", 5, 9, 7);

		List<Command> commands = Lists.newArrayList(flightA, flightB, flightC,
				flightD);

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=18, path=[MONAD42, LEGACY01]}");

	}

	@Test
	public void testTreeCommands() {
		Command command1 = new Command("AF1", 0, 1, 2);
		Command command2 = new Command("AF2", 4, 1, 4);
		Command command3 = new Command("AF3", 2, 1, 6);

		List<Command> commands = Lists.newArrayList(command1, command2,
				command3);

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=12, path=[AF1, AF3, AF2]}");

	}

	@Test
	public void testFourCommands() {
		Command command1 = new Command("AF514", 0, 5, 10);
		Command command2 = new Command("CO5", 3, 7, 14);
		Command command3 = new Command("AF515", 5, 9, 7);
		Command command4 = new Command("BA01", 6, 9, 8);

		List<Command> commands = Lists.newArrayList(command1, command2, command3, command4);

		Response response = jajaScriptResource.optimize(commands);

		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=18, path=[AF514, BA01]}");

	}

}
