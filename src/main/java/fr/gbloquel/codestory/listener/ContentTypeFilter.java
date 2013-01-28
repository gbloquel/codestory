package fr.gbloquel.codestory.listener;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

import fr.gbloquel.codestory.services.JajaScriptResource;

/**
 * This class update the content Type FROM application/x-www-form-urlencoded to
 * application/json only for request from jajascript/optimize
 * 
 * @author g.bloquel
 * 
 */
public class ContentTypeFilter implements ContainerRequestFilter {

	private static final Logger logger = LoggerFactory
			.getLogger(ContentTypeFilter.class);
	
    @Override
    public ContainerRequest filter(ContainerRequest request) {

        if (request.getPath().startsWith("jajascript/optimize") && request.getMethod().equals("POST")) {

            request.getRequestHeaders().remove(HttpHeaders.CONTENT_TYPE);
            request.getRequestHeaders().putSingle(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);
            
            
            try {
				IOUtils.copy(request.getEntityInputStream(), new FileOutputStream("request.json"));
			} catch (FileNotFoundException e) {
				logger.warn("The file is not found", e);
			} catch (IOException e) {
				logger.warn("I/O exception during the copy.", e);
			}

        }
        return request;
    }

}
