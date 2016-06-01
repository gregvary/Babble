package de.hska.vs2.beschte.Babble.login;

public class Login {

    private String username;
    private String password;
    private boolean remember;
	
    public String getUsername() {
		return username == null ? null : username.toLowerCase();
	}
	public void setUsername(String username) {
		this.username = username == null ? null : username.toLowerCase();
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public boolean isRemember() {
		return remember;
	}
	public void setRemember(boolean remember) {
		this.remember = remember;
	}
}
