package nl.k4u.web.wishlist;

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
}
