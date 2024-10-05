package nl.k4u.jpa.wishlist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.Data;

import java.util.Date;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
@Data
public class BeckersUser {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private Integer id;

	@Column
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private String name;

	@Column
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private String email;

	@Column
	@JsonIgnore
	private String passHash;

	@Column
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private Date dateOfBirth;

	@Column
	@Schema(requiredMode = Schema.RequiredMode.REQUIRED)
	private String avatarName;

}
