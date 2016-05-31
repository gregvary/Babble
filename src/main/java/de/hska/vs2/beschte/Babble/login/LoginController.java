package de.hska.vs2.beschte.Babble.login;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.vs2.beschte.Babble.Util;
import de.hska.vs2.beschte.Babble.post.Post;
import de.hska.vs2.beschte.Babble.timeline.Timeline;
import de.hska.vs2.beschte.Babble.user.User;
import de.hska.vs2.beschte.Babble.user.UserRepository;

@Controller
public class LoginController {
	
	private final UserRepository userRepository;

	@Autowired
	public LoginController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLogin(Model model) {
		model.addAttribute("login", new Login());
		return "login";
	}
	
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public String signIn(@ModelAttribute Login login, Model model) {
		User user = userRepository.findAndCreateUser(login.getUsername());
		if (user == null)
			return "login";
		
		String hashedPassword = Util.hash(login.getPassword() + login.getUsername());
		if (user.getPassword().equals(hashedPassword)) {
			model.addAttribute("user", user);
			model.addAttribute("post", new Post());
			model.addAttribute("timeline", initTimeline());
			return "timeline";
		} else 
			return "login";
	}

	private Timeline initTimeline() {
		Timeline timeline = new Timeline();
		List<Post> posts = userRepository.findGlobalPostsInRange(10);
		for (int i = posts.size() - 1; i >= 0; i--) {
			User userForPost = userRepository.findAndCreateUserForPost(posts.get(i).getId());
			timeline.getEntries().put(posts.get(i), userForPost);
		}
		return timeline;
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute User user, Model model) {
		user.setPassword(Util.hash(user.getPassword() + user.getUsername()));
		userRepository.saveUser(user);
		model.addAttribute("post", new Post());
		model.addAttribute("timeline", initTimeline());
		return "timeline";
	}
}
