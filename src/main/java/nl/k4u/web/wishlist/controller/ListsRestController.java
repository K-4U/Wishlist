package nl.k4u.web.wishlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.api.annotations.ResponseForbidden;
import nl.k4u.web.wishlist.api.annotations.ResponseNoContent;
import nl.k4u.web.wishlist.api.annotations.ResponseNotFound;
import nl.k4u.web.wishlist.api.annotations.ResponseOk;
import nl.k4u.web.wishlist.api.mappers.WishlistMapper;
import nl.k4u.web.wishlist.api.pojo.WishlistCreate;
import nl.k4u.web.wishlist.api.pojo.WishlistDTO;
import nl.k4u.web.wishlist.api.pojo.WishlistUpdate;
import nl.k4u.web.wishlist.security.AuthSupport;
import nl.k4u.web.wishlist.validators.ListValidator;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author Koen Beckers (K-4U)
 */
@RestController
@RequestMapping("/api/lists")
@Tag(name = "Lists")
@RequiredArgsConstructor
public class ListsRestController extends BaseController {

    private static final String COMMAND_NAME = "listAddCommand";

    private final ListService listService;
    private final ListValidator validator;
    private final WishlistMapper mapper;
    private final AuthSupport authSupport;


    @InitBinder(COMMAND_NAME)
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("own")
    @Operation(summary = "Get own lists")
    @ResponseOk
    public List<WishlistDTO> ownLists() {
        BeckersUser user = AuthSupport.getPrincipalDelegate();
        return mapper.toDTOList(listService.getAllWishlistsByUser(user));
    }

    @GetMapping
    @Operation(summary = "Get all lists")
    @ResponseOk
    public List<WishlistDTO> getAllLists() {
        return mapper.toDTOList(listService.getAllWishlists());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get list by ID")
    @ResponseOk
    public WishlistDTO getListById(@PathVariable("id") Long id) {
        return mapper.toDTO(listService.getWishListById(id));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete list by ID")
    @ResponseNoContent
    @ResponseForbidden
    @ResponseNotFound
    public ResponseEntity<Void> deleteList(@PathVariable("id") Long id) {
        listService.deleteWishListById(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}")
    @Operation(summary = "Update list by ID")
    @ResponseOk
    @ResponseForbidden
    @ResponseNotFound
    public WishlistDTO updateList(@PathVariable("id") Long id, @RequestBody WishlistUpdate dto) {
        return mapper.toDTO(listService.updateList(id, dto));
    }

    @PutMapping("/")
    @Operation(summary = "Create list")
    @ResponseOk
    public WishlistDTO createList(@RequestBody WishlistCreate dto) {
        return mapper.toDTO(listService.createList(dto));
    }

}
