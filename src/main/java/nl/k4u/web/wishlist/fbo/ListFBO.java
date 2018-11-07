package nl.k4u.web.wishlist.fbo;

import nl.k4u.jpa.wishlist.pojo.Wishlist;

/**
 * @author Koen Beckers (K-4U)
 */
public class ListFBO {

	private Wishlist delegate;

	public ListFBO(Wishlist delegate) {
		this.delegate = delegate;
	}

	public ListFBO() {
		this.delegate = new Wishlist();
	}

	public Wishlist getDelegate() {
		return delegate;
	}

	public void setDelegate(Wishlist delegate) {
		this.delegate = delegate;
	}
}
