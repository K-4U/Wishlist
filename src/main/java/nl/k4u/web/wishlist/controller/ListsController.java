package nl.k4u.web.wishlist.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.fbo.ListFBO;
import nl.k4u.web.wishlist.security.AuthSupport;
import nl.k4u.web.wishlist.validators.ListValidator;

/**
 * @author Koen Beckers (K-4U)
 */
@Controller
@RequestMapping("/lists")
public class ListsController {

	private static final String COMMAND_NAME = "listAddCommand";

	@Autowired
	private ListService listService;

	@Autowired
	private ListValidator validator;


	@InitBinder(COMMAND_NAME)
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	private ListFBO getListFBO(Integer id) {
		ListFBO fbo = new ListFBO();
		fbo.getDelegate().setOwner(AuthSupport.getPrincipalDelegate());
		if (null != id) {
			fbo.setDelegate(listService.getWishListById(id));
		}
		return fbo;
	}

	@RequestMapping("own")
	public String lists(Model model) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();
		List<Wishlist> wishlists = listService.getAllWishlistsByUser(user);

		model.addAttribute("lists", wishlists);

		return "lists-own";
	}

	@RequestMapping(path = "own/add", method = RequestMethod.GET)
	public String addList(Model model) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();
		//May need it. Doubt it

		model.addAttribute(COMMAND_NAME, getListFBO(null));
		return "lists-own-add";
	}

	@RequestMapping(path = "own/add", method = RequestMethod.POST)
	public String saveList(@Valid @ModelAttribute(COMMAND_NAME) ListFBO obj, BindingResult result,
	                       Model model) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();

		if (result.hasErrors()) {
			//May need it. Doubt it
			model.addAttribute(COMMAND_NAME, obj);
			return "lists-own-add";
		}

		obj.getDelegate().setOwner(user);

		Wishlist saved = listService.saveList(obj.getDelegate());

		return "redirect:/list/" + saved.getId();
	}

	//TODO: Add basic edit functionality
}
