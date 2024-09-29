package nl.k4u.web.wishlist.api.pojo;

import jakarta.validation.constraints.NotEmpty;


public record LoginRequest(@NotEmpty String username, @NotEmpty String password) {
}
