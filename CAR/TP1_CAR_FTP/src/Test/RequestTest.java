package Test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import Server.Serveur;

public class RequestTest {

	public static Serveur ftpServer;
	public ClientTest ftpClient;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		ftpServer = new Serveur();
		ftpServer.init(2121);
		ftpServer.start();
	}

	@SuppressWarnings("deprecation")
	@AfterClass
	public static void tearDownAfterClass() throws Exception {
		ftpServer.stop();
	}

	@Before
	public void setUp() throws Exception {
		this.ftpClient = new ClientTest(2121);
		System.out.println("Conencted");
	}

	@After
	public void tearDown() throws Exception {
		this.ftpClient.close();
	}
	@Test
	public void testLogin() {
		assertEquals(this.ftpClient.receive(), "220 Service ready, login");
		this.ftpClient.send("USER anonymous");
		assertEquals(this.ftpClient.receive(), "331 Login ok !");
		this.ftpClient.send("PASS anonymous");
		assertEquals(this.ftpClient.receive(), "230 Password ok !");
	}
	@Test
	public void testLoginFail() {
		assertEquals(this.ftpClient.receive(), "220 Service ready, login");
		this.ftpClient.send("USER failtest");
		assertEquals(this.ftpClient.receive(), "430 Invalid login!");
	}
	
	public void testPwd() {
		this.ftpClient.send("PWD");
		assertEquals(this.ftpClient.receive(), "257 /folderTest is the current directory");
	}
	
	public void testCwd() {
		this.ftpClient.send("CWD toto");
		assertEquals(this.ftpClient.receive(), "250 CWD ok");
	}
	
}
