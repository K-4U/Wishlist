package nl.k4u.jpa.wishlist.service;

import nl.k4u.jpa.wishlist.dao.WishlistItemDao;
import nl.k4u.jpa.wishlist.enums.Event;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
public class ItemService {

	@Autowired
	private WishlistItemDao repository;

	public WishlistItem getItemById(Integer id) {
        return repository.findById(id.longValue()).get();
    }

    public WishlistItem getItemById(Long id) {
        return repository.findById(id).get();
	}

	public WishlistItem saveItem(WishlistItem delegate) {
		return repository.save(delegate);
	}

	public void buyItem(BeckersUser user, Event purchaseEvent, WishlistItem item) {
		item = getItemById(item.getId());
		item.setPurchasedBy(user);
		item.setPurchasedOn(Calendar.getInstance().getTime());
		item.setPurchaseEvent(purchaseEvent);

		saveItem(item);
	}
}
