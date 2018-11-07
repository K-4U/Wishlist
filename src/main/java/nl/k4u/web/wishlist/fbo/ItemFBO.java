package nl.k4u.web.wishlist.fbo;

import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;

/**
 * @author Koen Beckers (K-4U)
 */
public class ItemFBO {

	private WishlistItem delegate;

	private Wishlist wishlist;

	public ItemFBO(WishlistItem delegate, Wishlist wishlist) {
		this.delegate = delegate;
		this.wishlist = wishlist;
	}

	public ItemFBO(Wishlist wishlist) {
		this.wishlist = wishlist;
		this.delegate = new WishlistItem();
	}

	public ItemFBO() {

	}

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	public WishlistItem getDelegate() {
		return delegate;
	}

	public void setDelegate(WishlistItem delegate) {
		this.delegate = delegate;
	}
}
