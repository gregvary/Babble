package de.hska.vs2.beschte.Babble.timeline;

import java.util.LinkedHashMap;
import java.util.Map;

import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.user.User;

public class Timeline {

	private int pageIndex = 0;
	private Map<Post, User> entries = new LinkedHashMap<>();

	public Map<Post, User> getEntries() {
		return entries;
	}

	public void setEntries(Map<Post, User> entries) {
		this.entries = entries;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	
}
