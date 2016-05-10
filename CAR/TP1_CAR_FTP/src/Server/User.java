package Server;

public class User {

	public String login;
	public String password;
	
	public User(String login,String password) {
		this.login = login;
		this.password = password;
	}
	
	public boolean validPassword(String password) {
		if(password.equals(password)){
			return true;
		} else {
			return false;
		}
	}
	
	public String getLogin() {
		return this.login;
	}
	
}
