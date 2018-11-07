package nl.k4u.jpa.wishlist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;

/**
 * @author Koen Beckers (K-4U)
 */
@Repository
public interface WishlistItemDao extends JpaRepository<WishlistItem, Integer> {

	List<WishlistItem> getAllByWishlist(Wishlist wishlist);

}
