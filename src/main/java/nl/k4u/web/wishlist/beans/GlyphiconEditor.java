package nl.k4u.web.wishlist.beans;

import java.beans.PropertyEditorSupport;

import org.springframework.beans.factory.annotation.Configurable;

import nl.k4u.web.wishlist.Glyphicon;

/**
 * @author Koen Beckers (K-4U)
 */
@Configurable
public class GlyphiconEditor extends PropertyEditorSupport {

	@Override
	public String getAsText() {
		if (getValue() != null) {
			return ((Glyphicon) getValue()).toString();
		} else {
			return "";
		}
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (null == text || "".equals(text)) {
			setValue(null);
		} else {
			setValue(Glyphicon.fromOrdinal(text));
		}
	}
}
