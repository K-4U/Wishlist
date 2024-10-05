package nl.k4u.web.wishlist.api.pojo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;

@Builder
public record JwtResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String token,
                          @Schema(requiredMode = Schema.RequiredMode.REQUIRED) BeckersUser delegate) {
}
