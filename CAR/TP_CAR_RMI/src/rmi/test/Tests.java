package rmi.test;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import org.junit.Test;

import rmi.node.SiteImplGraphe;
import rmi.node.SiteImplTree;
import rmi.node.SiteItf;

/**
 * Classe de tests unitaires
 * 
 * @author Dhersin Jérôme Knapik Christopher
 *
 */
public class Tests {

	@Test
	public void testId() throws Exception {
		SiteItf instance = (SiteItf) new SiteImplTree(1);
		assertEquals(1, instance.getId());
	}
	
	@Test
	public void testName() throws Exception {
		SiteItf instance = (SiteItf) new SiteImplTree(1);
		assertEquals("Node1", instance.getName());
	}
	
	@Test
	public void testReceive() throws Exception {
		SiteItf instance = (SiteItf) new SiteImplTree(1);
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		instance.broadcast(1, "toto");
		assertEquals("Node1 a recu toto ", outContent.toString());
	}
	
	@Test
	public void testTreeReceive() throws Exception {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		String messageData = "toto";
		SiteItf instance = (SiteItf) new SiteImplTree(1);
		SiteItf instance2 = (SiteItf) new SiteImplTree(2);
		SiteItf instance3 = (SiteItf) new SiteImplTree(3);
		SiteItf instance4 = (SiteItf) new SiteImplTree(4);
		SiteItf instance5 = (SiteItf) new SiteImplTree(5);
		SiteItf instance6 = (SiteItf) new SiteImplTree(6);
		instance.createEdge(instance, instance2);
		instance2.createEdge(instance2, instance3);
		instance2.createEdge(instance2, instance4);
		instance.createEdge(instance, instance5);
		instance5.createEdge(instance5, instance6);
		instance3.broadcast(3, messageData);
		assertEquals("Node3 a recu toto ", outContent.toString());
		assertEquals("Node2 a recu toto ", outContent.toString());
		assertEquals("Node1 a recu toto ", outContent.toString());
		assertEquals("Node5 a recu toto ", outContent.toString());
		assertEquals("Node4 a recu toto ", outContent.toString());
		assertEquals("Node6 a recu toto ", outContent.toString());
	}
	
	@Test
	public void testGraphReceive() throws Exception {
		ByteArrayOutputStream outContent = new ByteArrayOutputStream();
		System.setOut(new PrintStream(outContent));
		SiteItf instance = (SiteItf) new SiteImplGraphe(1);
		SiteItf instance2 = (SiteItf) new SiteImplGraphe(2);
		instance.createEdge(instance, instance2);
		String messageData = "toto";
		instance.broadcast(1, messageData);
		assertEquals("Node1 a recu toto ", outContent.toString());
		assertEquals("Node2 a recu toto ", outContent.toString());
	}
}