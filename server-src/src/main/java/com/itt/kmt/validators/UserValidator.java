
package com.itt.kmt.validators;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.itt.kmt.models.User;

/**
 * The Class UserValidator.
 */
public class UserValidator implements Validator {

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#supports(java.lang.Class)
     */
    @Override
    public boolean supports(final Class clazz) {

        return User.class.equals(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object target, final Errors errors) {

        User user = (User) target;

        // do "complex" validation here

    }

}
