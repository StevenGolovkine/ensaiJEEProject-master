package jaxrs;

import java.util.logging.Logger;

import org.jboss.resteasy.plugins.server.undertow.UndertowJaxrsServer;

import com.undertow.ServletServer;

import io.undertow.Undertow;

/**
 * Class which starts the RestServer. 
 * @author steven
 *
 */
public class RestServer {

	private static final Logger logger = Logger.getLogger(ServletServer.class.getName());
	
	public static void start(){
		
		UndertowJaxrsServer ut = new UndertowJaxrsServer();

		RestService ta = new RestService();

		ut.deploy(ta);

		ut.start(Undertow.builder().addHttpListener(8081, "localhost"));

		logger.info("JAX-RS based micro-service running!");
	}
}
