package de.hska.vs2.beschte.Babble.timeline.global;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import de.hska.vs2.beschte.Babble.login.Login;

@Controller
public class GlobalTimelineController {

	@RequestMapping(value = "/timeline")
	public String greetingSubmit(@ModelAttribute Login login, Model model) {
		model.addAttribute("login", login);
		return "timeline";
	}
}
