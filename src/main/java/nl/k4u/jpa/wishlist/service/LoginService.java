package nl.k4u.jpa.wishlist.service;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import nl.k4u.jpa.wishlist.dao.UserDao;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
@Transactional
public class LoginService {

	@Autowired
	private UserDao userDao;

	public BeckersUser getUserByEmail(String username) {
		return userDao.getByEmail(username);
	}

	public void saveUser(BeckersUser user) {
		userDao.save(user);
	}
}
