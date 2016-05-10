package com.html;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import org.apache.commons.net.ftp.FTPFile;

/**
 * Classe qui génère le code HTML en fonction de la requête REST
 * 
 * @author Dhersin Jérôme Knapik Christopher
 *
 */
public class HTMLGenerator {

	/* Variables pour les ifférentes pages HTML du projet **/
	private String PATH = new String();                    // chemin courant dans lequel on se situe dans l'api REST sur le navigateur
	private String getHomePage = new String();             // String qui contient le code html de la page d'acceuil
	private String getSignInContent = new String();        // String qui contient le code html de la page quand le client est connecté
	private String getBadConnectionContent = new String(); // String qui contient le code html de la page quand le mot de pass ou id du client sont incorrectes
	private String getWhatWentWrong = new String();        // String qui contient le code html de la page quand une erreur survient
	private String getDisconnected = new String();         // String qui contient le code html de la page de déconnexion du client
	private String getFileList = new String();             // String qui contient le code html de la page qui affichera l'ensemble des fichiers et répertoires
	
	/**
	 * Constructeur de la classe : <br>
	 * Instancie les differentes String avec les contenus des pages HTML
	 */
	public HTMLGenerator() {
		PATH = "http://localhost:8080/rest/api/";
		getHomePage = getPageHTML("ressources/HTML/Acceuil.html");
		getSignInContent = getPageHTML("ressources/HTML/ConnectedPage.html");
		getBadConnectionContent = getPageHTML("ressources/HTML/AuthentificationFailPage.html");
		getWhatWentWrong = getPageHTML("ressources/HTML/ErrorConnect.html");
		getDisconnected = getPageHTML("ressources/HTML/DisconnectedPage.html");
		getFileList = getPageHTML("ressources/HTML/ListFilesPage.html");
		this.changePATHtoGoodValue();
	}
	
	public String getHomePage() {
		return getHomePage;
	}
	
	public String getSignInContent() {
		return getSignInContent;
	}
	
	public String getBadConnectionContent() {
		return getBadConnectionContent;
	}
	
	public String getWhatWentWrong(String error) {
		return changeERROR(getWhatWentWrong, error);
	}
	
	public String getDisconnected() {
		return getDisconnected;
	}
	
	/**
	 * Methode qui complete la page HTML avec la partie qui affiche la liste des fichiers
	 * @param cwd : String -> repertoire pour lequel on veut afficher la liste des fichiers
	 * @param list : FTPFile[] -> liste des fichiers du repertoire
	 * @return Retourne une String contenant le code HTML charge d'afficher la liste des fichiers
	 */
	public String getFileList(String cwd, FTPFile[] list) {
		return createTabWithFileList(cwd, list);
	}
	
	/**
	 * Methode qui actualise la page HTML qui affiche la liste des fichiers si un fichier est supprime par le client
	 * @param cwd : String -> repertoire pour lequel on veut afficher la liste des fichiers
	 * @param list : FTPFile[] -> liste des fichiers du repertoire
	 * @param fileDeleted : String -> fichier supprime
	 * @return Retourne une String contenant le code HTML charge d'afficher la liste des fichiers
	 */
	public String getFileList(String cwd, FTPFile[] list, String fileDeleted) {
		return createTabWithFileList(cwd, list, fileDeleted);
	}
	
	/**
	 * Methode qui stocke le code d'une page HTML dans une String
	 * @param pathHTMLPage : String -> correspond au chemin du fichier HTML dont on veut recuperer le contenu
	 * @return Retourne une String qui va contenir l'ensemble du code HTML du fichier passe en parametre
	 */
	@SuppressWarnings("resource")
	public String getPageHTML(String pathHTMLPage) {
		FileChannel channel;
		ByteBuffer b = null;
		try {
			File f = new File(pathHTMLPage);
	        channel = new FileInputStream(f).getChannel();
			b = ByteBuffer.allocate((int) channel.size());
			channel.read(b);
			channel.close();
		} catch (IOException e) {
			System.out.println("Fichier " + pathHTMLPage + " introuvable!!");
			e.printStackTrace();
		}
        String s = new String(b.array());
		return s;
	}
	
