package nl.k4u.jpa.wishlist.pojo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
@Data
public class BeckersUser {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column
	private String name;

	@Column
	private String email;

	@Column
	@JsonIgnore
	private String passHash;

	@Column
	private Date dateOfBirth;

	@Column
	private String avatarName;

}
