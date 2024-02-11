package nl.k4u.web.wishlist.api.pojo;


import lombok.Builder;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;

@Builder
public record JwtResponse(String token, String type, BeckersUser delegate) {

}
