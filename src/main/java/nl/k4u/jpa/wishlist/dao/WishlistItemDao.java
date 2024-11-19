package nl.k4u.jpa.wishlist.dao;

import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
@Repository
public interface WishlistItemDao extends JpaRepository<WishlistItem, Long> {

	List<WishlistItem> getAllByWishlist(Wishlist wishlist);

}
