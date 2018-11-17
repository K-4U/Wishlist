package nl.k4u.jpa.wishlist.service;

import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.UUID;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import nl.k4u.jpa.wishlist.dao.PasswordTokenDao;
import nl.k4u.jpa.wishlist.dao.UserDao;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.PasswordToken;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
@Transactional
public class LoginService {

	@Autowired
	private UserDao userDao;

	@Autowired
	private PasswordTokenDao passwordTokenDao;

	public BeckersUser getUserByEmail(String username) {
		return userDao.getByEmail(username);
	}

	public void save(BeckersUser user) {
		userDao.save(user);
	}

	public void save(PasswordToken token) {
		passwordTokenDao.save(token);
	}

	public PasswordToken createToken(BeckersUser user) throws NoSuchAlgorithmException {
		PasswordToken pwToken = new PasswordToken();
		pwToken.setUser(user);

		String token = DigestUtils.md5DigestAsHex(String.format("%d%s%s", Calendar.getInstance().getTime().getTime(), user.getName(), UUID.randomUUID().toString()).getBytes());
		pwToken.setToken(token);
		save(pwToken);

		return pwToken;
	}

	public PasswordToken getTokenByToken(String token) {
		return passwordTokenDao.getByToken(token);
	}
}

