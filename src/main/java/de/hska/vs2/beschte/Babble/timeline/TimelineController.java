package de.hska.vs2.beschte.Babble.timeline;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.user.User;
import de.hska.vs2.beschte.Babble.user.UserRepository;

@Controller
public class TimelineController {
	
	private final UserRepository userRepository;

	@Autowired
	public TimelineController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@RequestMapping(value = "/timeline/{user-id}", method = RequestMethod.GET)
	public String showUserTimeline(@PathVariable("user-id") String userID, @ModelAttribute Timeline timeline,
			Model model) {
		model.addAttribute("timeline", timeline);
		return "timeline";
	}
	
	@RequestMapping(value = "/timeline", method = RequestMethod.POST)
	public String post(@ModelAttribute Post post, @ModelAttribute User user, Model model) {
		post.setTimestamp(new Date());
		System.out.println(user.getUsername() + "...." + post.getUsername());
		userRepository.savePost(post, post.getUsername());
		
		model.addAttribute("post", new Post(post.getUsername()));
		return "timeline";
	}
	
}
