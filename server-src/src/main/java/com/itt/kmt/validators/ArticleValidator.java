package com.itt.kmt.validators;

import com.itt.kmt.models.Article;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * The Class ArticleValidator.
 */
public class ArticleValidator implements Validator {
    /* (non-Javadoc)
 * @see org.springframework.validation.Validator#supports(java.lang.Class)
 */
    @Override
    public boolean supports(final Class clazz) {

        return Article.class.equals(clazz);
    }

    /* (non-Javadoc)
     * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
     */
    @Override
    public void validate(final Object target, final Errors errors) {

        Article user = (Article) target;

        // do "complex" validation here

    }
}
