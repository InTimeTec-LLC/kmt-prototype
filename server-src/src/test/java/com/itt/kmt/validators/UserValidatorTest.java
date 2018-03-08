
package com.itt.kmt.validators;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.validation.BindException;
import org.springframework.validation.ValidationUtils;

import com.itt.kmt.models.User;
import com.itt.test_category.ValidatorsTests;

@Category(ValidatorsTests.class)
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserValidatorTest {

    private UserValidator validator;

    @Before
    public void setUp() {

        validator = new UserValidator();
    }

    @Test
    public void supports() {

        assertTrue(validator.supports(User.class));
        assertFalse(validator.supports(Object.class));
    }

    @Test
    public void userIsValid() {

        User user = new User();
        user.setFirstName("testFirstName");
        user.setLastName("testLastName");
        user.setEmail("test@mail.com");
        user.setPassword("testPassword");
        user.setUserRole("Manager");
        user.setActive(true);

        BindException errors = new BindException(user, "user");
        ValidationUtils.invokeValidator(validator, user, errors);
        assertFalse(errors.hasErrors());
    }
}
