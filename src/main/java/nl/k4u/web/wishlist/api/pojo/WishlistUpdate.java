package nl.k4u.web.wishlist.api.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

public record WishlistUpdate(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) long id,
                             @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String name,
                             @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String icon
) {
}
