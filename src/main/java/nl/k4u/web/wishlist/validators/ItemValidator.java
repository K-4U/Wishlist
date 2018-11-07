package nl.k4u.web.wishlist.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import nl.k4u.web.wishlist.fbo.ItemFBO;

/**
 * @author Koen Beckers (K-4U)
 */
@Component
public class ItemValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return ItemFBO.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "delegate.description", "error.empty", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "delegate.price", "error.empty", "error.empty");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "delegate.url", "error.empty", "error.empty");
	}
}
