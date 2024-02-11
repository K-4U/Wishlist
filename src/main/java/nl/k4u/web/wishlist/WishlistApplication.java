package nl.k4u.web.wishlist;

import java.net.URI;
import java.net.URISyntaxException;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "nl.k4u.jpa.wishlist")
@EntityScan("nl.k4u.jpa.wishlist.pojo")
public class WishlistApplication {

	public static void main(String[] args) {
		SpringApplication.run(WishlistApplication.class, args);
	}



	//TODO: Move me to k4lib or something
	public static String getDomainName(String url) throws URISyntaxException {
		URI uri = new URI(url);
		String domain = uri.getHost();
		if (domain == null) {
			throw new URISyntaxException(url, "no domain");
		}
		return domain.startsWith("www.") ? domain.substring(4) : domain;
	}
}
