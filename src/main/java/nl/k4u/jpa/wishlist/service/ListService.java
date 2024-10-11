package nl.k4u.jpa.wishlist.service;

import lombok.RequiredArgsConstructor;
import nl.k4u.jpa.wishlist.dao.ListDao;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.web.wishlist.api.exceptions.NotFoundException;
import nl.k4u.web.wishlist.api.pojo.WishlistCreate;
import nl.k4u.web.wishlist.api.pojo.WishlistUpdate;
import nl.k4u.web.wishlist.security.AuthSupport;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
@Service
@RequiredArgsConstructor
public class ListService {

    private final ListDao listDao;
    private final AuthSupport authSupport;

    public List<Wishlist> getAllWishlists() {
        return listDao.findAll();
    }

    public List<Wishlist> getAllWishlistsByUser(BeckersUser user) {
        return listDao.getAllByOwner(user);
    }

    public Wishlist getWishListById(Long id) {
        return listDao.findById(id).orElseThrow();
    }

    public Wishlist saveList(Wishlist delegate) {
        return listDao.save(delegate);
    }

    public List<Wishlist> getAllWishlistsExceptUser(BeckersUser user) {
        return listDao.getAllByOwnerNot(user);
    }

    public void deleteWishListById(Long id) {
        Wishlist list = getWishListById(id);
        if (list == null) {
            throw new NotFoundException("List not found");
        }

        authSupport.assertEdit(list.getOwner());

        if (!list.getItems().isEmpty()) {
            throw new IllegalStateException("List still has items");
        }
        listDao.delete(list);
    }

    public Wishlist updateList(Long id, WishlistUpdate dto) {
        Wishlist list = getWishListById(id);
        if (list == null) {
            throw new NotFoundException("List not found");
        }
        authSupport.assertEdit(list.getOwner());

        list.setListName(dto.name());
        list.setIcon(dto.icon());

        return listDao.save(list);
    }

    public Wishlist createList(WishlistCreate dto) {
        Wishlist list = new Wishlist();
        if (dto.name() == null || dto.name().isBlank()) {
            throw new IllegalArgumentException("Name is required");
        }

        list.setListName(dto.name());
        list.setIcon(dto.icon());
        list.setOwner(AuthSupport.getPrincipalDelegate());

        return listDao.save(list);
    }
}
