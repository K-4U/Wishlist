package nl.k4u.jpa.wishlist.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Data;

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
    private Long id;

    @JoinColumn
    @ManyToOne(optional = false)
    private BeckersUser owner;

    @Column
    private String listName;

    @OneToMany(targetEntity = WishlistItem.class, mappedBy = "wishlist")
    @JsonIgnoreProperties("wishlist")
    private List<WishlistItem> items;

    @Column
    private String icon;

}
