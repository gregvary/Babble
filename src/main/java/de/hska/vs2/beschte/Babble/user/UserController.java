package de.hska.vs2.beschte.Babble.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {
	
	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/user/{username}")
	public String showProfile(@PathVariable("username") String username, @ModelAttribute User user, Model model) {
		model.addAttribute("user", user);
		return "profile";
	}
	
//	@RequestMapping(value = "/users", method = RequestMethod.GET)
//	public String getAllUsers(Model model) {
//		Map<Object, Object> retrievedUsers = userRepository.findAllUsers();
//		model.addAttribute("users", retrievedUsers);
//		return "users";
//	}
}
