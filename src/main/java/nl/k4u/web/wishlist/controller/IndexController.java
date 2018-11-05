package nl.k4u.web.wishlist.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Koen Beckers (K-4U)
 */
@Controller
public class IndexController {

	@RequestMapping("/")
	public String index() {
		return "index";
	}
}
