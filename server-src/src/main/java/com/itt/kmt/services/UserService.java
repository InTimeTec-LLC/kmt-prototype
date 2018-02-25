
package com.itt.kmt.services;

import com.itt.kmt.models.User;

/**
 * Service that acts as an intermediary between controller and the database for
 * all basic CRUD operations. The business logic should reside in implementation
 * class.
 * 
 * @author Manoj Mewara
 */
public interface UserService {

    /**
     * Gets the User given the email.
     * 
     * @param email Email of the User.
     * @return User object matching the email.
     */
    User getUserByEmail(final String email);

}
