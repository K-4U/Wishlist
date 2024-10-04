package nl.k4u.web.wishlist;

import java.net.URI;
import java.net.URISyntaxException;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "nl.k4u.jpa.wishlist")
@EntityScan("nl.k4u.jpa.wishlist.pojo")
@OpenAPIDefinition(info = @Info(title = "Apply Default Global SecurityScheme in springdoc-openapi", version = "1.0.0"), security = {@SecurityRequirement(name = "jwt")})
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "jwt", in = SecuritySchemeIn.HEADER, scheme = "bearer", bearerFormat = "JWT")
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
