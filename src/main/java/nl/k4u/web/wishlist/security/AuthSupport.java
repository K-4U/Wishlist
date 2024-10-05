package nl.k4u.web.wishlist.security;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.web.wishlist.api.pojo.JwtResponse;
import nl.k4u.web.wishlist.api.pojo.LoginRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;

/**
 * @author Koen Beckers (K-4U)
 */
@Component("authSupport")
@Log4j2
@RequiredArgsConstructor
public class AuthSupport {

	public final AuthenticationManager authenticationManager;
	public final JwtUtils jwtUtils;

	/**
	 * We need to update the existing principal; we either have new assignments
	 * available, or something else has happened causing us to want to update
	 * the principal
	 *
	 * @param login The principal which needs to be set.
	 */
	public static void updatePrincipal(LoginPrincipal login) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {

				UsernamePasswordAuthenticationToken ass = (UsernamePasswordAuthenticationToken) auth;
				UsernamePasswordAuthenticationToken newToken = new UsernamePasswordAuthenticationToken(login,
						ass.getCredentials(), login.getAuthorities());
				newToken.setDetails(ass.getDetails());
				SecurityContextHolder.getContext().setAuthentication(newToken);
			}
		} catch (Throwable t) {
			log.warn("Error updating the principal", t);
		}
	}

	/**
	 * Nothing changes which relates to the permissions, only the settings for
	 * the delegate are different. Update the delegate which is used for the
	 * principal
	 *
	 * @param login New entity which needs to be used as delegate.
	 */
	public static void updatePrincipalDelegate(BeckersUser login) {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				if (auth.getPrincipal() != null && auth.getPrincipal() instanceof LoginPrincipal) {
					((LoginPrincipal) auth.getPrincipal()).setDelegate(login);
				}
			}
		} catch (Throwable t) {
			log.warn("Error updating the principal delegate", t);
		}
	}

	/**
	 * Quick access to the delegate for the current authentication objects.
	 *
	 * @return The current authentication delgate.
	 */
	public static BeckersUser getPrincipalDelegate() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				if (auth.getPrincipal() != null && auth.getPrincipal() instanceof LoginPrincipal) {
					return ((LoginPrincipal) auth.getPrincipal()).getDelegate();
				}
			}
		} catch (Throwable t) {
			log.warn("Error getting the principal delegate", t);
		}
		return null;
	}

	/**
	 * Quick access to the login principal.
	 *
	 * @return The current authentication principal.
	 */
	public static LoginPrincipal getPrincipal() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				if (auth.getPrincipal() != null && auth.getPrincipal() instanceof LoginPrincipal) {
					return ((LoginPrincipal) auth.getPrincipal());
				}
			}
		} catch (Throwable t) {
			log.warn("Error getting the principal", t);
		}
		return null;
	}

	public static String getSessionId() {
		try {
			Authentication auth = SecurityContextHolder.getContext().getAuthentication();
			if (auth != null) {
				return ((WebAuthenticationDetails) auth.getDetails()).getSessionId();
			}
		} catch (Throwable t) {
			log.warn("Error getting the principal", t);
		}
		return null;
	}

	public JwtResponse authenticate(LoginRequest loginRequest) {
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.username(),
				loginRequest.password()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		LoginPrincipal userDetails = (LoginPrincipal) authentication.getPrincipal();

		return JwtResponse.builder()
				.token(jwt)
				.delegate(userDetails.getDelegate())
				.build();
	}

    public boolean canEdit(BeckersUser owner) {
        return getPrincipalDelegate().getId().equals(owner.getId());
    }
}
