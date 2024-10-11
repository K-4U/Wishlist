package nl.k4u.web.wishlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.api.annotations.ResponseOk;
import nl.k4u.web.wishlist.api.mappers.WishlistMapper;
import nl.k4u.web.wishlist.api.pojo.WishlistDTO;
import nl.k4u.web.wishlist.security.AuthSupport;
import nl.k4u.web.wishlist.validators.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
@RestController
@RequestMapping("/api/lists")
@Tag(name = "Lists")
public class ListsRestController extends BaseController {

    private static final String COMMAND_NAME = "listAddCommand";

    @Autowired
    private ListService listService;

    @Autowired
    private ListValidator validator;

    @Autowired
    private WishlistMapper mapper;


    @InitBinder(COMMAND_NAME)
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("own")
    @Operation(summary = "Get own lists")
    @ResponseOk
    public List<WishlistDTO> ownLists(Model model) {
        BeckersUser user = AuthSupport.getPrincipalDelegate();
        return mapper.toDTOList(listService.getAllWishlistsByUser(user));
    }

    @GetMapping
    @Operation(summary = "Get all lists")
    @ResponseOk
    public List<WishlistDTO> getAllLists() {
        return mapper.toDTOList(listService.getAllWishlists());
    }

    //TODO: Add basic edit functionality
}
