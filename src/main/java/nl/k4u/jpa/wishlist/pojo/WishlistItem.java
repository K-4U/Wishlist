package nl.k4u.jpa.wishlist.pojo;

import java.net.URISyntaxException;
import java.util.Date;

import jakarta.persistence.*;
import lombok.Data;
import nl.k4u.jpa.wishlist.enums.Event;
import nl.k4u.web.wishlist.WishlistApplication;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
@Data
public class WishlistItem {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JoinColumn(nullable = false)
	@ManyToOne(optional = false)
	private BeckersUser owner;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private String url;

	@Column(nullable = false)
	private Double price;

	@Column(nullable = false)
	private Date addedOn;

	@JoinColumn
	@ManyToOne
	private BeckersUser purchasedBy;

	@Column
	private Date purchasedOn;

	@Column
	private Event purchaseEvent;
	@Column(nullable = false)
	private boolean deleted;
	@JoinColumn(nullable = false)
	@ManyToOne(optional = false)
	private Wishlist wishlist;

	@Column(columnDefinition = "text")
	private String remarks;

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

	public boolean hasValidUrl() {
		try {
			WishlistApplication.getDomainName(getUrl());
			return true;
		} catch (URISyntaxException e) {
			return false;
		}
	}
}
