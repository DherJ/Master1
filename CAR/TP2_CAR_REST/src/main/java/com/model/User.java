package com.model;

import javax.inject.Singleton;

/**
 * Classe qui repr�sente les donn�es li�es aux clients qui se connectent sur la passerelle REST
 * 
 * @author Dhersin J�r�me Knapik Christopher 
 *
 */
@Singleton
public class User {
	
	private String login;
	private String password;
	
	public String getLogin() {
		return this.login;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setLogin(String log) {
		this.login = log;
	}
	
	public void setPassword(String pass) {
		this.password = pass;
	}
}