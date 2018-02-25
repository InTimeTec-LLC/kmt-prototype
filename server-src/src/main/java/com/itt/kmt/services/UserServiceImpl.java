
package com.itt.kmt.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itt.kmt.models.User;
import com.itt.kmt.repositories.UserRepository;

/**
 * Service class that acts as an intermediary between controller and the
 * database for all basic CRUD operations. The business logic should reside in
 * service class.
 * 
 * @author Manoj Mewara
 */
@Service
public class UserServiceImpl implements UserService {

    /**
     * Instance of the basic Repository implementation.
     */
    @Autowired
    private UserRepository repository;

    /**
     * Gets the User given the email.
     * 
     * @param email Email of the User.
     * @return User object matching the email.
     */
    public User getUserByEmail(final String email) {

        User user = repository.findByEmail(email);
        return user;
    }
}
