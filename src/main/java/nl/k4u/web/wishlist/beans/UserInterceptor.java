package nl.k4u.web.wishlist.beans;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.RedirectView;

import nl.k4u.web.wishlist.security.AuthSupport;

/**
 * @author Koen Beckers (K-4U)
 */
public class UserInterceptor extends HandlerInterceptorAdapter {
	private static final String REDIRECT_PREFIX = "redirect:";

	public final boolean isRedirectView(ModelAndView modelAndView) {
		return (modelAndView.getViewName().startsWith(REDIRECT_PREFIX) || modelAndView.getView() instanceof RedirectView);
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

		if (null != modelAndView.getViewName()) {
			if (!isRedirectView(modelAndView)) {
				if (null != AuthSupport.getPrincipalDelegate()) {
					modelAndView.addObject("user", AuthSupport.getPrincipalDelegate());
				}
			}
		}

	}
}
