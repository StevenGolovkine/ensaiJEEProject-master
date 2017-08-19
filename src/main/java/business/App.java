package business;

import com.undertow.ServletServer;

import jaxrs.RestServer;

/**
 * Main class using to launch the two servers
 * @author steven
 *
 */
public class App {

	public static void main(String[] args) {
		
		// Launch the ServletServer
		ServletServer.start();
		
		// Launch the RestServer
		RestServer.start();
	}

}
