package nl.k4u.jpa.wishlist.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.k4u.jpa.wishlist.dao.ListDao;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
public class ListService {

	@Autowired
	private ListDao listDao;

	public List<Wishlist> getAllWishlistsByUser(BeckersUser user) {
		return listDao.getAllByOwner(user);
	}

	public Wishlist getWishListById(Integer id) {
		return listDao.getOne(id);
	}

	public Wishlist saveList(Wishlist delegate) {
		return listDao.save(delegate);
	}
}
