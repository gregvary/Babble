package de.hska.vs2.beschte.Babble;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hska.vs2.beschte.Babble.login.Login;
import de.hska.vs2.beschte.Babble.timeline.Timeline;
import de.hska.vs2.beschte.Babble.user.User;

@Controller
public class AppController {

	@RequestMapping(value = "/")
	public String greetingSubmit(@ModelAttribute Login login, Model model) {
		model.addAttribute("login", login != null ? login : new Login());
		return "login";
	}

	@RequestMapping(value = "/timeline")
	public String showTimeline(@ModelAttribute Timeline timeline, Model model) {
		model.addAttribute("timeline", timeline);
		return "timeline";
	}

	@RequestMapping(value = "/timeline/{user-id}")
	public String showUserTimeline(@PathVariable("user-id") String userID, @ModelAttribute Timeline timeline,
			Model model) {
		model.addAttribute("timeline", timeline);
		return "timeline_user";
	}

	@RequestMapping(value = "/user/{user-id}")
	public String showProfile(@PathVariable("user-id") String userID, @ModelAttribute User user, Model model) {
		model.addAttribute("user", user);
		return "profile";
	}

	@RequestMapping(value = "/search")
	public String showSearchResults(Model model) {
		return "search";
	}
}
