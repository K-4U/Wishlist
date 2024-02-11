package nl.k4u.web.wishlist.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author Koen Beckers (K-4U)
 */
//@EnableWebMvc
@Configuration
@ComponentScan(basePackages = {"nl.k4u.web", "nl.k4u.jpa"})
public class WebConfig implements WebMvcConfigurer {

/*
	@Override
	public void configureViewResolvers(ViewResolverRegistry registry) {
		UrlBasedViewResolver viewResolver = new UrlBasedViewResolver();
//		viewResolver.setViewClass(TilesView.class);
		viewResolver.setOrder(0);
		registry.viewResolver(viewResolver);

		InternalResourceViewResolver jspViewResolver = new InternalResourceViewResolver();
		jspViewResolver.setOrder(1);
		jspViewResolver.setViewClass(JstlView.class);
		jspViewResolver.setPrefix("/WEB-INF/jsp/");
		jspViewResolver.setSuffix(".jsp");
		registry.viewResolver(jspViewResolver);
	}*/

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/static/**")
				.addResourceLocations("/static/");
		registry.addResourceHandler("/WEB-INF/jsp/**")
				.addResourceLocations("/WEB-INF/jsp/");

	}
}
