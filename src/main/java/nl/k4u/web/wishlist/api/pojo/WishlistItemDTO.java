package nl.k4u.web.wishlist.api.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import nl.k4u.jpa.wishlist.enums.Event;
import nl.k4u.web.wishlist.WishlistApplication;

import java.net.URISyntaxException;
import java.util.Date;

/**
 * @author Koen Beckers (K-4U)
 */
@Data
public class WishlistItemDTO {

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Integer id;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private UserDTO owner;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String description;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private String url;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Double price;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Date addedOn;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private UserDTO purchasedBy;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private Date purchasedOn;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED, enumAsRef = true)
    private Event purchaseEvent;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private boolean deleted;

    @JsonIgnoreProperties("items")
    @Schema(requiredMode = Schema.RequiredMode.REQUIRED, name = "wishlist", description = "The wishlist this item is part of")
    private WishlistDTO wishlist;

    @Schema(requiredMode = Schema.RequiredMode.NOT_REQUIRED)
    private String remarks;

    @Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    public String getStore() {
        //Parse:
        String domainName;
        try {
            domainName = WishlistApplication.getDomainName(getUrl());
        } catch (URISyntaxException e) {
            //This means we don't have a valid url, maybe the store was entered, just return that:
            domainName = getUrl();
        }
        return domainName.substring(0, 1).toUpperCase() + domainName.substring(1).toLowerCase();
    }

    @Schema(name = "hasValidUrl", description = "Indicates if the URL is a valid URL", requiredMode = Schema.RequiredMode.REQUIRED)
    public boolean getHasValidUrl() {
        try {
            WishlistApplication.getDomainName(getUrl());
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
