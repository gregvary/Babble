package de.hska.vs2.beschte.Babble.profile;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import de.hska.vs2.beschte.Babble.login.security.SimpleSecurity;
import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.user.UserRepository;

@Controller
public class ProfileController {
	
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private ProfileFactory profileFactory;

	@RequestMapping(value = "/profile/{user-id}", method = RequestMethod.GET)
	public String showUserTimeline(@PathVariable("user-id") String userID, @ModelAttribute Profile profile,
			Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		model.addAttribute("profile", profile);
		return "profile";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.POST)
	public String post(@ModelAttribute Post post, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		post.setTimestamp(new Date());
		userRepository.savePost(post, SimpleSecurity.getUsername());
		String username = SimpleSecurity.getUsername();
		
		model.addAttribute("user", userRepository.findAndCreateUser(username));
		model.addAttribute("post", new Post());
		model.addAttribute("profile", profileFactory.createProfileTimelineForRange(0, ProfileFactory.POSTS_PER_PAGE_COUNT, username));
		return "profile";
	}
	
	@RequestMapping(value = "/profile", method = RequestMethod.GET)
	public String showTimelinePage(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
						
		long end = page * ProfileFactory.POSTS_PER_PAGE_COUNT;
		long start = end - ProfileFactory.POSTS_PER_PAGE_COUNT;
		String username = SimpleSecurity.getUsername();
		
		model.addAttribute("user", userRepository.findAndCreateUser(username));
		model.addAttribute("post", new Post());
		model.addAttribute("profile", profileFactory.createProfileTimelineForRange(start, end, username));
		return "profile";
	}
	
}
