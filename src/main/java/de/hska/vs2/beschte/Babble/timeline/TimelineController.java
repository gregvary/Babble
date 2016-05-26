package de.hska.vs2.beschte.Babble.timeline;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class TimelineController {
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
}
