package nl.k4u.web.wishlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import nl.k4u.jpa.wishlist.service.ItemService;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.api.annotations.ResponseForbidden;
import nl.k4u.web.wishlist.api.annotations.ResponseNoContent;
import nl.k4u.web.wishlist.api.annotations.ResponseNotFound;
import nl.k4u.web.wishlist.api.annotations.ResponseOk;
import nl.k4u.web.wishlist.api.pojo.WishlistItemCreate;
import nl.k4u.web.wishlist.api.pojo.WishlistItemUpdate;
import nl.k4u.web.wishlist.security.AuthSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Calendar;

/**
 * @author Koen Beckers (K-4U)
 */
@Controller
@RestController
@RequestMapping("/api/lists")
@Tag(name = "Lists")
public class ListRestController extends BaseController {

    @Autowired
    private ListService listService;

    @Autowired
    private ItemService itemService;
    @Autowired
    private AuthSupport authSupport;


/*	@RequestMapping(path = "list/{listId}/add", method = RequestMethod.POST)
	public String saveList(@Valid @ModelAttribute(COMMAND_NAME) ItemFBO obj, BindingResult result,
                           Model model, @PathVariable Long listId) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();

		if (result.hasErrors()) {
			model.addAttribute(COMMAND_NAME, obj);
			return "list-add-item";
		}
		Wishlist wishListById = listService.getWishListById(listId);

		WishlistItem delegate = obj.getDelegate();
		delegate.setAddedOn(Calendar.getInstance().getTime());
		delegate.setOwner(user);
		delegate.setWishlist(wishListById);

		itemService.saveItem(delegate);

		messagesBean.addSuccesssMessage(delegate.getDescription() + " toegevoegd aan " + wishListById.getListName());

		return "redirect:/list/" + listId;
	}*/

    @GetMapping("/{id}")
    @Operation(summary = "Get list by ID")
    @ResponseOk
    public Wishlist getListById(@PathVariable("id") Long id) {
        return listService.getWishListById(id);
    }

    @PostMapping("/{listId}/items/{itemId}")
    @ResponseOk
    public ResponseEntity<WishlistItem> saveItem(@Valid @RequestBody WishlistItemUpdate update,
                                                 BindingResult result,
                                                 @PathVariable Long listId,
                                                 @PathVariable Long itemId) {

        if (result.hasErrors()) {
            //TODO: Figure out how to send this back?
            //Maybe throw a validationError?
        }

        WishlistItem item = itemService.getItemById(itemId);

        if (!authSupport.canEdit(item.getOwner())) {
            return ResponseEntity.status(403).build(); //TODO: Maybe just deal with this in the authSupport and throw exceptions
        }

        if (!item.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }

        item.setDescription(update.description());
        item.setPrice(update.price());
        item.setUrl(update.url());
        item.setRemarks(update.remarks());

        itemService.saveItem(item);

        return ResponseEntity.ok(item);
    }

    @PutMapping("/{listId}/items")
    @ResponseOk
    @ResponseForbidden
    public ResponseEntity<WishlistItem> addItem(@Valid @RequestBody WishlistItemCreate update,
                                                BindingResult result,
                                                @PathVariable Long listId) {

        if (result.hasErrors()) {
            //TODO: Figure out how to send this back?
            //Maybe throw a validationError?
        }

        BeckersUser user = AuthSupport.getPrincipalDelegate();

        WishlistItem item = new WishlistItem();
        Wishlist wishListById = listService.getWishListById(listId);

        if (!authSupport.canEdit(wishListById.getOwner())) {
            return ResponseEntity.status(403).build(); //TODO: Maybe just deal with this in the authSupport and throw exceptions
        }

        item.setAddedOn(Calendar.getInstance().getTime());
        item.setOwner(user);
        item.setWishlist(wishListById);

        item.setDescription(update.description());
        item.setPrice(update.price());
        item.setUrl(update.url());
        item.setRemarks(update.remarks());

        itemService.saveItem(item);

        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{listId}/items/{itemId}")
    @ResponseNoContent
    @ResponseForbidden
    public ResponseEntity<Void> deleteItem(@PathVariable Long listId, @PathVariable Long itemId) {

        WishlistItem item = itemService.getItemById(itemId);

        if (!authSupport.canEdit(item.getOwner())) {
            return ResponseEntity.status(403).build();
        }
        if (!item.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }

        item.setDeleted(true);
        itemService.saveItem(item);

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{listId}/items/{itemId}")
    @ResponseOk
    @ResponseNotFound
    public ResponseEntity<WishlistItem> getItem(@PathVariable Long listId, @PathVariable Long itemId) {

        WishlistItem itemById = itemService.getItemById(itemId);
        if (!itemById.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemById);
    }

	/*@RequestMapping(path = "list/{listId}/buy/{itemId}", method = RequestMethod.GET)
	public String buyItem(Model model, @PathVariable Integer listId, @PathVariable Integer itemId) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();

		WishlistItem item = itemService.getItemById(itemId);
		//item.setPurchaseEvent();
		item.setPurchasedOn(Calendar.getInstance().getTime());
		item.setPurchasedBy(user);

		itemService.saveItem(item);

		messagesBean.addSuccesssMessage(item.getDescription() + " gemarkeerd als gekocht!");

		return "redirect:/list/" + listId;
	}*/

}