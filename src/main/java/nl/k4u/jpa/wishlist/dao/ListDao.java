package nl.k4u.jpa.wishlist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;

/**
 * @author Koen Beckers (K-4U)
 */
@Repository
public interface ListDao extends JpaRepository<Wishlist, Long> {

	List<Wishlist> getAllByOwner(BeckersUser owner);

	List<Wishlist> getAllByOwnerNot(BeckersUser owner);

}
