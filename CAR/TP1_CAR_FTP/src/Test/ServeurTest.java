package Test;
import static org.junit.Assert.*;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import Server.Serveur;
public class ServeurTest {
	@Test
	public void testRunning() throws UnknownHostException, IOException {
		  Serveur serveur = new Serveur();
		  serveur.init(2000);
		  assertNotNull(serveur.serveurSocket);
		  assertNotNull(serveur.socket);
		  assertEquals(serveur.serveurSocket.getLocalPort(), 2121);
	}
}
