package nl.k4u.web.wishlist.validators;

import nl.k4u.web.wishlist.api.pojo.WishlistItemUpdate;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

/**
 * @author Koen Beckers (K-4U)
 */
@Component
public class ItemValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
        return WishlistItemUpdate.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "description", "error.empty", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "price", "error.empty", "error.empty");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "url", "error.empty", "error.empty");
	}
}
