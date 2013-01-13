package fr.gbloquel.codestory;

import static net.sourceforge.jwebunit.junit.JWebUnit.assertTextPresent;
import static net.sourceforge.jwebunit.junit.JWebUnit.beginAt;
import static net.sourceforge.jwebunit.junit.JWebUnit.setBaseUrl;
import static org.fest.assertions.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;

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

	@Test
	public void should_be_happy() {
		beginAt("/?q=Es+tu+heureux+de+participer(OUI/NON)");
		assertTextPresent("OUI");

	}

	@Test
	public void should_receive_markdown_format() {
		beginAt("/?q=Es+tu+pret+a+recevoir+une+enonce+au+format+markdown+par+http+post(OUI/NON)");
		assertTextPresent("OUI");

	}

	@Test
	public void should_always_yes() {
		beginAt("?q=Est+ce+que+tu+reponds+toujours+oui(OUI/NON)");
		assertTextPresent("NON");
	}

	@Test
	public void shouldPostEnonce() {
		Client client = Client.create();

		WebResource webResource = client
				.resource("http://localhost:6543/enonce/1");
		
		String input = "blabla";
		 
		ClientResponse response = webResource.type("text/plain")
		   .post(ClientResponse.class, input);

		assertThat(response).isNotNull();
		assertThat(response.getClientResponseStatus()).isEqualTo(ClientResponse.Status.CREATED);
		
	}
}
