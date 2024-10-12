package nl.k4u.web.wishlist.api.mappers;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {WishlistMapper.class, UserMapper.class})
public interface WishlistItemMapper {


}
