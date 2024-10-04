package nl.k4u.jpa.wishlist.service;

import nl.k4u.jpa.wishlist.dao.ListDao;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
public class ListService {

	@Autowired
	private ListDao listDao;

	public List<Wishlist> getAllWishlists() {
		return listDao.findAll();
	}

	public List<Wishlist> getAllWishlistsByUser(BeckersUser user) {
		return listDao.getAllByOwner(user);
	}

	public Wishlist getWishListById(Long id) {
		return listDao.findById(id).orElseThrow();
	}

	public Wishlist saveList(Wishlist delegate) {
		return listDao.save(delegate);
	}

	public List<Wishlist> getAllWishlistsExceptUser(BeckersUser user) {
		return listDao.getAllByOwnerNot(user);
	}
}
