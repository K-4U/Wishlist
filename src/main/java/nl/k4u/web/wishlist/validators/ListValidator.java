package nl.k4u.web.wishlist.validators;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import nl.k4u.web.wishlist.fbo.ListFBO;

/**
 * @author Koen Beckers (K-4U)
 */
@Component
public class ListValidator implements Validator {
	@Override
	public boolean supports(Class<?> aClass) {
		return ListFBO.class.isAssignableFrom(aClass);
	}

	@Override
	public void validate(Object o, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "delegate.listName", "error.empty", "error.empty");
	}
}
