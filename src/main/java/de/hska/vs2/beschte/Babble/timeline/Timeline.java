package de.hska.vs2.beschte.Babble.timeline;

import java.util.LinkedHashMap;
import java.util.Map;

import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.user.User;

public class Timeline {

	private Map<Post, User> entries = new LinkedHashMap<>();
	private int page = 1;
	
	public Timeline(int page) {
		this.page = page;
	}

	public Map<Post, User> getEntries() {
		return entries;
	}

	public void setEntries(Map<Post, User> entries) {
		this.entries = entries;
	}

	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}	
}
