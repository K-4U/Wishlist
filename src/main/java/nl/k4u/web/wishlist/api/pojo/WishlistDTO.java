package nl.k4u.web.wishlist.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import nl.k4u.web.wishlist.Glyphicon;

import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
@Data
public class WishlistDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private UserDTO owner;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String listName;

    @JsonIgnoreProperties("wishlist")
//    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, name = "items", description = "The items in the wishlist", hidden = false, implementation = WishlistItemDTO.class, accessMode = Schema.AccessMode.READ_ONLY)
    private List<WishlistItemDTO> items;

    @Schema(enumAsRef = true, requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Glyphicon icon;

    @JsonIgnoreProperties("wishlist")
    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, name = "items")
    public List<WishlistItemDTO> getWishlistItems() {
        return items;
    }
}
