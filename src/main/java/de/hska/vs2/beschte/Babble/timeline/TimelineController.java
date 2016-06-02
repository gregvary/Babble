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
import de.hska.vs2.beschte.Babble.user.User;
import de.hska.vs2.beschte.Babble.user.UserRepository;

@Controller
public class TimelineController {
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private TimelineFactory timelineFactory;

	@RequestMapping(value = "/user/{username}", method = RequestMethod.GET)
	public String showUserTimeline(@RequestParam(name = "page", defaultValue = "1") int page, @PathVariable("username") String username, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		initTimelineModel(
				model, 
				2, 
				SimpleSecurity.getUsername(), 
				userRepository.findAndCreateUser(username), 
				timelineFactory.createUserTimeline(username, page));
		return "timeline";
	}
	
	private void initTimelineModel(Model model, int navstate, String username, User user, Timeline timeline) {
		model.addAttribute("navstate", navstate);
		model.addAttribute("post", new Post());
		model.addAttribute("username", username);
		model.addAttribute("user", user);
		model.addAttribute("timeline", timeline);
	}
	
	@RequestMapping(value = "/post", method = RequestMethod.POST)
	public String post(@ModelAttribute Post post, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		post.setTimestamp(new Date());
		userRepository.savePost(post, SimpleSecurity.getUsername());
		return "redirect:/feed";
	}
	
	@RequestMapping(value = "/follow", method = RequestMethod.GET)
	public String follow(@RequestParam(name = "user") String user, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		userRepository.follow(SimpleSecurity.getUsername(), user);
		return "redirect:/feed";
	}
	
	@RequestMapping(value = "/timeline", method = RequestMethod.GET)
	public String showGlobalTimeline(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		initTimelineModel(
				model, 
				1, 
				SimpleSecurity.getUsername(), 
				null, 
				timelineFactory.createGlobalTimelineForRange(page));
		return "timeline";
	}
	
	@RequestMapping(value = "/feed", method = RequestMethod.GET)
	public String showFeedTimeline(@RequestParam(name = "page", defaultValue = "1") int page, Model model) {
		if(!SimpleSecurity.isSignedIn())
			return "redirect:/";
		
		initTimelineModel(
				model, 
				0, 
				SimpleSecurity.getUsername(), 
				null, 
				timelineFactory.createFeedTimeline(SimpleSecurity.getUsername(), page));
		return "timeline";
	}
}
