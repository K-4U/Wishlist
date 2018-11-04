package nl.k4u.web.wishlist.security;

import java.util.Collections;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;

/**
 * @author Koen Beckers (K-4U)
 */
public class LoginPrincipal extends User implements UserDetails {

	private BeckersUser delegate;

	public LoginPrincipal(BeckersUser user) {
		super(user.getEmail(), user.getPassHash(), true, true, true, true, Collections.emptyList());
		this.delegate = user;
	}

	public BeckersUser getDelegate() {
		return delegate;
	}

	public void setDelegate(BeckersUser delegate) {
		this.delegate = delegate;
	}
}
