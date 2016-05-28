package de.hska.vs2.beschte.Babble.user;

import java.io.Serializable;

public class User implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private String id;
	private String username;
	private String password;
	private String firstname;
	private String lastname;
	
	public User() {}
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

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
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
}
