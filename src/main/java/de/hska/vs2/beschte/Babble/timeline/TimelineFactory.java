package de.hska.vs2.beschte.Babble.timeline;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.user.User;
import de.hska.vs2.beschte.Babble.user.UserRepository;

@Service
public class TimelineFactory {
	public static final int POSTS_PER_PAGE_COUNT = 10;

	@Autowired
	private UserRepository userRepository;

	public Timeline createGlobalTimelineForRange(int page) {
		long end = page * TimelineFactory.POSTS_PER_PAGE_COUNT;
		long start = end - TimelineFactory.POSTS_PER_PAGE_COUNT;
		Timeline timeline = new Timeline(page);
		List<Post> posts = userRepository.findGlobalPostsInRange(start, end);
		for (Post post : posts) {
			User userForPost = userRepository.findAndCreateUserForPost(post.getId());
			timeline.getEntries().put(post, userForPost);
		}
		return timeline;
	}

	public Timeline createUserTimeline(String username, int page) {
		long end = page * TimelineFactory.POSTS_PER_PAGE_COUNT;
		long start = end - TimelineFactory.POSTS_PER_PAGE_COUNT;
		Timeline timeline = new Timeline(page);
		List<Post> posts = userRepository.findUserPostsInRange(username, start, end);
		for (Post post : posts) {
			User userForPost = userRepository.findAndCreateUserForPost(post.getId());
			timeline.getEntries().put(post, userForPost);
		}
		return timeline;
	}

	public Timeline createFeedTimeline(String username, int page) {
		long end = page * TimelineFactory.POSTS_PER_PAGE_COUNT;
		long start = end - TimelineFactory.POSTS_PER_PAGE_COUNT;
		Timeline timeline = new Timeline(page);

		Set<String> followingIDs = userRepository.findFollowingIDs(username);
		List<Post> posts = userRepository.findGlobalPostsInRange(start, end);
		for (Post post : posts) {
			User userForPost = userRepository.findAndCreateUserForPost(post.getId());
			if (followingIDs.contains(userForPost.getUsername()))
				timeline.getEntries().put(post, userForPost);
		}
		return timeline;
	}

}
