package de.hska.vs2.beschte.Babble.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String greetingSubmit(@ModelAttribute Login login, Model model) {
		model.addAttribute("login", login != null ? login : new Login());
		return "login";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute Login login, Model model) {
		return "timeline";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String addUser(@ModelAttribute User user) {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute User user, Model model) {
		userRepository.saveUser(user);
		model.addAttribute("message", "User successfully added");
		return "timeline";
	}
}
