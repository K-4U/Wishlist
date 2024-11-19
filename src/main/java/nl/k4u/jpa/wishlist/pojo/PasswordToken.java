package nl.k4u.jpa.wishlist.pojo;

import jakarta.persistence.*;
import lombok.Data;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
@Data
public class PasswordToken {

	@Id
	@Column
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@JoinColumn
	@ManyToOne(optional = false)
	private BeckersUser user;

	@Column(unique = true)
	private String token;

}
