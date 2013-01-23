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

        assertThat(response.getEntity().toString()).isEqualTo("Result{gain=0, path=[]}");

    }

    
    @Test
    public void testOneCommand() {
        Command flightA = new Command("FLIGHT-A", 10, 2, 3);

        List<Command> commands = Lists.newArrayList(flightA);

        Response response = jajaScriptResource.optimize(commands);

        assertThat(response.getEntity().toString()).isEqualTo("Result{gain=3, path=[FLIGHT-A]}");

    }

    
    @Test
    public void testTwoCommands() {
        Command flightA = new Command("FLIGHT-A", 10, 2, 3);
        Command flightB = new Command("FLIGHT-B", 0, 4, 6);

        List<Command> commands = Lists.newArrayList(flightA, flightB);

        Response response = jajaScriptResource.optimize(commands);

        assertThat(response.getEntity().toString()).isEqualTo("Result{gain=9, path=[FLIGHT-B, FLIGHT-A]}");

    }

    @Test
    public void testTwoCommandsNotEnchainable() {
        Command flightA = new Command("FLIGHT-A", 1, 2, 3);
        Command flightB = new Command("FLIGHT-B", 0, 4, 6);

        List<Command> commands = Lists.newArrayList(flightA, flightB);

        Response response = jajaScriptResource.optimize(commands);

        assertThat(response.getEntity().toString()).isEqualTo("Result{gain=6, path=[FLIGHT-B]}");

    }
    
    @Test
    public void testTwoCommandsNotEnchainableButResultIsLastAfterSort() {
        Command flightA = new Command("FLIGHT-A", 1, 8, 30);
        Command flightB = new Command("FLIGHT-B", 0, 4, 6);

        List<Command> commands = Lists.newArrayList(flightA, flightB);

        Response response = jajaScriptResource.optimize(commands);

        assertThat(response.getEntity().toString()).isEqualTo("Result{gain=30, path=[FLIGHT-A]}");

    }

    @Test
    public void testForCommands() {
        Command flightA = new Command("MONAD42", 0, 5, 10);
        Command flightB = new Command("META18", 3, 7, 14);
        Command flightC = new Command("LEGACY01", 5, 9, 8);
        Command flightD = new Command("YAGNI17", 5, 9, 7);

        List<Command> commands = Lists.newArrayList(flightA, flightB, flightC, flightD);

        Response response = jajaScriptResource.optimize(commands);

        assertThat(response.getEntity().toString()).isEqualTo("Result{gain=18, path=[MONAD42, LEGACY01]}");

    }


}
