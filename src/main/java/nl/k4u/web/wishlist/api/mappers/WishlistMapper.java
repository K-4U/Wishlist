package nl.k4u.web.wishlist.api.mappers;

import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.web.wishlist.api.pojo.WishlistDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {WishlistItemMapper.class, UserMapper.class})
public interface WishlistMapper {

    WishlistDTO toDTO(Wishlist wishlist);

    List<WishlistDTO> toDTOList(List<Wishlist> wishlists);

}
