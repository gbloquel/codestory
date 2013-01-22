package fr.gbloquel.codestory.listener;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;

import com.sun.jersey.spi.container.ContainerRequest;
import com.sun.jersey.spi.container.ContainerRequestFilter;

/**
 * This class update the content Type FROM application/x-www-form-urlencoded to
 * application/json only for request from jajascript/optimize
 * 
 * @author g.bloquel
 * 
 */
public class ContentTypeFilter implements ContainerRequestFilter {

    @Override
    public ContainerRequest filter(ContainerRequest request) {

        if (request.getPath().startsWith("jajascript/optimize") && request.getMethod().equals("POST")) {

            request.getRequestHeaders().remove(HttpHeaders.CONTENT_TYPE);
            request.getRequestHeaders().putSingle(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON);

        }
        return request;
    }

}
