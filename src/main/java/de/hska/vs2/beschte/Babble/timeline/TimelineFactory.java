package de.hska.vs2.beschte.Babble.timeline;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.user.User;
import de.hska.vs2.beschte.Babble.user.UserRepository;

@Service
public class TimelineFactory {
	public static final int POSTS_PER_PAGE_COUNT = 10;
	
	private final UserRepository userRepository;

	@Autowired
	public TimelineFactory(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	public Timeline createGlobalTimelineForRange(long start, long end) {
		Timeline timeline = new Timeline();
		List<Post> posts = userRepository.findGlobalPostsInRange(start, end);
		for (int i = posts.size() - 1; i >= 0; i--) {
			User userForPost = userRepository.findAndCreateUserForPost(posts.get(i).getId());
			timeline.getEntries().put(posts.get(i), userForPost);
		}
		return timeline;
	}
	
}
