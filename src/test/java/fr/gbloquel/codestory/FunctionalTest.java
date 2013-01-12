package fr.gbloquel.codestory;


import static org.fest.assertions.api.Assertions.assertThat;
import static net.sourceforge.jwebunit.junit.JWebUnit.*;
import org.junit.Before;
import org.junit.Test;



public class FunctionalTest {

	@Before
	public void setup() {
		 setBaseUrl("http://localhost:6543/");
	}
	
	
	@Test
	public void should_getEmail() {
		beginAt("/?q=Quelle+est+ton+adresse+email");
        assertTextPresent("gregory.bloquel@gmail.com");
	}

	@Test
	public void should_subscribe_mailing_list() {
		beginAt("/?q=Es+tu+abonne+a+la+mailing+list(OUI/NON)");
        assertTextPresent("OUI");
        
	}
	
	
	
}
