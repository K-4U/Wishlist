package nl.k4u.web.wishlist.config;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import nl.k4u.web.wishlist.security.AuthEntryPointJwt;
import nl.k4u.web.wishlist.security.AuthTokenFilter;
import nl.k4u.web.wishlist.security.JwtUtils;
import nl.k4u.web.wishlist.security.LoginPasswordEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.ExceptionHandlingConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * @author Koen Beckers (K-4U)
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
@Log4j2
public class SecurityConfig {

	//	@Qualifier("loginDetailsProvider")
	private final UserDetailsService userDetailsService;
	private final JwtUtils jwtUtils;
	private final AuthEntryPointJwt unauthorizedHandler;

	@Bean
	public AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter(jwtUtils, userDetailsService);
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(loginPasswordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.csrf(AbstractHttpConfigurer::disable);
		http.exceptionHandling((ExceptionHandlingConfigurer<HttpSecurity> exception) -> {
			exception.authenticationEntryPoint(unauthorizedHandler);
			exception.accessDeniedHandler(accessDeniedHandler());
		});
		http.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
		http.authorizeHttpRequests(requests -> {
			requests.requestMatchers("/api/auth/**", "/api/test/**", "/swagger-ui.html", "/v3/**", "/swagger-ui*/**").permitAll();
			requests.anyRequest().authenticated();
		});

		http.authenticationProvider(authenticationProvider());
		http.addFilterBefore(authenticationJwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public LoginPasswordEncoder loginPasswordEncoder() {
		return new LoginPasswordEncoder();
	}

	@Bean
	public AccessDeniedHandler accessDeniedHandler() {
		return (request, response, accessDeniedException) -> {
			log.error("Unauthorized error: {}", accessDeniedException.getMessage());
			response.sendError(HttpServletResponse.SC_FORBIDDEN, "Access Denied");
		};
	}
}
