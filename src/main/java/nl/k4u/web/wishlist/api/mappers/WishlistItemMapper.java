package nl.k4u.web.wishlist.api.mappers;

import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import nl.k4u.web.wishlist.api.pojo.WishlistItemDTO;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {WishlistMapper.class, UserMapper.class})
public interface WishlistItemMapper {

    WishlistItemDTO toDTO(WishlistItem wishlistitem);

    List<WishlistItemDTO> toDTOList(List<WishlistItem> wishlistitems);
}
