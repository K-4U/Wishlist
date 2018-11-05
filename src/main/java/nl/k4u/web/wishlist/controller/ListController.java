package nl.k4u.web.wishlist.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.security.AuthSupport;

/**
 * @author Koen Beckers (K-4U)
 */
@Controller
@RequestMapping("/lists")
public class ListController {


	@Autowired
	private ListService listService;

	@RequestMapping("own")
	public String lists(Model model) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();
		List<Wishlist> wishlists = listService.getAllWishlistsByUser(user);

		model.addAttribute("lists", wishlists);

		return "lists-own";
	}
}
