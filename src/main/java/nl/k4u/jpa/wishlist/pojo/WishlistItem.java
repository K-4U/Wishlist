package nl.k4u.jpa.wishlist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;
import nl.k4u.jpa.wishlist.enums.Event;

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
	@JsonIgnoreProperties("items")
	private Wishlist wishlist;

	@Column(columnDefinition = "text")
	private String remarks;

}
