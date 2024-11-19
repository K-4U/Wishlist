package nl.k4u.web.wishlist.api.mappers;

import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.web.wishlist.api.pojo.UserDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserDTO toDTO(BeckersUser user);
}
