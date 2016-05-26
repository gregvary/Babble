package de.hska.vs2.beschte.Babble;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppController {

	@RequestMapping(value = "/search")
	public String showSearchResults(Model model) {
		return "search";
	}
}
