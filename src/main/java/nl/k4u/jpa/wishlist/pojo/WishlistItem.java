package nl.k4u.jpa.wishlist.pojo;

import java.util.Date;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import nl.k4u.jpa.wishlist.enums.Event;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
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

	@JoinColumn(nullable = false)
	@ManyToOne(optional = false)
	private Wishlist wishlist;

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	@Override
	public String toString() {
		return "WishlistItem{" +
				"id=" + id +
				", owner=" + owner +
				", description='" + description + '\'' +
				", url='" + url + '\'' +
				", price=" + price +
				", addedOn=" + addedOn +
				", purchasedBy=" + purchasedBy +
				", purchasedOn=" + purchasedOn +
				", purchaseEvent=" + purchaseEvent +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		WishlistItem that = (WishlistItem) o;
		return Objects.equals(getId(), that.getId()) &&
				Objects.equals(getOwner(), that.getOwner()) &&
				Objects.equals(getDescription(), that.getDescription()) &&
				Objects.equals(getUrl(), that.getUrl()) &&
				Objects.equals(getPrice(), that.getPrice()) &&
				Objects.equals(getAddedOn(), that.getAddedOn()) &&
				Objects.equals(getPurchasedBy(), that.getPurchasedBy()) &&
				Objects.equals(getPurchasedOn(), that.getPurchasedOn()) &&
				getPurchaseEvent() == that.getPurchaseEvent();
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getOwner(), getDescription(), getUrl(), getPrice(), getAddedOn(), getPurchasedBy(), getPurchasedOn(), getPurchaseEvent());
	}

	public Double getPrice() {
		return price;
	}

	public void setPrice(Double price) {
		this.price = price;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BeckersUser getOwner() {
		return owner;
	}

	public void setOwner(BeckersUser owner) {
		this.owner = owner;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getAddedOn() {
		return addedOn;
	}

	public void setAddedOn(Date addedOn) {
		this.addedOn = addedOn;
	}

	public BeckersUser getPurchasedBy() {
		return purchasedBy;
	}

	public void setPurchasedBy(BeckersUser purchasedBy) {
		this.purchasedBy = purchasedBy;
	}

	public Date getPurchasedOn() {
		return purchasedOn;
	}

	public void setPurchasedOn(Date purchasedOn) {
		this.purchasedOn = purchasedOn;
	}

	public Event getPurchaseEvent() {
		return purchaseEvent;
	}

	public void setPurchaseEvent(Event purchaseEvent) {
		this.purchaseEvent = purchaseEvent;
	}
}
