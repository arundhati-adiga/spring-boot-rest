package com.apiapp.validator;


import java.util.Arrays;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


/**Custom validator class to validate string array
 * The annotated element must not be {@code null} and must contain at least one
 * non-whitespace character. Accepts {@code  String[]}.
 * @author Arundhati Adiga
 *NotEmptyFieldsValidator.java
 */
public class NotEmptyFieldsValidator implements ConstraintValidator<NotEmptyFields, String[]> {

    @Override
    public void initialize(NotEmptyFields notEmptyFields) {
    }

	

	@Override
	public boolean isValid(String[] array, ConstraintValidatorContext context) {
		if ( array == null ) {
			return false;
		}
		return Arrays.asList(array).stream().allMatch(nef -> nef != null && !nef.trim().isEmpty());
	}
}
	