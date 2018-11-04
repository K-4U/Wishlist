package nl.k4u.jpa.wishlist.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;

/**
 * @author Koen Beckers (K-4U)
 */
@Repository
public interface UserDao extends JpaRepository<BeckersUser, Integer> {

	BeckersUser getByEmail(String email);

}
