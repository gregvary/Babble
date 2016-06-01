package de.hska.vs2.beschte.Babble.timeline;

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
public class TimelineController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TimelineFactory timelineFactory;

	@RequestMapping(value = "/timeline/{username}", method = RequestMethod.GET)
	public String showUserTimeline(@RequestParam(name = "page", defaultValue = "1") int page, @PathVariable("username") String username, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		model.addAttribute("username", SimpleSecurity.getUsername());
		model.addAttribute("user", userRepository.findAndCreateUser(username));
		model.addAttribute("timeline", timelineFactory.createUserTimeline(username, page));
		return "timeline_user";
	}
	
	@RequestMapping(value = "/timeline", method = RequestMethod.POST)
	public String post(@ModelAttribute Post post, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		post.setTimestamp(new Date());
		userRepository.savePost(post, SimpleSecurity.getUsername());
		
		model.addAttribute("username", SimpleSecurity.getUsername());
		model.addAttribute("post", new Post());
		model.addAttribute("timeline", timelineFactory.createGlobalTimelineForRange(1));
		return "timeline_global";
	}
	
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public String follow(@RequestParam(name = "user") String user, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		userRepository.follow(SimpleSecurity.getUsername(), user);
		return "redirect:/timeline";
	}
	
	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public String showGlobalTimeline(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		model.addAttribute("username", SimpleSecurity.getUsername());
		model.addAttribute("post", new Post());
		model.addAttribute("timeline", timelineFactory.createGlobalTimelineForRange(page));
		return "timeline_global";
	}
	
	@RequestMapping(value = "/feed", method = RequestMethod.GET)
	public String showFeedTimeline(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		model.addAttribute("username", SimpleSecurity.getUsername());
		model.addAttribute("post", new Post());
		model.addAttribute("timeline", timelineFactory.createFeedTimeline(SimpleSecurity.getUsername(), page));
		return "timeline_feed";
	}
}
