package de.hska.vs2.beschte.Babble.profile;

import java.util.LinkedHashMap;
import java.util.Map;

import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.user.User;

public class Profile {
	
	private Map<Post, User> entries = new LinkedHashMap<>();

	public Map<Post, User> getEntries() {
		return entries;
	}

	public void setEntries(Map<Post, User> entries) {
		this.entries = entries;
	}
	
}
