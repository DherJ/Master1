package com.rs;

import java.io.IOException;
import java.io.InputStream;

import javax.ws.rs.DELETE;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.html.HTMLGenerator;
import com.model.User;

/**
 * Classe qui contient les méthodes qui permettent d'accéder aux ressources de la passerelle
 * Passerelle REST accessible à  l'adresse suivante : <br>
 * 		http://localhost:8080/rest/api/
 * 
 * @author Dhersin Jérôme Knapik Christopher
 */
@Path("/")
public class PasserelleRest {

	// Port du serveur FTP
	private static final int PORT = 3377;
	private static final String HOST = "127.0.0.1";
	private User user = null;
	private HTMLGenerator html = null;
	private String cwd = null;
	

	public PasserelleRest() {
		user = new User();
		html = new HTMLGenerator();
	}
	
	@GET
	@Produces("text/html")
	public String getHomePage() {
		return this.html.getHomePage();
	}
	
	/* *
	 * Connection Part 
	 * */

	/**
	 * Connexion utilisant le formulaire sur la page d'acceuil
	 * @param _user --> nom de l'utilisateur
	 * @param _password --> mot de passe de l'utilisateur
	 * @return Retourne la page HTML en fonction de la situation
	 */
	@POST
	@Path("/signIn")
	@Produces("text/html")
	public String postSignIn( @FormParam("user") String _user, @FormParam("password") String _password ) {
		try {
			FTPClient client = new FTPClient();
			client.connect(HOST, PORT);
			if(client.login(_user,_password)){
				user.setLogin(_user);
				user.setPassword(_password);
				client.disconnect();
				return html.getSignInContent();
			}
		} catch(IOException e) {
			return html.getWhatWentWrong(e.getMessage());
		}
		return html.getBadConnectionContent();
	}
	
	/**
	 * Connexion anonyme avec user=anonymous & password=anonymous
	 * @return un message afficher sur la page HTML
	 */
	@GET
	@Path("/signIn")
	@Produces("text/html")
	public String getAnonymousSignIn() {
		try {
			FTPClient client = new FTPClient();
			client.connect(HOST,PORT);
			if(client.login("anonymous", "anonymous")) {
				user.setLogin("anonymous");
				user.setPassword("anonymous");
				client.disconnect();
				return html.getSignInContent();
			}
		} catch(IOException e) {
			return html.getWhatWentWrong(e.getMessage());
		}
		return html.getBadConnectionContent();	
	}
	
	/**
	 * 
	 * @return Retourne une String correspondant au contenu de la page HTML qui affiche la deconnexion d'un client
	 */
	@GET
	@Path("/disconnect")
	@Produces("text/html")
	public String getDisconnected() {
		user.setLogin(null);
		user.setPassword(null);
		return html.getDisconnected();
	}
	
	/* *
	 * File List 
	 * */
	/**
	 * Methode qui affiche l'ensemble des fichiers du repertoire par defaut
	 * @return Retourne une String contenant le code de la page HTML qui affiche la liste des fichiers
	 */
	@GET
	@Path("/file")
	@Produces("text/html")
	public String getFileList() {
		FTPClient client = new FTPClient();
		try {
			client.connect(HOST,PORT);
		} catch(IOException e) {
			return html.getWhatWentWrong(e.getMessage());
		}
		try {
			if(client.login(user.getLogin(), user.getPassword())) {
				System.out.println("getFileList");
				FTPFile[] fileList = client.listFiles();
				cwd = client.printWorkingDirectory();
				client.disconnect();
				return html.getFileList(cwd, fileList);
			}
		} catch(IOException e) {
			return html.getWhatWentWrong(e.getMessage());
		}
		return html.getBadConnectionContent();
	}
	
