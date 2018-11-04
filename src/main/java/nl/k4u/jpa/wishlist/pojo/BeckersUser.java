package nl.k4u.jpa.wishlist.pojo;

import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author Koen Beckers (K-4U)
 */
@Entity
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
	private String passHash;


	@Override
	public String toString() {
		return "BeckersUser{" +
				"id=" + id +
				", name='" + name + '\'' +
				", email='" + email + '\'' +
				", passHash='" + passHash + '\'' +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		BeckersUser user = (BeckersUser) o;
		return Objects.equals(getId(), user.getId()) &&
				Objects.equals(getName(), user.getName()) &&
				Objects.equals(getEmail(), user.getEmail()) &&
				Objects.equals(getPassHash(), user.getPassHash());
	}

	@Override
	public int hashCode() {
		return Objects.hash(getId(), getName(), getEmail(), getPassHash());
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassHash() {
		return passHash;
	}

	public void setPassHash(String passHash) {
		this.passHash = passHash;
	}
}
