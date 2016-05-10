package Server;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

// exe : telnet localhost 2000
public class Serveur extends Thread{
	
	public static int PORT = 2000;
	
	public ServerSocket serveurSocket;
	public Socket socket = new Socket();
	
	/** Initialisation du Serveur avec le port **/
	public void init(int port){
		try {
			this.serveurSocket = new ServerSocket(port);
			System.out.println("Initialization OK on: "+ port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void run(JFrame f){
		while(true){
			System.out.println("Waiting client ...");
			try {
				this.socket = this.serveurSocket.accept();
				new Thread(new FtpRequest(this.socket,f)).start();
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
	}
	

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		JFrame f = new JFrame();
		f.setSize(300, 100);
		f.setLocationRelativeTo(null);
		f.setResizable(false);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.add(new JLabel("Server online",SwingConstants.CENTER));
		f.setAlwaysOnTop( true );
		f.setVisible(true);
		Serveur serv = new Serveur();
		serv.init(PORT);
		serv.run(f);
	}

}
