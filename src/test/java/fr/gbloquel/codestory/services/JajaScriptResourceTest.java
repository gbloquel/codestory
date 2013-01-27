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
	
	
    @Test
    public void compute10Commands() {
    
    final List<Command> commands = Lists.newArrayList(
            new Command("voiceless-regime-17", 0, 4, 13),
            new Command("brainy-ufo-15", 1, 2, 1),
            new Command("mushy-landscape-94", 2, 6, 1),
            new Command("calm-keystroke-35", 4, 5, 7),
            new Command("proud-thunderstorm-91", 5, 2, 2),
            new Command("misty-puzzle-15", 5, 4, 14),
            new Command("wild-tugboat-98", 6, 2, 5),
            new Command("grumpy-cane-60", 7, 6, 6),
            new Command("dull-summertime-36", 9, 5, 4),
            new Command("soft-beige-12", 10, 2, 25)
    );
    
    Response response = jajaScriptResource.optimize(commands);
    assertThat(response.getEntity().toString()).isEqualTo("Result{gain=52, path=[voiceless-regime-17, misty-puzzle-15, soft-beige-12]}");
    													
    
    }
	
	
	@Test
	public void testLevel33() {
		Command command1 = new Command("energetic-collarbone-34", 1, 2, 28); 
		Command command2 = new Command("weary-someone-49", 1, 4, 14); 
		Command command3 = new Command("chubby-nitroglycerine-34", 2, 9, 2); 
		Command command4 = new Command("nice-kneecap-6", 2, 9, 12); 
		Command command5 = new Command("unusual-photographer-44", 4, 15, 3); 
		Command command6 = new Command("faint-ringworm-14", 8, 10, 9); 
		Command command7 = new Command("tired-bassist-73", 8, 1, 12); 
		Command command8 = new Command("combative-mischief-9", 6, 7, 2); 
		Command command9 = new Command("uninterested-toothbrush-46", 8, 4, 15); 
		Command command10 = new Command("crazy-newsman-66", 5, 1, 5); 
		Command command11 = new Command("selfish-marquee-45", 12, 5, 30); 
		Command command12 = new Command("tall-text-64", 13, 3, 6); 
		Command command13 = new Command("cooing-swordsman-58", 13, 3, 7); 
		Command command14 = new Command("skinny-chickenpox-23", 14, 3, 6); 
		Command command15 = new Command("adorable-restaurant-11", 13, 3, 7); 
		Command command16 = new Command("flat-youngster-77", 15, 9, 7); 
		Command command17 = new Command("annoyed-sneaker-6", 17, 3, 20); 
		Command command18 = new Command("zealous-volt-24", 16, 9, 10); 
		Command command19 = new Command("low-sword-95", 16, 2, 15); 
		Command command20 = new Command("talented-bankroll-68", 18, 15, 1); 
		Command command21 = new Command("gleaming-jackpot-63", 20, 5, 29); 
		Command command22 = new Command("gifted-iceskate-26", 20, 3, 11); 
		Command command23 = new Command("curved-eyetooth-20", 22, 8, 2); 
		Command command24 = new Command("deep-railroad-20", 24, 1, 10); 
		Command command25 = new Command("long-toothpaste-58", 22, 20, 5); 
		Command command26 = new Command("broad-waiver-85", 26, 5, 28); 
		Command command27 = new Command("super-passover-5", 29, 4, 13); 
		Command command28 = new Command("tame-windmill-44", 26, 10, 8); 
		Command command29 = new Command("drab-upstart-95", 26, 10, 12); 
		Command command30 = new Command("brief-traction-2", 27, 6, 2); 
		Command command31 = new Command("zealous-apathetic-74", 34, 3, 19); 
		Command command32 = new Command("homely-spotlight-36", 34, 4, 18); 
		Command command33 = new Command("silent-origami-52", 30, 10, 8); 
		Command command34 = new Command("brainy-gristle-81", 31, 1, 8); 
		Command command35 = new Command("high-mouthful-26", 31, 13, 1); 
		Command command36 = new Command("comfortable-tumbleweed-80", 37, 8, 11); 
		Command command37 = new Command("awful-poem-62", 38, 10, 20); 
		Command command38 = new Command("curious-trash-9", 38, 5, 2); 
		Command command39 = new Command("bright-pot-10", 37, 5, 11); 
		Command command40 = new Command("expensive-thinker-47", 37, 13, 4); 
		Command command41 = new Command("dead-detergent-4", 42, 6, 27); 
		Command command42 = new Command("annoyed-sponge-21", 44, 8, 10); 
		Command command43 = new Command("super-lubricant-61", 42, 1, 10); 
		Command command44 = new Command("homeless-castle-43", 43, 5, 10); 
		Command command45 = new Command("petite-pig-89", 41, 9, 4); 
		Command command46 = new Command("steep-wineglass-35", 49, 10, 9); 
		Command command47 = new Command("dark-comedian-88", 46, 9, 10); 
		Command command48 = new Command("anxious-asparagus-8", 48, 7, 1); 
		Command command49 = new Command("tall-sphere-99", 47, 3, 10); 
		Command command50 = new Command("glorious-wildlife-89", 49, 1, 6); 
		Command command51 = new Command("bewildered-gazelle-86", 52, 6, 8); 
		Command command52 = new Command("scrawny-pantry-29", 53, 3, 12); 
		Command command53 = new Command("talented-oceanographer-2", 52, 7, 8); 
		Command command54 = new Command("cautious-cowhide-2", 53, 1, 6); 
		Command command55 = new Command("glorious-starlet-5", 53, 3, 7);
		
		List<Command> commands = Lists.newArrayList(command1, command2, command3, command4,command5, command6, command7, command8,command9, command10,
				command11, command12, command13, command14,command15, command16, command17, command18,command19, command20,
				command21, command22, command23, command24,command25, command26, command27, command28,command29, command30,
				command31, command32, command33, command34,command35, command36, command37, command38,command39, command40,
				command41, command42, command43, command44,command45, command46, command47, command48,command49, command50,
				command51, command52, command53, command54,command55);
		
		long startTime = System.currentTimeMillis();
		Response response = jajaScriptResource.optimize(commands);
		System.out.println("Temps ecoule=" + (System.currentTimeMillis() - startTime));
		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=232, path=[energetic-collarbone-34, crazy-newsman-66, uninterested-toothbrush-46, selfish-marquee-45, annoyed-sneaker-6, gleaming-jackpot-63, broad-waiver-85, brainy-gristle-81, zealous-apathetic-74, bright-pot-10, dead-detergent-4, scrawny-pantry-29]}");
		
	}
	
	
	
	@Test
	public void testLevel33_8() {
Command command1  =  new Command("slow-symbolism-41", 3, 10, 26); 
Command command2  =  new Command("swift-rehab-88", 0, 4, 18); 
Command command3  =  new Command("resonant-knitter-77", 0, 3, 6); 
Command command4  =  new Command("excited-liar-72", 0, 5, 12); 
Command command5  =  new Command("square-wiring-86", 4, 2, 7); 
Command command6  =  new Command("relieved-cylinder-47", 8, 3, 4); 
Command command7  =  new Command("wicked-wiretap-58", 9, 6, 17); 
Command command8  =  new Command("dead-prodigy-51", 8, 6, 2); 
Command command9  =  new Command("brave-victim-46", 5, 8, 9); 
Command command10 =  new Command("colorful-minion-3", 7, 9, 6); 
Command command11 =  new Command("rich-signature-63", 13, 3, 18); 
Command command12 =  new Command("unsightly-gong-77", 14, 1, 14); 
Command command13 =  new Command("calm-grasshopper-23", 11, 9, 1); 
Command command14 =  new Command("alert-carp-21", 13, 4, 10); 
Command command15 =  new Command("glorious-stalemate-77", 10, 20, 5); 
Command command16 =  new Command("fancy-sleepyhead-43", 17, 2, 24); 
Command command17 =  new Command("frantic-underachiever-37", 15, 2, 8); 
Command command18 =  new Command("innocent-cradle-14", 16, 5, 8); 
Command command19 =  new Command("outrageous-violet-96", 17, 7, 8); 
Command command20 =  new Command("calm-art-33", 15, 1, 4); 
Command command21 =  new Command("muddy-leopard-16", 22, 9, 9); 
Command command22 =  new Command("glorious-revenue-91", 20, 10, 5); 
Command command23 =  new Command("sleepy-jib-10", 23, 1, 1); 
Command command24 =  new Command("screeching-hippy-28", 21, 8, 9); 
Command command25 =  new Command("alive-courthouse-75", 21, 2, 5); 
Command command26 =  new Command("horrible-viewpoint-85", 25, 5, 25); 
Command command27 =  new Command("rapid-vine-44", 27, 1, 22); 
Command command28 =  new Command("cooperative-sunscreen-11", 28, 10, 5); 
Command command29 =  new Command("shrill-rant-81", 25, 2, 6); 
Command command30 =  new Command("helpful-sunup-32", 29, 13, 2); 
Command command31 =  new Command("testy-minus-75", 32, 9, 5); 
Command command32 =  new Command("jittery-leprechaun-59", 33, 10, 20); 
Command command33 =  new Command("helpless-undershorts-79", 31, 3, 2); 
Command command34 =  new Command("hollow-millionaire-43", 32, 6, 7); 
Command command35 =  new Command("deafening-blob-70", 33, 10, 2); 
Command command36 =  new Command("little-sausage-13", 38, 8, 18); 
Command command37 =  new Command("cooing-molecule-91", 37, 6, 5); 
Command command38 =  new Command("talented-crossbow-38", 39, 2, 3); 
Command command39 =  new Command("fast-picnic-94", 38, 3, 12); 
Command command40 =  new Command("blushing-starch-55", 37, 16, 1); 
Command command41 =  new Command("busy-syllable-39", 41, 9, 2); 
Command command42 =  new Command("resonant-kidnapper-35", 40, 8, 21); 
Command command43 =  new Command("crooked-fuzz-58", 41, 6, 1); 
Command command44 =  new Command("husky-victor-29", 40, 10, 7); 
Command command45 =  new Command("cooperative-eyetooth-71", 42, 6, 2); 
Command command46 =  new Command("zealous-mice-4", 49, 4, 19); 
Command command47 =  new Command("scrawny-standin-36", 46, 2, 6); 
Command command48 =  new Command("weary-steppingstone-11", 46, 7, 9); 
Command command49 =  new Command("bored-computer-16", 49, 4, 12); 
Command command50 =  new Command("zany-streptococcus-75", 48, 15, 2); 
Command command51 =  new Command("lucky-muffler-21", 54, 8, 4); 
Command command52 =  new Command("panicky-xray-80", 54, 2, 9); 
Command command53 =  new Command("helpless-absinthe-29", 52, 5, 2); 
Command command54 =  new Command("distinct-vanity-98", 53, 3, 14); 
Command command55 =  new Command("little-toy-13", 51, 11, 3); 
Command command56 =  new Command("better-rack-98", 59, 2, 29); 
Command command57 =  new Command("jittery-banknote-9", 55, 1, 13); 
Command command58 =  new Command("muddy-keynote-46", 57, 2, 1); 
Command command59 =  new Command("short-peach-31", 56, 3, 9); 
Command command60 =  new Command("clever-perpetrator-54", 59, 19, 5); 
		
		

		
		List<Command> commands = Lists.newArrayList(command1, command2, command3, command4,command5, command6, command7, command8,command9, command10,
				command11, command12, command13, command14,command15, command16, command17, command18,command19, command20,
				command21, command22, command23, command24,command25, command26, command27, command28,command29, command30,
				command31, command32, command33, command34,command35, command36, command37, command38,command39, command40,
				command41, command42, command43, command44,command45, command46, command47, command48,command49, command50,
				command51, command52, command53, command54,command55, command56, command57, command58,command59, command60);
		
		long startTime = System.currentTimeMillis();
		Response response = jajaScriptResource.optimize(commands);
		System.out.println("Temps ecoule=" + (System.currentTimeMillis() - startTime));
		assertThat(response.getEntity().toString()).isEqualTo(
				"Result{gain=214, path=[resonant-knitter-77, slow-symbolism-41, unsightly-gong-77, frantic-underachiever-37, fancy-sleepyhead-43, alive-courthouse-75, sleepy-jib-10, shrill-rant-81, rapid-vine-44, hollow-millionaire-43, little-sausage-13, scrawny-standin-36, zealous-mice-4, distinct-vanity-98, short-peach-31, better-rack-98]}");
		
	}
	
	

}
