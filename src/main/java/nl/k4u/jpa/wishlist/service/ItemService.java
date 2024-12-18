package nl.k4u.jpa.wishlist.service;

import nl.k4u.jpa.wishlist.dao.WishlistItemDao;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
@Transactional
public class ItemService {

	@Autowired
	private WishlistItemDao repository;

    public WishlistItem getItemById(Long id) {
        return repository.findById(id).get();
	}

	public WishlistItem saveItem(WishlistItem delegate) {
		return repository.save(delegate);
	}
}
