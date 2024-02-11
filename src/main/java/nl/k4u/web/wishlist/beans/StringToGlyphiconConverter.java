package nl.k4u.web.wishlist.beans;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import nl.k4u.web.wishlist.Glyphicon;

/**
 * @author Koen Beckers (K-4U)
 */
@Converter(autoApply = true)
public class StringToGlyphiconConverter implements AttributeConverter<Glyphicon, String> {

	@Override
	public String convertToDatabaseColumn(Glyphicon attribute) {
		return (attribute == null) ? null : attribute.toString();
	}

	@Override
	public Glyphicon convertToEntityAttribute(String dbData) {
		return Glyphicon.fromOrdinal(dbData);
	}
}
