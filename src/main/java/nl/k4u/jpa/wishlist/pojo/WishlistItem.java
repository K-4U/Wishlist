package nl.k4u.jpa.wishlist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import nl.k4u.jpa.wishlist.enums.Event;
import nl.k4u.web.wishlist.WishlistApplication;

import java.net.URISyntaxException;
import java.util.Date;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
@Data
public class WishlistItem {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer id;

	@JoinColumn(nullable = false)
	@ManyToOne(optional = false)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private BeckersUser owner;

	@Column(nullable = false)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private String description;

	@Column(nullable = false)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private String url;

	@Column(nullable = false)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private Double price;

	@Column(nullable = false)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private Date addedOn;

	@JoinColumn
	@ManyToOne
	private BeckersUser purchasedBy;

	@Column
	private Date purchasedOn;

	@Column
	private Event purchaseEvent;

	@Column(nullable = false)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private boolean deleted;

	@JoinColumn(nullable = false)
	@ManyToOne(optional = false)
	@JsonIgnoreProperties("items")
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private Wishlist wishlist;

	@Column(columnDefinition = "text")
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
