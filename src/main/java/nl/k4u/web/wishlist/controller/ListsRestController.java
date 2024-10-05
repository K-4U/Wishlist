package nl.k4u.web.wishlist.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import nl.k4u.jpa.wishlist.pojo.BeckersUser;
import nl.k4u.jpa.wishlist.pojo.Wishlist;
import nl.k4u.jpa.wishlist.service.ListService;
import nl.k4u.web.wishlist.api.annotations.ResponseOk;
import nl.k4u.web.wishlist.fbo.ListFBO;
import nl.k4u.web.wishlist.security.AuthSupport;
import nl.k4u.web.wishlist.validators.ListValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

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


    @InitBinder(COMMAND_NAME)
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(validator);
    }

    @GetMapping("own")
    @Operation(summary = "Get own lists")
    @ResponseOk
    public List<Wishlist> lists(Model model) {
        BeckersUser user = AuthSupport.getPrincipalDelegate();
        return listService.getAllWishlistsByUser(user);
    }

    @GetMapping
    @Operation(summary = "Get all lists")
    @ResponseOk
    public List<Wishlist> getAllLists() {
        return listService.getAllWishlists();
    }

    @RequestMapping(path = "own/add", method = RequestMethod.GET)
    public String addList(Model model) {
        BeckersUser user = AuthSupport.getPrincipalDelegate();
        //May need it. Doubt it

//		model.addAttribute(COMMAND_NAME, getListFBO(null));
        return "lists-own-add";
    }

    @RequestMapping(path = "own/add", method = RequestMethod.POST)
    public String saveList(@Valid @ModelAttribute(COMMAND_NAME) ListFBO obj, BindingResult result,
                           Model model) {
        BeckersUser user = AuthSupport.getPrincipalDelegate();

        if (result.hasErrors()) {
            //May need it. Doubt it
            model.addAttribute(COMMAND_NAME, obj);
            return "lists-own-add";
        }

        obj.getDelegate().setOwner(user);

        Wishlist saved = listService.saveList(obj.getDelegate());
        messagesBean.addSuccesssMessage("Lijst aangemaakt");

        return "redirect:/list/" + saved.getId();
    }

    //TODO: Add basic edit functionality
}
