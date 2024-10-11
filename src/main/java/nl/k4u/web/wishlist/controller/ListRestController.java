package nl.k4u.web.wishlist.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import nl.k4u.jpa.wishlist.service.ItemService;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.api.annotations.ResponseForbidden;
import nl.k4u.web.wishlist.api.annotations.ResponseNoContent;
import nl.k4u.web.wishlist.api.annotations.ResponseNotFound;
import nl.k4u.web.wishlist.api.annotations.ResponseOk;
import nl.k4u.web.wishlist.api.mappers.WishlistItemMapper;
import nl.k4u.web.wishlist.api.mappers.WishlistMapper;
import nl.k4u.web.wishlist.api.pojo.SingleValueWrapper;
import nl.k4u.web.wishlist.api.pojo.WishlistItemCreate;
import nl.k4u.web.wishlist.api.pojo.WishlistItemDTO;
import nl.k4u.web.wishlist.api.pojo.WishlistItemUpdate;
import nl.k4u.web.wishlist.security.AuthSupport;
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
@RequiredArgsConstructor
public class ListRestController extends BaseController {

    private final ListService listService;
    private final ItemService itemService;
    private final AuthSupport authSupport;
    private final WishlistMapper listMapper;
    private final WishlistItemMapper itemMapper;

    @PostMapping("/{listId}/items/{itemId}")
    @ResponseOk
    public ResponseEntity<WishlistItemDTO> saveItem(@Valid @RequestBody WishlistItemUpdate update,
                                                    BindingResult result,
                                                    @PathVariable Long listId,
                                                    @PathVariable Long itemId) {

        if (result.hasErrors()) {
            //TODO: Figure out how to send this back?
            //Maybe throw a validationError?
        }

        WishlistItem item = itemService.getItemById(itemId);

        authSupport.assertEdit(item.getOwner());

        if (!item.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }

        item.setDescription(update.description());
        item.setPrice(update.price());
        item.setUrl(update.url());
        item.setRemarks(update.remarks());

        itemService.saveItem(item);

        return ResponseEntity.ok(itemMapper.toDTO(item));
    }

    @PutMapping("/{listId}/items")
    @ResponseOk
    @ResponseForbidden
    public ResponseEntity<WishlistItemDTO> addItem(@Valid @RequestBody WishlistItemCreate update,
                                                BindingResult result,
                                                @PathVariable Long listId) {

        if (result.hasErrors()) {
            //TODO: Figure out how to send this back?
            //Maybe throw a validationError?
        }

        BeckersUser user = AuthSupport.getPrincipalDelegate();

        WishlistItem item = new WishlistItem();
        Wishlist wishListById = listService.getWishListById(listId);

        authSupport.assertEdit(wishListById.getOwner());

        item.setAddedOn(Calendar.getInstance().getTime());
        item.setOwner(user);
        item.setWishlist(wishListById);

        item.setDescription(update.description());
        item.setPrice(update.price());
        item.setUrl(update.url());
        item.setRemarks(update.remarks());

        itemService.saveItem(item);

        return ResponseEntity.ok(itemMapper.toDTO(item));
    }

    @DeleteMapping("/{listId}/items/{itemId}")
    @ResponseNoContent
    @ResponseForbidden
    public ResponseEntity<Void> deleteItem(@PathVariable Long listId, @PathVariable Long itemId) {

        WishlistItem item = itemService.getItemById(itemId);

        authSupport.assertEdit(item.getOwner());
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
    public ResponseEntity<WishlistItemDTO> getItem(@PathVariable Long listId, @PathVariable Long itemId) {

        WishlistItem itemById = itemService.getItemById(itemId);
        if (!itemById.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(itemMapper.toDTO(itemById));
    }

    @PostMapping("/{listId}/items/{itemId}/buy")
    @ResponseNoContent
    @ResponseNotFound
    public ResponseEntity<Void> buyItem(@PathVariable Long listId, @PathVariable Long itemId) {
		BeckersUser user = AuthSupport.getPrincipalDelegate();

		WishlistItem item = itemService.getItemById(itemId);
        if (!item.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }
		//item.setPurchaseEvent();
		item.setPurchasedOn(Calendar.getInstance().getTime());
		item.setPurchasedBy(user);

		itemService.saveItem(item);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listId}/items/{itemId}/unbuy")
    @ResponseNoContent
    @ResponseForbidden
    @ResponseNotFound
    public ResponseEntity<Void> unbuyItem(@PathVariable Long listId, @PathVariable Long itemId) {
        BeckersUser user = AuthSupport.getPrincipalDelegate();

        WishlistItem item = itemService.getItemById(itemId);
        if (!item.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }
        if (!item.getPurchasedBy().equals(user)) {
            return ResponseEntity.status(403).build();
        }
        //item.setPurchaseEvent();
        item.setPurchasedOn(null);
        item.setPurchasedBy(null);

        itemService.saveItem(item);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{listId}/items/{itemId}/move")
    @ResponseNoContent
    @ResponseForbidden
    @ResponseNotFound
    public ResponseEntity<Void> moveItem(@PathVariable Long listId, @PathVariable Long itemId, @RequestBody SingleValueWrapper<Long> targetListId) {
        BeckersUser user = AuthSupport.getPrincipalDelegate();

        WishlistItem item = itemService.getItemById(itemId);
        if (!item.getWishlist().getId().equals(listId)) {
            return ResponseEntity.notFound().build();
        }
        authSupport.assertEdit(item.getOwner());
        Wishlist targetList = listService.getWishListById(targetListId.getValue());
        if (targetList == null) {
            return ResponseEntity.notFound().build();
        }
        authSupport.assertEdit(targetList.getOwner());

        item.setWishlist(targetList);

        itemService.saveItem(item);

        return ResponseEntity.noContent().build();
    }
}
