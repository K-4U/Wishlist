package nl.k4u.web.wishlist.controller;

import java.util.Calendar;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import nl.k4u.jpa.wishlist.service.ItemService;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.fbo.ItemFBO;
import nl.k4u.web.wishlist.security.AuthSupport;
import nl.k4u.web.wishlist.validators.ItemValidator;

/**
 * @author Koen Beckers (K-4U)
 */
@Controller
public class ListController {

	private static final String COMMAND_NAME = "itemAddCommand";

	@Autowired
	private ListService listService;

	@Autowired
	private ItemService itemService;

	@Autowired
	private ItemValidator validator;


	@InitBinder(COMMAND_NAME)
	public void initBinder(WebDataBinder binder) {
		binder.addValidators(validator);
	}

	private ItemFBO getItemFBO(Integer listId, Integer itemId) {
		Wishlist wishListById = listService.getWishListById(listId);
		ItemFBO fbo = new ItemFBO(wishListById);
		fbo.getDelegate().setOwner(AuthSupport.getPrincipalDelegate());
		if (null != itemId) {
			//Set the delegate..
			fbo.setDelegate(itemService.getItemById(itemId));
		}
		return fbo;
	}


	@RequestMapping("/list/{id}")
	public String showList(Model model, @PathVariable Integer id) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();

		Wishlist wishListById = listService.getWishListById(id);
		if (wishListById.getOwner().getId().equals(user.getId())) {
			model.addAttribute("owner", true);
		}

		model.addAttribute("list", wishListById);

		return "list-view";
	}


	@RequestMapping(path = "list/{listId}/add", method = RequestMethod.GET)
	public String addList(Model model, @PathVariable Integer listId) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();
		//May need it. Doubt it

		model.addAttribute(COMMAND_NAME, getItemFBO(listId, null));
		return "list-add-item";
	}


	@RequestMapping(path = "list/{listId}/add", method = RequestMethod.POST)
	public String saveList(@Valid @ModelAttribute(COMMAND_NAME) ItemFBO obj, BindingResult result,
	                       Model model, @PathVariable Integer listId) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();

		if (result.hasErrors()) {
			model.addAttribute(COMMAND_NAME, obj);
			return "list-add-item";
		}

		WishlistItem delegate = obj.getDelegate();
		delegate.setAddedOn(Calendar.getInstance().getTime());
		delegate.setOwner(user);
		delegate.setWishlist(obj.getWishlist());


		itemService.saveItem(delegate);

		return "redirect:/list/" + listId;
	}

}
