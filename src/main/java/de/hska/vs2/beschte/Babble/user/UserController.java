package de.hska.vs2.beschte.Babble.user;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {
	
	@RequestMapping(value = "/user/{username}")
	public String showProfile(@PathVariable("username") String username, @ModelAttribute User user, Model model) {
		model.addAttribute("user", user);
		return "timeline";
	}
}
