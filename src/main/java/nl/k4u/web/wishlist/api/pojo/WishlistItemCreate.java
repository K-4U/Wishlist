package nl.k4u.web.wishlist.api.pojo;

import io.swagger.v3.oas.annotations.media.Schema;

public record WishlistItemCreate(@Schema(requiredMode = Schema.RequiredMode.REQUIRED) String description,
                                 @Schema(requiredMode = Schema.RequiredMode.REQUIRED) String url,
                                 @Schema(requiredMode = Schema.RequiredMode.REQUIRED) Double price,
                                 String remarks
) {
}
