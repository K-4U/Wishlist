package nl.k4u.jpa.wishlist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;
import nl.k4u.web.wishlist.Glyphicon;
import nl.k4u.web.wishlist.beans.StringToGlyphiconConverter;

import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
@Data
@Entity
public class Wishlist {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
    private Long id;

	@JoinColumn
	@ManyToOne(optional = false)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private BeckersUser owner;

	@Column
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private String listName;

	@OneToMany(targetEntity = WishlistItem.class, mappedBy = "wishlist")
	@JsonIgnoreProperties("wishlist")
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private List<WishlistItem> items;

	@Column
	@Convert(converter = StringToGlyphiconConverter.class)
	@Enumerated(EnumType.STRING)
	private Glyphicon icon;

}