	/**
	 * Methode qui affiche l'ensemble des fichiers du repertoire donne en parametre
	 * @param dir : String -> correspond au repertoire dont on veut affichier l'ensemble de ses fichiers
	 * @return Retourne une String contenant le code de la page HTML qui affiche la liste des fichiers
	 */
	@GET
    @Path("/file/{var: .*}")
    @Produces("text/html")
    public String getFileList(@PathParam("var") String dir) {
        FTPClient client = new FTPClient();
        try {
            client.connect(HOST, PORT);
        } catch (IOException e) {
            return html.getBadConnectionContent();
        }
        try {
            if (client.login(user.getLogin(), user.getPassword())) {
                client.cwd(dir);
                FTPFile[] fileList = client.listFiles();
                String cwd = client.printWorkingDirectory();
                client.disconnect();
                return html.getFileList(cwd, fileList);
            }
        } catch (IOException e) {
            return html.getWhatWentWrong(e.getMessage());
        }
        return html.getBadConnectionContent();
    }
	
	/* *
	 * Download file
	 * */
	/**
	 * Methode qui permet de telecharger un fichier
	 * @param dir : String -> le repertoire dans lequel se trouve le fichier a telecharger
	 * @param fileToDownload : String -> le nom du fichier a telecharger
	 * @return Retourne une Response qui indique l'etat du telechargement
	 */
	@GET
	@Path("/file/{var: .*}/download/{fileToDownload}")
	@Produces("text/html")
	public Response download(@PathParam("var") String dir, @PathParam("fileToDownload") String fileToDownload ) {
		FTPClient client = new FTPClient();
		try{
			client.connect(HOST, PORT);
		} catch(IOException e) {
			return null;
		}
		try{
			if(client.login(user.getLogin(), user.getPassword())) {
                client.cwd(dir);
                InputStream in = client.retrieveFileStream(fileToDownload);
                Response reponse = Response.ok(in, MediaType.APPLICATION_OCTET_STREAM).build();
                client.disconnect();
                return reponse;
            }
        } catch (IOException e) {
            return null;
        }
        return null;
    }
	
	/* *
	 * Delete file
	 * */
	/**
	 * Methode qui permet de supprimer un fichier
	 * @param dir : String -> le repertoire dans lequel se trouve le fichier a supprimer
	 * @param fileToDelete : String -> nom du fichier a supprimer
	 * @return Retourne une String contenant le code de la page HTML qui affiche la liste des fichiers actualisee
	 */
	@GET
	@Path("/file/{var: .*}/delete/{fileToDelete}")
	@Produces("text/html")
	public String delete(@PathParam("var") String dir, @PathParam("fileToDelete") String fileToDelete ) {
		FTPClient client = new FTPClient();
		try{
			client.connect(HOST, PORT);
		} catch(IOException e) {
			return null;
		}
		try {
			if(client.login(user.getLogin(), user.getPassword())) {
                client.cwd(dir);
                client.deleteFile(fileToDelete);
                FTPFile[] fileList = client.listFiles();
                client.disconnect();
                return html.getFileList(cwd, fileList, fileToDelete);
			}
        } catch (IOException e) {
            return html.getWhatWentWrong(e.getMessage());
        }
        return html.getBadConnectionContent();
    }
	
	/**
	 * Methode qui permet de supprimer un fichier
	 * @param file : String -> nom du fichier a supprimer
	 * @return Retourne une String contenant le code de la page HTML qui affiche la liste des fichiers actualisee
	 */
	@DELETE
	@Path("/file/{var: .*}")
	@Produces("text/html")
	public String deleteFile(@PathParam("var") String file) {
		FTPClient client = new FTPClient();
		try {
			client.connect(HOST, PORT);
		} catch (IOException ex) {
			return html.getBadConnectionContent();
		}
		try {
			if (client.login(this.user.getLogin(), this.user.getPassword())) {
				client.deleteFile(file);
				FTPFile[] fileList = client.listFiles();
				client.disconnect();
				return html.getFileList(cwd, fileList, file);
			}
		} catch (IOException ioe) {
			return html.getWhatWentWrong(ioe.getMessage());
		}
		return "";
	}
}