package fr.gbloquel.codestory;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class RootServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write(answer(req.getParameter("q")));
    }

    String answer(String parameter) {
    	if(parameter.equals("Quelle est ton adresse email"))
    		return "gregory.bloquel@gmail.com";
    	else if(parameter.equals("Es tu abonne a la mailing list(OUI/NON)")) {
    		return "OUI";
    	}
    	return "";
    }

    
    public static void main(String[] args) throws Exception {
        Server server = new Server(6543);
        ServletContextHandler context = new ServletContextHandler(server, "/");
        context.addServlet(new ServletHolder(new RootServlet()), "/*");
        server.setHandler(context);
        server.start();
    }
}
