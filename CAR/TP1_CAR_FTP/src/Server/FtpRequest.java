package Server;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JButton;
import javax.swing.JFrame;


public class FtpRequest implements Runnable {
	
	Socket socket,dataSocket;
	String path, racine, systemRemote, adress;
	int port;
	BufferedReader reader;
	PrintWriter writer;
	DataInputStream dataReader;
	DataOutputStream dataWriter;
	User anonymous, client;
	boolean passive;
	ServerSocket passiveServer;
	String messageLab;

	// declaration des "echangeur" entre client et serveur"
	public FtpRequest (Socket socket,JFrame frame) {
		
		JButton b = new JButton("Quitter");
		b.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				processQUIT();
			}
		});
		frame.getContentPane().removeAll();
		frame.add(b);
		frame.repaint();
		frame.validate();
		this.socket = socket;
		this.path = "/folderTest"; //System.getProperty("user.dir");
		this.racine = this.path;
		this.systemRemote = System.getProperty("os.name");
		anonymous = new User("anonymous", "anonymous");
		try {
			this.reader = new BufferedReader(new InputStreamReader(
					this.socket.getInputStream()));
			this.writer = new PrintWriter(new OutputStreamWriter(
					this.socket.getOutputStream()), true);
			this.writer.println(220 +"Service ready, login");
		} catch (IOException e) {
			System.out.println("Exception while connecting :"+e);
		}
	}
	
	
	/**
	 * Lit en continu les requêtes envoyées par le client et les transmets aux
	 * objets de type Request pour trouver et éxécuter la tâche voulue. Appelée
	 * dans run()
	 * 
	 * @throws IOException
	 */
	public void processRequest() throws IOException,TPException{
		String line;
		while ((line = reader.readLine()) != null) {
			String request[] = line.split("\\s");
			System.out.println(request[0]);
			try {
				if(request[0].equals("USER")) {
					this.processUSER(request);
				} else if(request[0].equals("PASS")) {
					this.processPASS(request);
				} else if(request[0].equals("SYST")) {
					this.processSYST();
				} else if(request[0].equals("FEAT")) {
					this.processFEAT();
				} else if(request[0].equals("PWD")) {
					this.processPWD();
				} else if(request[0].equals("TYPE")) {
					this.processTYPE();
				} else if(request[0].equals("PASV")) {
					this.processPASV();
				} else if(request[0].equals("PORT")) {
					this.processPORT(request);
				} else if(request[0].equals("LIST")) {
					this.processLIST();
				} else if(request[0].equals("CWD")) {
					this.processCWD(request);
				} else if(request[0].equals("CDUP")) {
					this.processCDUP();
				} else if(request[0].equals("RETR")) {
					this.processRETR(request);
				} else if(request[0].equals("STOR")) {
					this.processSTOR(request);
				} else if(request[0].equals("QUIT")) {
					this.processQUIT();
				} else {
					throw new TPException("Unknow request "+request[0]);					
				}
			} catch (TPException e) {
				System.out.println("Exception occur :"+e);
				throw new TPException("Command not implemented");
			}
		}
	}
	
	/**
	 * methode de verification de la requete user
	 * @param request
	 * @throws TPException
	 */
	public void processUSER(String[] request) throws TPException {
		if(anonymous.getLogin().equals(request[1])){
			writer.println(331 +" Login ok !");
			this.client = anonymous;
		}else{
			writer.println(430 + " Invalid login");
			this.processQUIT();
			throw new TPException("Invalid login !");
		}
	}
	
	/**
	 * methode de verification de la requete pass
	 * @param request
	 * @throws TPException
	 */
	public void processPASS(String[] request) throws TPException {
		if(anonymous.validPassword(request[1])){
			writer.println(230 +"Password ok !");
			System.out.println("Logg-in succeed :"+anonymous.login);
		}else{
			writer.println(430 + " Invalid password !");
			this.processQUIT();
			throw new TPException("Invalid password !");
		}
	}
	
	/**
	 * methode renvoyant le systeme opérant
	 */
	public void processSYST() {
		writer.println(215 + " " + this.systemRemote );
		System.out.println("Current system is : " + this.systemRemote + " ...");
	}
	
	/**
	 * methode renvoyant la liste des équipements gérés par le serveur
	 */
	public void processFEAT() {
		writer.println(211 + " end");
		System.out.println("Features supported : nil");
	}
	
	/**
	 * methode renvoyant les informations sur le repertoire courant 
	 */
	public void processPWD() {
		writer.println(257 + " "+this.path+" is the current directory");
		System.out.println("Current directory is : " + this.path + " ...");
	}
	
	/**
	 * 
	 */
	public void processTYPE() {
		writer.println(200 + " type ok");
		System.out.println("Type ok...");
	}
	
	/**
	 * methode activant le serveur passif pour le transfert de données
	 */
	public void processPASV() {
		try {
			this.passiveServer = new ServerSocket(0);
		} catch (IOException e) {
			System.out.println(e);
		}
		this.port = this.passiveServer.getLocalPort();
		
		String ipServer[] = this.socket.getLocalAddress().getHostAddress().split("\\.");
		int port1 = this.port / 256;
		int port2 = this.port % 256;
		
		String args = ipServer[0] + "," + ipServer[1] + "," + ipServer[2] + "," + ipServer[3] + "," + port1 + "," + port2;
		this.writer.println(227 + " Entering passive mode " + args);
		this.passive = true;
		/*writer.println(421 + " Can't create socket ...");
		System.out.println("No passive mode ...");*/
	}
	
	/**
	 * methode renvoyant le port courant
	 * @param request
	 */
	public void processPORT(String[] request) {
		String client[] = request[1].split(",");
		this.adress = client[0] + "." + client[1] + "." + client[2] + "." + client[3];
		port = (Integer.parseInt(client[4]) * 256) + Integer.parseInt(client[5]);
		System.out.println("Adress : " + this.adress + " & port: " + this.port);		
		writer.println(200 + " PORT ok");
		this.passive = false;
	}
	
	/**
	 * methode renvoyant la liste des fichiers et dossiers compris dans le repertoire courant
	 */
	public void processLIST() {

		File base = new File(this.path.substring(1,this.path.length()));
		File fileList[] = base.listFiles();
		for(int x = 0; x < fileList.length; x++) {
			System.out.println(fileList[x]);
		}
		String currentFile = "";
		
		this.writer.println(150 + " Files OK, open Data Connection in ASCII"); 
		try {

			openDataSocket();
			//envoie des donnees
			//dataWriter.writeUTF(result);
			if(fileList.length > 0){
				//on formate les donnŽes pour les envoyer au format EPLF
				for (int i = 0; i < fileList.length; i++){
					if(!fileList[i].isHidden()){
						//on recupere les fichiers non caches
						if (fileList[i].isFile())
	                    {
	                        currentFile = "+s" + fileList[i].length()+",m"+fileList[i].lastModified()/1000+",\011"+fileList[i].getName()+"\015\012";
	                    }
	                    else if(fileList[i].isDirectory())
	                    {
	                        currentFile = "+/,m"+fileList[i].lastModified()/1000+",\011"+fileList[i].getName()+"\015\012";
	                    }
						currentFile = currentFile + "\r\n";
						//System.out.println(currentFile);
						this.dataWriter.writeBytes(currentFile);
						this.dataWriter.flush();
					}				
				}			
			}
			//on libere le socket des donnees pour le transfert suivant
			this.dataSocket.close();
			this.writer.println(226 + " Closing data connection. Requested file action successful");
		} 
    	catch (IOException e) {
    		this.writer.println(425 + " Can't open data connection.");
    		throw new RuntimeException(e);
    	}
	}
	
	/**
	 * methode changeant le repertoir courant pas le nom de repertoire passé en parametre
	 * @param request
	 */
	public void processCWD(String[] request) {
		if(request[1].startsWith("/"))
			this.path = request[1];
		else{	
			if(this.path.compareTo("/") == 0)
				this.path = this.path + request[1];
			else
				this.path = this.path + "/" + request[1];
		}
		if(this.path.startsWith(this.racine)){
			if(new File(this.path.substring(1,this.path.length())).exists())
				this.writer.println(250 + " CWD ok");
			else{
				this.path = this.racine;
				this.writer.println(550 + " directory not exist");
			}
		}
		else{
			this.path = this.racine;
			this.writer.println(550 + " access denied");
		}
	}
	
	/**
	 * methode permettant de remonter de 1 dans la hierarchie des dossiers
	 */
	public void processCDUP() {
		String cdup = "";
		Path p = Paths.get(this.path);
		for (int i = 0; i < p.getNameCount()-1; i++) {
			cdup = cdup  + "/"  + p.getName(i);
		}
		if(cdup.startsWith(this.racine)){
			this.path = cdup;
			this.writer.println(250 + " CDUP ok");	
		}
		else{
			this.path = this.racine;
			this.writer.println(550 + " access denied");
		}
	}
	
	/**
	 * methode permettant de recuperer un fichier distant dans le repertoire local
	 * @param request
	 * @throws TPException
	 */
	public void processRETR(String[] request) throws TPException {
		this.writer.println(150 + " Files OK, open Data Connection in ASCII"); 
		try {
			//on ouvre le socket pour les donnŽes
			openDataSocket();
			FileInputStream fis;
			int i;
			String filename = "";
			for(int j = 1; j< request.length; j++) {
				filename += request[j];
				if(j<request.length-1) filename +=	" ";
			}
			//on envoie le fichier
			fis = new FileInputStream( this.path.substring(1, this.path.length()) + "/" + filename );
			while( (i = fis.read()) != -1){
				this.dataWriter.writeByte(i);
			}

			//on ferme l'envoyer de fichier
			fis.close();

			//on ferme le socket de données
			this.dataSocket.close();
			this.writer.println(226 + " Closing data connection. Requested file action successful");
		} catch (IOException e) {
			this.writer.println(425 + "Can't open data connection.");
			throw new TPException("Error retriving file :" +e);
		}
	}
	
	/**
	 * methode permettant de stocker un fichier du repertoire local dans le repertoire distant
	 * @param request
	 * @throws TPException
	 */
	public void processSTOR(String[] request) throws TPException {
		writer.println(150 + " Files OK, open Data Connection in ASCII"); 
		try {
			//on ouvre le socket pour les donnŽes
			openDataSocket();
			FileOutputStream fos;
			int i;
			String filename = "";
			for(int j = 1; j< request.length; j++) {
				filename += request[j];
				if(j<request.length-1) filename +=	" ";
			}
			//on reçois le fichier
			fos = new FileOutputStream( this.path.substring(1, this.path.length()) + "/" + filename );
			while( (i = this.dataReader.read()) != -1){
				fos.write(i);
			}
			//on ferme le receveur de fichier
			fos.close();

			//on ferme le socket de données
			this.dataSocket.close();
			this.writer.println(226 + " Closing data connection. Requested file action successful");
		} catch (IOException e) {
			this.writer.println(425 + " Can't open data connection.");
			throw new TPException("Error storing file :" +e);

		}
	}
	
	/**
	 * methode quittant l'application et fermant les sockets
	 */
	public void processQUIT() {
		try {
			writer.println(231 + " logout");
			this.socket.close();
		} catch(IOException e) {
			
		}
	}
	
	/**
	 * methode activant la socket passive pour l'envoie de donnée 
	 * ainsi que les echangeurs de données
	 * @throws IOException
	 */
	public void openDataSocket() throws IOException {
		if(this.passive)
			this.dataSocket = this.passiveServer.accept();
		else
			this.dataSocket = new Socket(InetAddress.getByName(this.adress), this.port);
		this.dataReader = new DataInputStream(this.dataSocket.getInputStream());
		this.dataWriter = new DataOutputStream(this.dataSocket.getOutputStream());
	}

	/**
	 * Méthode run éxécuté à chaque connexion d'un client. Lancement du
	 * traitement des requêtes du client
	 */
	@Override
	public void run() {
		// TODO Auto-generated method stub
		try {
			try {
				this.processRequest();
			} catch (TPException t) {
				System.out.println("error processing request :  "+t );
			}
		} catch (IOException e) {
			System.out.println("error processing request :  "+e );
		}
		try {
			System.out.println("closing connection");
			this.socket.close();
			System.exit(1);
		} catch (IOException e) {
			System.out.println("Exception disconnecting : "+e);
		}
		
	}

}
