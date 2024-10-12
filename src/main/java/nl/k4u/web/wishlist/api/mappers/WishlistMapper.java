package nl.k4u.web.wishlist.api.mappers;

import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.pojo.WishlistItem;
import nl.k4u.web.wishlist.api.pojo.WishlistDTO;
import nl.k4u.web.wishlist.api.pojo.WishlistItemDTO;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {UserMapper.class})
public interface WishlistMapper {

    WishlistDTO toDTO(Wishlist wishlist, @Context CycleAvoidingMappingContext context);

    List<WishlistDTO> toDTOList(List<Wishlist> wishlists, @Context CycleAvoidingMappingContext context);

    //    @Mapping(target="wishlist", )
    WishlistItemDTO toItemDTO(WishlistItem wishlistitem, @Context CycleAvoidingMappingContext context);

//    @Mapping(target="items", ignore = true)
//    WishlistDTO toDTOFromItem(Wishlist wishlist);
//
//    List<WishlistItemDTO> toItemDTOList(List<WishlistItem> wishlistitems);
}
