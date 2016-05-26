package de.hska.vs2.beschte.Babble.post;

import java.io.Serializable;
import java.sql.Timestamp;

public class Post implements Serializable {

	private static final long serialVersionUID = 1L;

	private String id;
	private String content;
	private String userID;
	private Timestamp timestamp;

	public Post() {}

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

	public Timestamp getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Timestamp timestamp) {
		this.timestamp = timestamp;
	}

	
	
}
