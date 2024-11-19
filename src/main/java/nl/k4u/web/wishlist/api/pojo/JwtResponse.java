package nl.k4u.web.wishlist.api.pojo;


import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record JwtResponse(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String token,
                          @Schema(requiredMode = Schema.RequiredMode.REQUIRED) UserDTO delegate) {
}
