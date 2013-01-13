package fr.gbloquel.codestory;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.servlet.ServletContainer;


/**
 * Main class to boot Jetty with Jersey.
 * @author greg
 *
 */
public class CodeStory {

	private static final Logger logger = LoggerFactory
			.getLogger(CodeStory.class);

	public static void main(String[] args) throws Exception {

		ServletHolder sh = new ServletHolder(ServletContainer.class);

		sh.setInitParameter(
				"com.sun.jersey.config.property.resourceConfigClass",
				"com.sun.jersey.api.core.PackagesResourceConfig");
		sh.setInitParameter("com.sun.jersey.config.property.packages",
				"fr.gbloquel.codestory.services");
		Server server = new Server(6543);

		ServletContextHandler context = new ServletContextHandler(server, "/",
				ServletContextHandler.SESSIONS);
		context.addServlet(sh, "/*");

		server.start();

	}
}
