package nl.k4u.jpa.wishlist.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.k4u.jpa.wishlist.dao.WishlistItemDao;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
public class ItemService {

	@Autowired
	private WishlistItemDao repository;

	public WishlistItem getItemById(Integer id) {
		return repository.getOne(id);
	}

	public WishlistItem saveItem(WishlistItem delegate) {
		return repository.save(delegate);
	}
}
