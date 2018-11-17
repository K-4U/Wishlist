package nl.k4u.jpa.wishlist.dao;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.PasswordToken;

/**
 * @author Koen Beckers (K-4U)
 */
@Repository
public interface PasswordTokenDao extends JpaRepository<PasswordToken, Integer> {

	List<PasswordToken> getAllByUser(BeckersUser user);

	PasswordToken getByToken(String token);

}
