package nl.k4u.web.wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * @author Koen Beckers (K-4U)
 */
@Controller
public class Authentication {

	@RequestMapping(value = "login", method = {RequestMethod.GET, RequestMethod.POST})
	public String loginPage(Model model, String error, String logout) {
		model.addAttribute("error", error);

		return "login";
	}
}
