package de.hska.vs2.beschte.Babble.login;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import de.hska.vs2.beschte.Babble.login.security.SecurityUtil;
import de.hska.vs2.beschte.Babble.login.security.SimpleSecurity;
import de.hska.vs2.beschte.Babble.user.User;
import de.hska.vs2.beschte.Babble.user.UserRepository;

@Controller
public class LoginController {

	@Autowired
	private UserRepository userRepository;
	@Autowired
	private LoginRepository loginRepository;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String showLogin(Model model) {
		if (!SimpleSecurity.isSignedIn()) {
			model.addAttribute("login", new Login());
			return "login";
		}

		return "redirect:/feed";
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String login(@ModelAttribute("login") @Valid Login login, HttpServletResponse response, Model model) {
		if (!isLoginValid(login.getUsername(), login.getPassword()))
			return "redirect:/";
		
		response.addCookie(createAuthCookie(login.getUsername()));
		return "redirect:/feed";

	}
	
	private boolean isLoginValid(String username, String password) {
		String hashedPassword = SecurityUtil.getUserPasswordHashed(password, username);
		return loginRepository.auth(username, hashedPassword);
	}

	private Cookie createAuthCookie(String username) {
		String auth = loginRepository.addAuth(username, SecurityUtil.TIMEOUT.getSeconds(),
				SecurityUtil.TIME_UNIT);
		Cookie cookie = new Cookie("auth", auth);
		cookie.setMaxAge((int) SecurityUtil.TIMEOUT.getSeconds());
		return cookie;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public String logout() {
		if (SimpleSecurity.isSignedIn()) {
			String name = SimpleSecurity.getUsername();
			loginRepository.deleteAuth(name);
			SimpleSecurity.logout();
		}
		return "redirect:/";
	}

	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegister(Model model) {
		model.addAttribute("user", new User());
		return "register";
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String registerUser(@ModelAttribute User user, HttpServletResponse response, Model model) {
		user.setPassword(SecurityUtil.getUserPasswordHashed(user.getPassword(), user.getUsername()));
		userRepository.saveUser(user);
		response.addCookie(createAuthCookie(user.getUsername()));
		return "redirect:/feed";
	}

}
