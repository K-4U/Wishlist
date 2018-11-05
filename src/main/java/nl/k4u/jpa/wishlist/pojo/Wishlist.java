package nl.k4u.jpa.wishlist.pojo;

import java.util.List;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
public class Wishlist {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JoinColumn
	@ManyToOne
	private BeckersUser owner;

	@Column
	private String listName;

	@OneToMany(targetEntity = WishlistItem.class, mappedBy = "wishlist")
	private List<WishlistItem> items;


	public Integer getId() {
		return id;
	}

	public BeckersUser getOwner() {
		return owner;
	}

	public void setOwner(BeckersUser owner) {
		this.owner = owner;
	}

	public String getListName() {
		return listName;
	}

	public void setListName(String listName) {
		this.listName = listName;
	}

	public List<WishlistItem> getItems() {
		return items;
	}

	public void setItems(List<WishlistItem> items) {
		this.items = items;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Wishlist wishlist = (Wishlist) o;
		return Objects.equals(getId(), wishlist.getId()) &&
				Objects.equals(getOwner(), wishlist.getOwner()) &&
				Objects.equals(getListName(), wishlist.getListName()) &&
				Objects.equals(getItems(), wishlist.getItems());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getOwner(), getListName(), getItems());
	}

	@Override
	public String toString() {
		return "Wishlist{" +
				"id=" + id +
				", owner=" + owner +
				", listName='" + listName + '\'' +
				", items=" + items +
				'}';
	}
}
