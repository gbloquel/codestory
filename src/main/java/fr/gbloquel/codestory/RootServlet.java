package fr.gbloquel.codestory;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;

public class RootServlet extends HttpServlet {

	
	private static final String ANSWER_YES = "OUI";
	private static final String ANSWER_NO = "NON";
	
	private static final Pattern YESNO_QUESTION_PATTERN = Pattern.compile("Es.+\\(OUI/NON\\)");
	
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String responseText = "";
    	if(isQuestion(request)) {
        	responseText = answer(request.getParameter("q"));
        }
    	response.getWriter().write(responseText);
    }

    /**
     * Check if the request is a question
     * @param request {@link HttpServletRequest}
     * @return true if it is a question
     */
    private boolean isQuestion(HttpServletRequest request) {
    	return request.getParameter("q")!= null;
    }
    
    /**
     * Answer to the question
     * @param question the question
     * @return answer the answer
     */
    private String answer(String question) {
    	Matcher matcher = YESNO_QUESTION_PATTERN.matcher(question);
    	if(matcher.matches()) {
    		if(question.equals("Est ce que tu reponds toujours oui(OUI/NON)")) {
    			return ANSWER_NO;
    		}
    		
    		return ANSWER_YES;
    	}
    	
    	if(question.equals("Quelle est ton adresse email"))
    		return "gregory.bloquel@gmail.com";

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
