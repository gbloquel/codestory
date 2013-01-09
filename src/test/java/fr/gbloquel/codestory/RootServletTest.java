package fr.gbloquel.codestory;


import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Test;



public class RootServletTest {

	private final RootServlet server = new RootServlet();
	
	
	@Test
	public void should_getEmail() {
        assertThat(server.answer("Quelle+est+ton+adresse+email")).isEqualTo("gregory.bloquel@gmail.com");
	}

}
