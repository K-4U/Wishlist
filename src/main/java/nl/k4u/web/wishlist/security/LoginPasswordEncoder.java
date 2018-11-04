package nl.k4u.web.wishlist.security;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Koen Beckers (K-4U)
 */
public class LoginPasswordEncoder implements PasswordEncoder {
	// Log instance for this class
	private static final Logger LOG = LogManager.getLogger();

	/**
	 * From plain text to hash!
	 */
	@Override
	public String encode(CharSequence rawPassword) {
		try {
			return PasswordSupport.generate(rawPassword.toString());
		} catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Check if password provided hashed matches the stored password
	 */
	@Override
	public boolean matches(CharSequence rawPassword, String encodedPassword) {
		try {
			return PasswordSupport.validate(rawPassword.toString(), encodedPassword);
		} catch (NoSuchAlgorithmException e) {
			LOG.warn("Unsupported algorithm", e);
		} catch (InvalidKeySpecException e) {
			LOG.warn("Invalid key for algorithm", e);
		}
		return false;
	}
}
