package nl.k4u.web.wishlist.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewResolverRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;

import nl.k4u.web.wishlist.beans.UserInterceptor;

/**
 * @author Koen Beckers (K-4U)
 */
//@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"nl.k4u.web", "nl.k4u.jpa"})
public class WebConfig implements WebMvcConfigurer {

	@Autowired
	private UserInterceptor userInterceptor;

	@Bean
	public TilesConfigurer tilesConfigurer() {
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		tilesConfigurer.setDefinitions("/WEB-INF/tiles/tiles.xml");
		tilesConfigurer.setCheckRefresh(true);

		return tilesConfigurer;
	}

	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
		viewResolver.setViewClass(TilesView.class);
		viewResolver.setOrder(0);
		registry.viewResolver(viewResolver);

		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setOrder(1);
		jspViewResolver.setViewClass(JstlView.class);
		jspViewResolver.setPrefix("/WEB-INF/jsp/");
		jspViewResolver.setSuffix(".jsp");
		registry.viewResolver(jspViewResolver);
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations("/static/");
		registry.addResourceHandler("/WEB-INF/jsp/**")
				.addResourceLocations("/WEB-INF/jsp/");

	}

	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(userInterceptor);
	}
}
