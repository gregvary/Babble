package de.hska.vs2.beschte.Babble.post;

import java.io.Serializable;
import java.util.Date;

public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String content;
	private String userID;
	private Date timestamp;
	private String username;

	public Post() {}

	public Post(String username) {
		this.username = username;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getUserID() {
		return userID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public String getUsername() {
		return username != null ? username.toLowerCase() : null;
	}
	public void setUsername(String username) {
		this.username = username != null ? username.toLowerCase() : null;
	}

	
	
}
