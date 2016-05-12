package de.hska.vs2.beschte.Babble.user;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.vs2.beschte.Babble.redis.UserRepository;
import de.hska.vs2.beschte.Babble.user.User;


@Controller
public class UserController {
	
	private final UserRepository userRepository;

	@Autowired
	public UserController(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String addUser(@ModelAttribute User user) {
		return "register";
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String saveUser(@ModelAttribute User user, Model model) {

		userRepository.saveUser(user);
		model.addAttribute("message", "User successfully added");

		Map<Object, Object> retrievedUsers = userRepository.findAllUsers();

		model.addAttribute("users", retrievedUsers);
		return "users";
	}
	
	@RequestMapping(value = "/users", method = RequestMethod.GET)
	public String getAllUsers(Model model) {
		Map<Object, Object> retrievedUsers = userRepository.findAllUsers();
		model.addAttribute("users", retrievedUsers);
		return "users";
	}
}