	/**
	 * Methode qui permet de changer "PATH" par la valeur de la variable PATH dans chage String contenant les codes HTML
	 */
	public void changePATHtoGoodValue() {
		this.getHomePage = this.getHomePage.replace("PATH", PATH);
		this.getSignInContent = this.getSignInContent.replace("PATH", PATH);
		this.getBadConnectionContent = this.getBadConnectionContent.replace("PATH", PATH);
		this.getWhatWentWrong = this.getWhatWentWrong.replace("PATH", PATH);
		this.getDisconnected = this.getDisconnected.replace("PATH", PATH);
		this.getFileList = this.getFileList.replace("PATH", PATH);
	}
	
	/**
	 * Méthode qui remplace la chaine de characteres "ERROR" presente dans la string initial par l'erreur donnee en parametre par la variable error
	 * @param initial -> chaine de caracteres dans laquelle on va remplacer la chaine "ERROR" par le contenu de la variable error
	 * @param error -> chaine de caracteres qui correspond a l'erreur releve par le programme
	 * @return Retourne une chaine de caracteres 
	 */
	public String changeERROR(String initial ,String error) {
		return initial.replace("ERROR", error);
	}
	
	/**
	 * Methode qui cree un contenu HTML qui va permettre d'afficher l'ensemble des fichiers des ressources REST
	 * @param cwd : String -> correspond au repertoire pour lequel on veut afficher l'ensemble des fichiers
	 * @param list : FTPFile[] -> tableau qui contient les fichiers du repertoire
	 * @return Retourne une String qui correspond au code HTML qui va permettre d'afficher la liste des fichiers
	 */
	public String createTabWithFileList(String cwd, FTPFile[] list) {
		String sample = "<tr><td>#NAME</td><td>#DELETE</td><td>#DOWNLOAD</td></tr>";
		String tmp = "Directory:" + cwd;
		String fileList;
		cwd = cwd.substring(1, cwd.length());
		
		for(int i=0; i<list.length; i++) {
			tmp += sample;
			tmp = tmp.replace("#NAME", "<a href='" + PATH + "file/" + list[i].getName() + "/'>" + list[i].getName() + "</a>" );
			if(list[i].getName().contains(".")) {
				tmp = tmp.replace("#DOWNLOAD", "<a href='" + PATH + "file/" + cwd + "download/" + list[i].getName() + "'>Donwload</a>");
				tmp = tmp.replace("#DELETE", "<a href='" + PATH + "file/" + cwd + "delete/" + list[i].getName() + "'>Delete</a>");
			}
			else {
				tmp = tmp.replace("#DOWNLOAD", "");
				tmp = tmp.replace("#DELETE", "");
			}
		}
		fileList = getFileList.replace("TABLE", tmp);
		fileList = fileList.replace("MSGDELETE", "");
		return fileList;
	}
	
	
	/**
	 * 
	 * @param cwd : String -> correspond au repertoire pour lequel on veut afficher l'ensemble des fichiers
	 * @param list : FTPFile[] -> tableau qui contient les fichiers du repertoire
	 * @param fileDeleted
	 * @return Retourne une String qui correspond au code HTML qui va permettre d'afficher la liste des fichiers
	 */
	public String createTabWithFileList(String cwd, FTPFile[] list, String fileDeleted) {
		
		String sample = "<tr><td>#NAME</td><td>#DELETE</td><td>#DOWNLOAD</td></tr>";
		String tmp = "Directory:" + cwd;
		String fileList;
		
		for(int i=0; i<list.length; i++) {
			tmp += sample;
			tmp = tmp.replace("#NAME", "<a href='" + PATH + "file/" + list[i].getName() + "/'>" + list[i].getName() + "</a>" );
			if(list[i].getName().contains(".")) {
				tmp = tmp.replace("#DOWNLOAD", "<a href='" + PATH + "file/" + cwd + "download/" + list[i].getName() + "'>Donwload</a>");
				tmp = tmp.replace("#DELETE", "<a href='" + PATH + "file/" + cwd + "delete/" + list[i].getName() + "'>Delete</a>");
			}
			else {
				tmp = tmp.replace("#DOWNLOAD", "");
				tmp = tmp.replace("#DELETE", "");
			}
		}
		fileList = getFileList.replace("TABLE", tmp);
		fileList = fileList.replace("MSGDELETE", fileDeleted + " deleted with success!");
		return fileList;
	}
}