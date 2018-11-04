package nl.k4u.web.wishlist.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.service.LoginService;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
public class LoginDetailsProvider implements UserDetailsService {

	@Autowired
	private LoginService loginService;


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		BeckersUser user = loginService.getUserByEmail(username);
		if (null == user) {
			throw new UsernameNotFoundException(username);
		}
		return new LoginPrincipal(user);
	}
}
