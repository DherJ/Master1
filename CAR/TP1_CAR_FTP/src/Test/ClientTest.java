package Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class ClientTest {
	public InetAddress adresse;
	public Socket socket;
	public PrintWriter writer;
	public BufferedReader reader;

	public ClientTest(int port){
		try {
			this.adresse = InetAddress.getByName("localhost"); 
			this.socket = new Socket(this.adresse,port); 
			this.writer = new PrintWriter(socket.getOutputStream(),true);
			this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(IOException e){
			e.printStackTrace();
		}
	}

	public void send(String request) {
		this.writer.println(request);
	}

	public String receive(){
		String line=null;
		try {
			line = this.reader.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return line;
	}

	public void close(){
		try {
			this.writer.close();
			this.reader.close();
			socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
